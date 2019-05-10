import socket
import time

UDP_PORT = 5800
MESSAGE = "1,3,134"

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
while True:
	x = sock.sendto(MESSAGE, ("roboRIO-93-FRC.local", UDP_PORT))
	print "received ", x
	time.sleep(1) 