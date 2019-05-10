import numpy as np
import cv2

Class VisionProcessor:
	#get proper port for cam
	camera_feed = cv2.VideoCapture(0)
	#connor values
	lower_green = np.array([0, 150, 0], dtype = "uint8")
	upper_green = np.array([150,255,150], dtype = "uint8")
	def __init__(self):
		pass
	def convert_feed(self):
		while(True):
			_, feed = camera_feed.read()
			#ret, feed = camera_feed.read()
			hsv = cv2.cvtColor(feed, cv2.COLOR_BGR2HSV)
			thresh = cv2.inrange(hsv, lower_green, upper_green)
			#result = cv2.bitwise_and(feed, feed, mask = thresh)
			kernel = np.ones((5,5),np.uint8)
			#removes false positives
			opening = cv2.morphologyEx(mask, cv2.MORPH_OPEN, kernel)
			#removes false negatives
			closing = cv2.morphologyEx(mask, cv2.MORPH_CLOSE, kernel)
			if cv2.waitKey(1) & 0xFF == ord('q'):
				break
	def corner_detection(self):
		_, feed = camera_feed.read()
		gray = cv2.cvtColor(feed, cv2.COLOR_BGR2GRAY)
		gray = np.float31(gray)
		#corners = cv2.goodFeaturesToTrack()
		for item in corners:
			x,y = item.ravel()
	camera_feed.release()