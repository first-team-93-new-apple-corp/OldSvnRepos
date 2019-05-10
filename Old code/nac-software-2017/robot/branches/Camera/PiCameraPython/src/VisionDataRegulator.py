'''
Created on Mar 23, 2017

@author: Colby McKinley
@version: 1.0
'''

class VisionDataRegulator:
  
    MAX_WIDTH_INCHES = 324 #27ft - the field width
    MAX_FORWARD_INCHES = 652 #54ft and 4 in - the field length
    REAL_MAX_FORWARD = 50  #length of neutral zone
    REAL_MAX_CENTER = 40
    MAX_TAPE_WIDTH = 240 #half the screen
    MAX_TAPE_HEIGHT = 640 #hieght of screen
    REAL_TAPE_WIDTH_MAX = 90
    REAL_TAPE_HEIGHT_MAX = 225
    REAL_TAPE_WIDTH_MIN = 5
    REAL_TAPE_HEIGHT_MIN = 13
    
    forward_distance = 0.0
    center_distance = 0.0
    tape_width = 0.0
    tape_height = 0.0
    score = 0.0
    def __init__(self):
        pass
    def check_left_right_bounds(self,leftRightDistance):
        return leftRightDistance < self.MAX_WIDTH_INCHES
    
    def check_foward_bounds(self,forwardDistance):
        return forwardDistance < self.MAX_FORWARD_INCHES
    
    def set_qualtity_control_variables(self, distance_forwad, distance_center,t_width,t_height):
        self.forward_distance = distance_forwad
        self.center_distance = distance_center
        self.tape_width = t_width
        self.tape_height = t_height
   
    def set_quality_score(self):
 
        NUMBER_OF_TESTS = 4;
        if self.check_left_right_bounds(self.center_distance):
            if self.center_distance < self.REAL_MAX_CENTER:
                se.fscore +=1
            else:
                self.score+=.25
        if self.check_foward_bounds(self.foward_distance):
            if self.foward_distance < self.REAL_MAX_FORWARD:
                self.score +=1
            else:
                self.score+=.25
        
        if self.tape_width < self.MAX_TAPE_WIDTH:
            if self.tape_widht < self.REAL_TAPE_WIDTH_MAX and self.tape_width > self.REAL_TAPE_WIDTH_MIN:
                self.score += 1
            else:
                self.score +=0.25
        
        if self.tape_height< self.MAX_TAPE_HEIGHT:
            if self.tape_height < self.REAL_TAPE_HEIGHT_MAX and self.tape_height > self.REAL_TAPE_HEIGHT_MIN:
                self.score += 1
            else:
                self.score +=0.25
        self.score /= NUMBER_OF_TESTS
    def get_score(self):
        self.set_quality_score();
        return self.score;
    