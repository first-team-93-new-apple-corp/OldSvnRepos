'''
Created on Mar 23, 2017

@author: Colby McKinley
@version: 1.0
'''
from _socket import AF_INET
'''
Created on Mar 23, 2017

@author: Colby McKinley
@version: 1.0
'''
import socket
from CameraData import CameraData

class UDPServer:
    #socket = None
    def __init__(self):
        self.socket = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
        self.cam_data = CameraData()
    def send_data(self, camera_data):
        port = 5005
        RoboRioIP = "roboRIO-93-FRC.local" #"10.0.93.2"
        try:
            self.socket.connect(RoboRioIP,port)
            data = self.cam_data.to_string()
            self.socket.sendall(data, (RoboRioIP,port))
            self.data_sent = repr(data)
            self.socket.close()
        except Exception:
            print "Error 401"
    