import numpy as np
import cv2
class TeleOp:
	cam_zero = cv2.VideoCapture(0)
	cam_one = cv2.VideoCapture(1)
	cam_two = cv2.VideoCapture(2)
	def __init__(self, cam_number):
		self.cam_number = cam_number
	def get_feed(self, cam_number):
		if cam_number == 0:
			return cam_zero
		elif cam_number == 1:
			return cam_one
		return cam_two
	def end(self):
		cam_zero.release()
		cam_one.release()
		cam_two.release()
	def send_feed(self):
		UDPTransmitter(get_feed)
		pass
	end()
	