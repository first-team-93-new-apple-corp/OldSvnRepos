import sys 
sys.path.append('/usr/local/lib/python2.7/site-packages')
import numpy as np
from picamera.array import PiRGBArray
from picamera import PiCamera
import time
import socket

import cv2


camera = PiCamera()
camera.resolution = (320, 240)
#camera.framerate = 0
camera.shutter_speed = 10000
camera.exposure_mode = "off"
rawCapture = PiRGBArray(camera)

UDP_PORT = 5800
sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
sock.settimeout(.001)

time.sleep(0.1)

while(True):    
    for frame in camera.capture_continuous(rawCapture, format ="bgr", use_video_port=True):

        print "top of for loop"
        im = frame.array

        cv2.imwrite('home/pi/Desktop/star_imaget.jpg',im)

        imgray = cv2.inRange(im, np.array([0, 150, 0], dtype = "uint8"), np.array([150,255,150], dtype = "uint8"))

        edges = cv2.Canny(imgray,30,200)
        contours, hierarchy = cv2.findContours(edges.copy(),cv2.RETR_TREE,cv2.CHAIN_APPROX_NONE)

        if (len(contours) > 0):
            largest = max(contours, key = cv2.contourArea)
            cv2.drawContours(im, largest, -1, (255,0,0), 2)

            M = cv2.moments(largest)
            if float(M['m00']) != 0.0:
                cx = int(M['m10']/M['m00'])
                cy = int(M['m01']/M['m00'])
                print "cx = ", cx

                height, width, channels = im.shape
                print "width = ",width
                percent = float(cx) / float(width)
                print "percent = ",percent
                    
                print "cy = ", cy
                area = cv2.contourArea(largest)
                print "area = ", area
                if (area > 100):
                    cv2.imwrite('/home/pi/Desktop/final_image.jpg', im)
                    cv2.imwrite('/home/pi/Desktop/thresholded.jpg', imgray)
                    cv2.imwrite('/home/pi/Desktop/edges.jpg', edges)
         
                message = str(cx)+','+str(cy)+','+str(area)
            else:
                print "m00 = 0.0"
                message = "m00 = 0.0"
        else:
            message = "0 contours detected"
        #try:
         #   x = sock.sendto(message, ("roboRIO-93-FRC.local", UDP_PORT))
          #  print message
           # print "received ", x
        #except Exception:
         #   print "error caught"
        cv2.imshow("image",im)
        cv2.imshow("thresholded",imgray)
               
        key = cv2.waitKey(1) &0xFF
        rawCapture.truncate(0)
            
        if key == ord("q"):
            break
