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
camera.awb_gains = (1,1)
rawCapture = PiRGBArray(camera)

time.sleep(0.1)

for frame in camera.capture_continuous(rawCapture, format ="bgr", use_video_port=True):
    im = frame.array
    imgray = cv2.inRange(im, np.array([100, 100, 100], dtype = "uint8"), np.array([255,255,255], dtype = "uint8"))
    edges = cv2.Canny(imgray,30,200)
    contours, hierarchy = cv2.findContours(edges.copy(),cv2.RETR_TREE,cv2.CHAIN_APPROX_NONE)
    cv2.drawContours(im, contours, -1, (0,255,0), 2)
    cv2.imshow("Image",im)

    cv2.imwrite('/home/pi/Desktop/test.jpg', im)
    
    key = cv2.waitKey(1) &0xFF
    rawCapture.truncate(0)
    
    if key == ord("q"):
        break
