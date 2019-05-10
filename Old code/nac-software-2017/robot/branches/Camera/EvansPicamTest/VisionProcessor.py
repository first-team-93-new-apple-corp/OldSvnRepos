import cv2
import numpy as np
import math

class VisionProcessor:
    
    stream = None
    show_errors = True
    fov = (60.0, 60.0)
    resolution = (640, 480)
    adjust = (1.0, 1.5)
    windowed = False
    
    # center_x, center_y, distance, side
    output = None
    
    # Constructs the VisionProcessor, given a stream object.
    # Stream objects have the read method which gives an image in openCV BGR format.
    # should be a stream, not a actual capture, otherwise it will take another image, slowing down the process.
    def __init__(self, stream, resolution, fov, adjust, **kwargs):
        self.stream = stream
        self.resolution = resolution
        self.width, self.height = self.resolution
        self.fov = fov
        self.adjust = adjust
        self.windowed = kwargs.get('windowed', False)
    
    # output is of the form (center_x, center_y, distance, side)
    def get_data(self):
        return self.output
    
    # runs the vision processor.
    # grabs an image and converts it to output data, (center_x, center_y, distance, side).
    def run(self):
        # get image from camera
        capture = self.stream.read()
        if capture is None:
            return
        s, image = capture
        
        # width and height of image is already set as the camera's resolution
        
        # find contours of the target
        contours = self.get_filtered_contours(image, *self.get_hsv_bounds())
        
        # find the center of the tapes
        bounds = None
        # if 2 quadrilaterals found
        if contours is not None and len(contours) == 2:
            # find the big rectangle that encompasses both tapes
            rect = self.merge_rectangles(contours)
            # find the bounds of the rectangle
            bounds = self.find_rotrect_bounds(rect)
            # find the center of that rectangle
            left_x, right_x, bottom_y, top_y = bounds
            center_x, center_y = ((left_x + right_x) * 0.5, (bottom_y + top_y) * 0.5)
            # since we hopefully have a good read on the rectangle
            # calculate distance forward and left/right
            fov_x, fov_y = self.fov
            distance, side = self.find_distance(self.width, self.height, fov_x, fov_y, 10.25, 5, center_x, bounds, self.adjust)
            # write to output
            self.output = (center_x, center_y, distance, side)
        else:
            # no output because it's bad data
            self.output = None
    
    def get_hsv_bounds(self):
        if self.windowed:
            # get current positions of all trackbars
            hMin = cv2.getTrackbarPos('HMin','sliders')
            sMin = cv2.getTrackbarPos('SMin','sliders')
            vMin = cv2.getTrackbarPos('VMin','sliders')
            hMax = cv2.getTrackbarPos('HMax','sliders')
            sMax = cv2.getTrackbarPos('SMax','sliders')
            vMax = cv2.getTrackbarPos('VMax','sliders')
            # Set minimum and max HSV values to display
            self.lowerHSV = np.array([hMin, sMin, vMin])
            self.upperHSV = np.array([hMax, sMax, vMax])
        return (self.lowerHSV, self.upperHSV)
    
    def find_center(self, contour):
        m = cv2.moments(contour.astype(np.uint8), False)
        if m['m00'] != 0:
            return (m['m10']/m['m00'], m['m01']/m['m00'])
        return None
    
    # converts pixels to an angle accurately
    def pxToAngle2(self, px_size, fov, image_width):
        if math.tan(math.radians(fov) * 0.5) != 0:
            f = image_width / (2 * math.tan(math.radians(fov) * 0.5))
            return math.atan2(px_size, f)
        return 0
    
    # gets the sorted contours (by area) of a binary image
    def get_contours(self, thresholded_image):
        contours, hierarchy = cv2.findContours(thresholded_image, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)[-2:]
        return sorted(contours, key = cv2.contourArea, reverse = True)
    
    # blurs an image to reduce noise
    def blur(self, image):
        return cv2.bilateralFilter(image, 5, 75, 75)
    
    # converts a colored image into a binary image by HSV threshold
    def threshold_hsv(self, image, lowerHSV, upperHSV):
        hsv_img = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)
        return cv2.inRange(hsv_img, lowerHSV, upperHSV)
    
    # thresholds but keeps color.
    def threshold_debug(self, image, lowerHSV, upperHSV):
        mask = self.threshold_hsv(image, lowerHSV, upperHSV)
        return cv2.bitwise_and(image, image, mask = mask)
    
    # converts a contour into a polygon
    def polygonize(self, contour):
        epsilon = 0.05 * cv2.arcLength(contour, True)
        return cv2.approxPolyDP(contour, epsilon, True)
    
    #merges two close rectangles into one large one
    def merge_rectangles(self, contours):
        contour_points = []
        # put contour points in list
        for contour in contours:
            for p in range(contour.shape[0]):
                contour_points += [contour[p, 0, 0], contour[p, 0, 1]]
        # format list as contour
        merged_contour = np.array(contour_points).reshape((-1,1,2)).astype(np.int32)
        # get the large rectangle
        return np.int0(cv2.boxPoints(cv2.minAreaRect(merged_contour))) # @UndefinedVariable
    
    # finds the midpoint-adjusted bounding box of a rectangle.
    # returns (left_x, right_x, bottom_y, top_y)
    def find_rotrect_bounds(self, rectangle):
        points_x = sorted(rectangle, key = lambda point: point[0])
        points_y = sorted(rectangle, key = lambda point: point[1])
        # left_x
        return ((points_x[0][0] + points_x[1][0]) * 0.5, 
        # right_x
        (points_x[-1][0] + points_x[-2][0]) * 0.5, 
        # bottom_y
        (points_y[0][1] + points_y[1][1]) * 0.5, 
        # top_y
        (points_y[-1][1] + points_y[-2][1]) * 0.5)
    
    # converts a colored image into its 2 filtered polygon contours
    def get_filtered_contours(self, image, lower_hsv, upper_hsv):
        if image is None:
            return None
        #copy the image so we don't modify the original
        img = image.copy()
        #blur it to get rid of noise
        #blur(img)
        #threshold by HSV
        img = self.threshold_hsv(img, lower_hsv, upper_hsv)
        #get largest 2 contours
        unfiltered_contours = self.get_contours(img)[:2]
        if len(unfiltered_contours) != 2:
            self.print_error("There are only " + str(len(unfiltered_contours)) + " contours.")
            return None
        #convert contours to polygons
        polygons = []
        for contour in unfiltered_contours:
            polygon = self.polygonize(contour)
            #make sure it is a convex quadrilateral, otherwise we have bad data
            if len(polygon) < 3:
                self.print_error("There are " + str(len(polygon)) + " sides.")
                return None
            if not cv2.isContourConvex(polygon):
                self.print_error("Not convex.")
                #return None
            polygons += [self.polygonize(cv2.convexHull(contour))]
        return polygons
    
    # converts an object's bounding angles, along with a known width of an object, to a distance from the camera.
    def anglesToDist(self, known_width, angleMin, angleMax):
        if math.tan(angleMax) - math.tan(angleMin) != 0:
            return known_width / (math.tan(angleMax) - math.tan(angleMin))
        return 0
    
    # finds the camera's distance from an object.
    def find_dist_forward(self, image_width, image_height, fov_width, fov_height, known_width, known_height, leftPx, rightPx, topPx, bottomPx, adjust_x, adjust_y):
        distance_w = adjust_x * self.anglesToDist(known_width, self.pxToAngle2(leftPx - (image_width * 0.5), fov_width, image_width), self.pxToAngle2(rightPx - (image_width * 0.5), fov_width, image_width))
        distance_h = adjust_y * self.anglesToDist(known_height, self.pxToAngle2(bottomPx - (image_height * 0.5), fov_height, image_height), self.pxToAngle2(topPx - (image_height * 0.5), fov_height, image_height))
        return (abs(distance_w) + abs(distance_h)) * 0.5
    
    # finds the camera's sideways distance from an object.
    def find_dist_side(self, dist_forward, px_centerw, image_width, fov_width):
        return dist_forward * math.sin(self.pxToAngle2(px_centerw - (image_width * 0.5), fov_width, image_width))
    
    # finds the camera's distance forward and sideways
    # returns (forward, side)
    def find_distance(self, image_width, image_height, fov_width, fov_height, known_width, known_height, centerPx, rectBounds, adjust):
        leftPx, rightPx, bottomPx, topPx = rectBounds
        adjust_x, adjust_y = adjust
        forward = self.find_dist_forward(image_width, image_height, fov_width, fov_height, known_width, known_height, leftPx, rightPx, topPx, bottomPx, adjust_x, adjust_y)
        side = self.find_dist_side(forward, centerPx, image_width, fov_width)
        return (forward, side)
    
    # prints an error output
    def print_error(self, o):
        if self.show_errors:
            print str(o)
