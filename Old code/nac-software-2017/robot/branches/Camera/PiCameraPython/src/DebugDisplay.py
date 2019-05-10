'''
Created on Mar 23, 2017

@author: Admin93
'''
import cv2
#import numpy as np

class DebugDisplay:
    #frame = np.zeros((300,512,3), np.uint8) #black image
    def __init__(self):
        pass
    
    def nothing(self):
        pass
    def create_threshold_window(self, frame):
        cv2.namedWindow('current_frame')
        cv2.createTrackbar('H','image',0,255,self.nothing())
        cv2.createTrackbar('S','image',0,255,self.nothing())
        cv2.createTrackbar('V','image',0,255,self.nothing())
    
        while(True):
            cv2.imshow('image',frame)
            k = cv2.waitKey(1) & 0xFF
            f = cv2.waitKey(1) & 0xFF
            if k == 27:
                break
            h = cv2.getTrackbarPos('H','current_frame')
            s = cv2.getTrackbarPos('S','current_frame')
            v = cv2.getTrackbarPos('V','current_frame')
            frame[:] = [h,s,v]
            if f == 27:
                print h,s,v
    
        cv2.destroyAllWindows()