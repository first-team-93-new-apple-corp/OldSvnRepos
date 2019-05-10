import sys
sys.path.append('/usr/local/lib/python2.7/site-packages')
import numpy as np
from picamera.array import PiRGBArray
from picamera import PiCamera
import time

import cv2

camera = PiCamera()
camera.resolution = (320, 240)
camera.framerate = 8
camera.awb_mode = 'off'
camera.awb_gains = (0.0,.0)
rawCapture = PiRGBArray(camera)

time.sleep(0.1)

for frame in camera.capture_continuous(rawCapture, format ="bgr", use_video_port=True):
    im = frame.array
    imgray = cv2.inRange(im, np.array([80, 75, 80], dtype = "uint8"), np.array([255,255,255], dtype = "uint8"))
    edges = cv2.Canny(imgray,30,200)
    contours, hierarchy = cv2.findContours(edges.copy(),cv2.RETR_TREE,cv2.CHAIN_APPROX_NONE)
    
    largest = max(contours, key = cv2.contourArea)
    cv2.drawContours(im, largest, -1, (0,255,0), 2)

    M = cv2.moments(largest)
    if int(M['m00']) != 0:
        cx = int(M['m10']/M['m00'])
        cy = int(M['m01']/M['m00'])
        print "cx = ", cx
        print "cy = ", cy
        print "area = ", cv2.contourArea(largest)
    else:
        print "m00 = 0.0"
   
    cv2.imshow("image", im)
    j=0

    key = cv2.waitKey(1) &0xFF
    rawCapture.truncate(0)
    
    if key == ord("q"):
        break
