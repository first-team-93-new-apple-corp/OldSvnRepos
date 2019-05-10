# import the necessary packages
import sys
sys.path.append('/usr/local/lib/python2.7/site-packages')
import numpy as np
import cv2
from picamera.array import PiRGBArray
from picamera import PiCamera
import time

camera = PiCamera()
camera.resolution = (320, 240)
camera.framerate = 8
rawCapture = PiRGBArray(camera)

while True:
    camera.capture(rawCapture, format ="bgr")
    frame = rawCapture.array
    cv2.imshow("frame",frame)
    rawCapture.truncate(0)
    if cv2.waitKey(1) &0xFF == ord('q'):
        break

cv2.destroyAllWindows()
