import socket
from enum import Enum
import sys
class MakeConnection:

    def __init__(self):
        self.s = None
        self.port = 5000
        self.host = '127.0.0.1'
        self.data = None
    def connect(self,data0):
        self.data = data0
        with socket.socketpair(socket.AF_INET, socket.SOCK_STREAM) as self.s:
            self.s.connect((self.host, self.port))
            self.s.sendall("hello/n")




