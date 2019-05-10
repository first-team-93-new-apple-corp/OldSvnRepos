import sys
import time
from networktables import NetworkTables

class PublishTables:
    def __init__(self):
        self.ip = None
        self.sd = None
        self.center = None

    def publish(self, ip, center):
        self.center = center
        self.ip = ip
        NetworkTables.initialize(server=self.ip)
        self.sd = NetworkTables.getTable("SmartDashboard")

        self.sd.putNumber("TapeCenter", self.center)