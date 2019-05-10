'''
Created on Mar 23, 2017

@author: Colby McKinley
@version: 1.0
'''
import math
from Ship import Ship
class CameraData:
    m_inches_left_right = 0.0;
    DIAMETER = 3.25;
    CIRCUMPRANCE = math.pi * DIAMETER;
    m_inches_forward = 0.0;
    current_side = None;
    m_quality_score = 0.0;
    def __init__(self):
        pass
    def set_all(self,inches_left_right, side, inches_forwad, score):
        self.m_inches_left_right = inches_left_right
        self.current_side = side
        self.m_inches_forward = inches_forwad
        self.m_quality_score = score
        self.db_side = self.current_side
    def convert_inches_to_encoder_ticks(self, inches):
        return inches * 360 / self.CIRCUMPRANCE

    def get_left_right_distance_inches(self):
        return self.m_inches_left_right

    def get_foward_distance_inches(self):
        return self.m_inches_forward

    def get_left_right_distance(self, side):
        if self.current_side == Ship.ShipSide_t.LEFT:
            return self.convert_inches_to_encoder_ticks(self.m_inches_left_right)
        elif self.current_side == Ship.ShipSide_t.RIGHT:
            return -self.convert_inches_to_encoder_ticks(self.m_inches_left_right)
        else:
            return 0
   
    def get_forward_distance(self):
        return self.convert_inches_to_encoder_ticks(self.m_inches_left_right)

    def get_score(self):
        return self.m_quality_score
    def to_string(self):
        return self.get_forward_distance() + "," + self.get_left_right_distance(self.current_side) + "," + self.get_score()