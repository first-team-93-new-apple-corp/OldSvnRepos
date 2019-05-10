import sys 
sys.path.append('/usr/local/lib/python2.7/site-packages')
import numpy as np
from picamera.array import PiRGBArray
from picamera import PiCamera
import time

import cv2

camera = PiCamera()
camera.resolution = (320, 240)
camera.framerate = 0
#camera.awb_mode = 'off'
#camera.awb_gains = (1,1)
camera.exposure_compensation = 25
rawCapture = PiRGBArray(camera)

time.sleep(0.1)

for frame in camera.capture_continuous(rawCapture, format ="bgr", use_video_port=True):

    roi = cv2.imread('/home/pi/Desktop/test.jpg')
    hsv = cv2.cvtColor(roi,cv2.COLOR_BGR2HSV)

    target = frame.array
    hsvt = cv2.cvtColor(target,cv2.COLOR_BGR2HSV)     

    roihist = cv2.calcHist([hsv],[0,1],None, [180,256],[0,180,0,256])

    cv2.normalize(roihist,roihist,0,255,cv2.NORM_MINMAX)
    dst = cv2.calcBackProject([hsvt],[0,1],roihist,[0,180,0,255],1)

    disc = cv2.getStructuringElement(cv2.MORPH_ELLIPSE,(5,5))
    cv2.filter2D(dst,-1,disc,dst)

    ret,thresh = cv2.threshold(dst,50,255,0)
    thresh = cv2.merge((thresh, thresh, thresh))
    res = cv2.bitwise_and(target,thresh)

    res = np.vstack((target, thresh, res))
    cv2.imshow("final", res)

    key = cv2.waitKey(1) &0xFF
    rawCapture.truncate(0)
    
    if key == ord("q"):
        break
