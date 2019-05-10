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
camera.awb_gains = (4.0,4.0)
rawCapture = PiRGBArray(camera)

time.sleep(0.1)

params = cv2.SimpleBlobDetector_Params()
#params.minThreshold = 5
#params.maxThreshold = 255
params.filterByCircularity = False
params.filterByArea = False
params.filterByConvexity = False
params.filterByInertia = False
params.filterByColor = False
#params.blobColor = 250
#params.minCircularity = 0.3
#params.maxCircularity = 0.85
detector = cv2.SimpleBlobDetector(params)

for frame in camera.capture_continuous(rawCapture, format ="bgr", use_video_port=True):
    image = frame.array

    mask = cv2.inRange(image, np.array([100, 100, 100], dtype = "uint8"), np.array([255,255,255], dtype = "uint8"))
    output = cv2.bitwise_and(image, image, mask=mask)
    
    keypoints = detector.detect(output)
    frame_with_keypoints = cv2.drawKeypoints(mask, keypoints, np.array([]), (0,255,0), cv2.DRAW_MATCHES_FLAGS_DRAW_RICH_KEYPOINTS)

    cv2.imshow("Image", frame_with_keypoints)
    key = cv2.waitKey(1) &0xFF
    rawCapture.truncate(0)

    if key == ord("q"):
        break
