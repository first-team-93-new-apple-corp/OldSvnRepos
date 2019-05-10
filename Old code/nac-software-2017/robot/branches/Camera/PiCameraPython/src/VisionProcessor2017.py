import cv2
import numpy as np
from collections import namedtuple
from VisionDataRegulator import VisionDataRegulator 
from CameraData import CameraData
from UDPServer import UDPServer
from DebugDisplay import DebugDisplay
from picamera.array import PiRGBArray
from Ship import Ship
from picamera import PiCamera

'''
Created on Mar 23, 2017

@author: Colby McKinley
@version: 1.0
'''

class VisionProcessor2017:
    '''
    The Vision Processor Class is the core of the vision processing code.  
    It takes in an image, then finds the distance away and distance to center (both in pixels).  
    It uses other classes to convert these values to ticks, check the quality of the data, and send it off.
    '''
    
    Team93Cam = namedtuple("Team93Cam", "camera_name port image_width image_height")
    camera_values = Team93Cam(camera_name = "cam", port = 0, image_width = 640, image_height = 480)
    def __init__(self):
        #the named tuple is like an emun 
        #these are scalars, they represent the lower threshold for the green and the upper threshold
        self.lower_green = np.array([0,0,0],np.uint8)
        self.upper_green = np.array([255,255,255],np.uint8)
        print "MADE THE PI"
        self.cam = PiCamera()
        self.cam.resolution = (640, 480)
        self.raw_cam = PiRGBArray(self.cam)
        self.grabbed, self.output = self.capture()
        
        self.cam_active = False
        #the image output is a matrix
        #output = None
        self.findContoursOutput = []
        self.filteredContoursOutput = []
        self.keep_running = False
        self.debug_mode = True
        self.regulator = VisionDataRegulator()
        self.myData = CameraData()
        self.keep_running = True

    # cams an image from the PiCamera
    def capture(self):
        self.grabbed = True
        self.raw_cam.seek(0)
        self.raw_cam.truncate()
        self.cam.capture(self.raw_cam, format='bgr', use_video_port = True)
        return (self.grabbed, self.raw_cam.array)
    
    #def start(self):
        '''
        Initializes the VisionProcessor
        '''
    #    self.cam = cv2.VideoCapture(self.camera_values.port)
    #    self.camera_found = self.cam is not None
    #    if self.debug_mode:
    #        self.processor_debug()
    #    else:
    #        self.processor()
    def processor(self):
        '''
        The processor takes in continous feed to process each still image to find the 
        values to position the robot
        '''
        self.start_cam()
        #create a black image
        source = np.zeros((300,512,3), np.uint8)
        while self.keep_running:
            source = self.cam.read()
            self.output = cv2.cvtColor(source, cv2.COLOR_BGR2HSV)
            if self.output is not None:
                self.process_image()
                self.find_side()
                if self.debug_mode:
                    #cv2.imshow("Threshold", self.thresh_copy)
                    cv2.imshow("Processed Image", self.output)
                    #cv2.namedWindow("Threshold", cv2.WINDOW_NORMAL, self.thresh_copy)
                    #cv2.namedWindow("Processed Image", cv2.WINDOW_NORMAL, self.output)
                    dd = DebugDisplay()
                    dd.create_threshold_window(self.thresh_copy)
                #udp = UDPServer()
                #udp.send_data(self.myData)
        #release to prevent memory issues
        source.release()
        self.output.release()
    def processor_debug(self):
        '''
        Used for debugging
        '''
        
        #self.start_cam()
        grabbed, source = self.capture()
        self.db_camera_read = source is None
        self.output = cv2.cvtColor(source, cv2.COLOR_BGR2HSV)
        if self.output is not None:
            self.process_image()
            self.find_side()
            #cv2.imshow("Threshold", self.thresh_copy)
            cv2.imshow("Processed Image", self.output)
            #cv2.namedWindow("Processed Image", cv2.WINDOW_NORMAL, self.output)
            #dd = DebugDisplay()
            #dd.create_threshold_window(self.thresh_copy)
            #self.udp = UDPServer()
            #self.udp.send()
        else:
            print "Error 101: No image from camera"
        #source.release()
        #self.output.release()
    def process_image(self):
        '''
        Processes each still image
        '''
        self.output = cv2.inRange(self.output, self.lower_green, self.upper_green)
        self.find_contours(self.output, self.findContoursOutput, cv2.RETR_CCOMP, cv2.CHAIN_APPROX_NONE)
        self.filter_contours(self.findContoursOutput)
    def find_contours(self, source, contours_output_list, mode, method):
        for x in range(len(contours_output_list)):
            contours_output_list.pop()
        contours_output_list_copy,_ =cv2.findContours(source, mode, method)

    #def start_cam(self):
    #    self.cam.open(self.camera_values.port)
    #    if not self.cam_active:
    #        if self.cam.isOpened():
    #            self.cam_active = True
    #    else:
    #        self.cam_active = False
    #        self.cam.release()
    def find_side(self):
        if len(self.filteredContoursOutput) > 0:
            sorted_list_of_mop = self.find_two_pieces_of_tape(self.filteredContoursOutput)
            tape_left = None
            tape_right = None
            if sorted_list_of_mop == 2:
                self.db_found_tape = True
                x0 = cv2.boundingRect(sorted_list_of_mop[0])
                x1 = cv2.boundingRect(sorted_list_of_mop[1])
                if x0 > x1:
                    temp = sorted_list_of_mop[0]
                    sorted_list_of_mop[0] = sorted_list_of_mop[1]
                    sorted_list_of_mop[1] = temp
                    
                tape_left = sorted_list_of_mop[0]
                tape_right = sorted_list_of_mop[1]
                
                pixel_difference = 0
                x_left, y_left, width_left, width_left, height_left = cv2.boundingRect(tape_left)
                x_right, y_right, width_right, width_right, height_right = cv2.boundingRect(tape_right)
                
                if tape_left is not None and tape_right is not None:
                    left_side_pixels = x_left
                    right_side_pixels = self.camera_values.image_width - x_right + width_right
                    left_right_ratio = left_side_pixels/right_side_pixels
                    
                    if left_right_ratio < 1:
                        
                        side = Ship.ShipSide_t.RIGHT
                        #print("driving left")
                        pixel_difference = left_side_pixels/left_right_ratio
                        #print("pixels" + m_pixelDifference)
                        leftRightDistance = self.getDriveXDistance(x_right, pixel_difference)
                        #print("inches" + leftRightDistance)
                        fowardDistance = self.getDriveYDistance(width_left)
                        self.regulator.set_qualtity_control_variables(fowardDistance, leftRightDistance, width_left, height_left)
                        self.myData.set_all(leftRightDistance, side, fowardDistance, self.regulator.getScore())

                        pass
                    elif left_right_ratio > 1:
                        side = Ship.ShipSide_t.LEFT
                        #print("driving right")

                        pixel_difference = right_side_pixels/left_right_ratio
                        #print("pixels" + m_pixelDifference)
                        leftRightDistance = self.getDriveXDistance(x_right, pixel_difference)
                        #print("inches" + leftRightDistance)
                        fowardDistance = self.getDriveYDistance(width_left)
                        self.regulator.set_qualtity_control_variables(fowardDistance, leftRightDistance, width_left, height_left)
                        self.myData.set_all(leftRightDistance, side, fowardDistance, self.regulator.getScore())
                    elif left_right_ratio == 1:
                        #print("driving foward")
                        side = Ship.ShipSide_t.CENTER
                        leftRightDistance = 0
                        fowardDistance= self.getDriveYDistance(width_left)
                        self.regulator.setQualtityControlVariables(fowardDistance, leftRightDistance, width_left, height_left)
                        self.myData.set_all(leftRightDistance, side, fowardDistance, self.regulator.getScore())
    def filter_contours(self, list_of_contours):
        '''
        Here the contours are found then determined if they make up of a rectangle
        '''
        for x in range(len(self.filteredContoursOutput)):
            self.filteredContoursOutput.pop()
        #self.filteredContoursOutput.clear()
        #x,y,width,height = cv2.boundingRect(mop)
        for mop in list_of_contours:
            epsilon = 0.1*cv2.arcLength(mop,True)
            approx = cv2.approxPolyDP(mop,epsilon,True)
            if len(approx) == 4:
                self.filteredContoursOutput.append(mop)
    def find_two_pieces_of_tape(self, list_of_contours):       
        yTolerance = 10
        
        tapeWidthOverTotalWidthTolerance = 0.2
        interiorWidthOverTotalWidthTolerance = 0.2
        tapeOneWidthOverTapeTwoWidthTolerance = 0.2
        areaTolerance = .2
        
        INTERIOR_TOTAL_WIDTH_RATIO = 6.25 / 10.25
        TAPE_WIDTH_TOTAL_WIDTH_RATIO = 2.0 / 10.25
        TAPE_WIDTH_TAPE_WIDTH_RATIO = 2.0 / 2.0
        
        tapeZeroWidthOverTapeOneWidth = 0.0
        tapeZeroWidthOverTotalWidth = 0.0
        tapeOneWidthOverTotalWidth = 0.0
        interiorWidthOverTotalWidth = 0.0
        totalWidth = 0.0
        interiorWidth = 0.0
        
        twoPiecesOfTape = []
        isFound = False
        for i in range(len(list_of_contours)):
            for j in range(len(list_of_contours)):       
                if i >= j:
                    continue
                tempZero = i
                tempOne = j
                xi,yi,widthi,heighti = cv2.boundingRect(i)
                xj,yj,widthj,heightj = cv2.boundingRect(j)
                #lets work on this
                if tempOne.x < tempZero.x:
                    tempTwo = tempZero
                    tempZero = tempOne
                    tempOne = tempTwo
                    
                    interiorWidth = xj - xi + widthi
                    if self.checkForIntersection(tempZero, tempOne):
                        continue
                    
                    totalWidth = widthi + interiorWidth + widthj
                    tapeZeroWidthOverTapeOneWidth = widthi /widthj
                    tapeZeroWidthOverTotalWidth = widthi / totalWidth
                    interiorWidthOverTotalWidth = interiorWidth / totalWidth

                    tapeOneWidthOverTotalWidth = tempOne.width / totalWidth
                    if abs(yi - yj) > yTolerance:
                        continue
                    if abs(tapeZeroWidthOverTapeOneWidth - TAPE_WIDTH_TAPE_WIDTH_RATIO) > tapeOneWidthOverTapeTwoWidthTolerance:
                        continue
                    if abs(tapeZeroWidthOverTotalWidth- TAPE_WIDTH_TOTAL_WIDTH_RATIO) > tapeWidthOverTotalWidthTolerance:
                        continue
                    if abs(tapeOneWidthOverTotalWidth - TAPE_WIDTH_TOTAL_WIDTH_RATIO) > tapeWidthOverTotalWidthTolerance:
                        continue
                    if abs(interiorWidthOverTotalWidth - INTERIOR_TOTAL_WIDTH_RATIO) > interiorWidthOverTotalWidthTolerance:
                        continue
                    if abs(cv2.contourArea(i) - cv2.contourArea(j))> areaTolerance:
                        continue
                    twoPiecesOfTape.append(i)
                    twoPiecesOfTape.append(j)
                    isFound = True
                    break
                if isFound:
                    break
        return twoPiecesOfTape
    def checkForIntersection(self, zerox, onex, zero_width):
        return not onex > zerox + zero_width
    def getDriveXDistance(self, tape_x, input_pixels):
        inches_tape_width = 2
        inches_pixels_product = inches_tape_width * input_pixels
        return tape_x / inches_pixels_product
    def getDriveYDistance(self, input_pixels):
        FOCAL_LENGTH = 705.5625 #@Colby McKinley calculated this out
        KNOWN_WIDTH_OF_PIECE_OF_TAPE = 2.0
        distanceFromTarget = FOCAL_LENGTH * KNOWN_WIDTH_OF_PIECE_OF_TAPE / input_pixels
        return distanceFromTarget
            
