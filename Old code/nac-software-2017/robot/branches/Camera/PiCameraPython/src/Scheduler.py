'''
Created on Mar 25, 2017

@author: Colby McKinley
'''
from VisionProcessor2017 import VisionProcessor2017
from Ship import Ship
class Scheduler:
    #vp = VisionProcessor2017()
    
    def __init__(self, vision_processor):
        self.vp = vision_processor
        self.vp.debug_mode = True
    def task_run(self):
        while True:
            self.vp.processor_debug()
            if len(self.vp.findContoursOutput) <= 0:
                print "Error 102"
            if len(self.vp.filteredContoursOutput) <=0:
                print "Error 103"
                '''
            if not self.vp.db_found_tape:
                print "Error 104"
            if not self.vp.udp.data_sent:  #check to make sure this is the proper way to see if data was sent
                print "Error 400"
            if self.vp.myData.get_forward_distance() is None:
                print "Error 200"
            if self.vp.myData.get_left_right_distance() is None:
                print "Error 201"
            if self.vp.myData.get_score() is None:
                print "Error 202"
            if not self.vp.camera_found:
                print "Error 300"
            if not self.vp.capture.isOpen():
                print "Error 301"
            if not self.vp.db_camera_read:
                print "Error 302"
            if not self.check_side(self.vp.myData.db_side):
                print "Error 500"
                '''
                
    def start(self):
        self.vp.start()
        
    def check_side(self, side):
        good_value = False
        if side == Ship.ShipSide_t.LEFT:
            good_value = True
        elif side == Ship.ShipSide_t.RIGHT:
            good_value = True
        elif side == Ship.ShipSide_t.CENTER:
            good_value = True
        else:
            pass
        return good_value
        
