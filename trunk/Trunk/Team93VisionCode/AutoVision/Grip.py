import cv2
import numpy

class AutoVision:

    def __init__(self):
        self._cam_source = None
        self.frame = None
        self.blur_edge = None
        self.resize_cam_source = None
        self.blurred = None
        self.coutput = []
        self.__filter_contours_min_area = 1000.0
        self.__filter_contours_min_perimeter = 0.0
        self.__filter_contours_min_width = 0
        self.__filter_contours_max_width = 1000
        self.__filter_contours_min_height = 0
        self.__filter_contours_max_height = 1000
        self.__filter_contours_solidity = [0, 100]
        self.__filter_contours_max_vertices = 10000.0
        self.__filter_contours_min_vertices = 0
        self.__filter_contours_min_ratio = 0
        self.__filter_contours_max_ratio = 1000
        self.line_contours = None




    def process_bot(self,camsource):
        self._cam_source = camsource
        retval , self.frame = cv2.VideoCapture.read(self._cam_source)
        self.resize_cam_source = cv2.resize(self.frame, ((int)(320.0), (int)(240.0)), 0, 0, cv2.INTER_CUBIC)
        out = cv2.cvtColor(self.resize_cam_source, cv2.COLOR_BGR2HSV)
        self.grayed = cv2.inRange(out, (0.0, 142.0, 0.0),  (80.0, 255.0, 255.0))
        self.blurred = cv2.GaussianBlur(self.grayed, (5, 5), 0)
        _, self.blur_edge = cv2.threshold(self.blurred, 127, 255, cv2.THRESH_BINARY)
        _,self.line_contours,hierarchy  = cv2.findContours(self.blur_edge, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)


        if len(self.line_contours) > 0:
            #cv2.drawContours(self.resize_cam_source,self.line_contours,-1,(0,255,0),3)
            rect = cv2.minAreaRect(self.line_contours[0])
            box = cv2.boxPoints(rect)
            box = numpy.int0(box)
            (x, y), (width, height), rect_angle = rect
            cv2.drawContours(self.resize_cam_source, [box], 0, (0, 0, 255), 2)
            print "angle:", rect_angle
            print "center:" , (x, y)

        else:
            print "No Contours found"













