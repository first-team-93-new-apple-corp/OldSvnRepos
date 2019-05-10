'''
Created on Mar 25, 2017

@author: Admin93
'''
from collections import namedtuple

class Ship:
    '''
    classdocs
    '''
    ShipSide_t = namedtuple("ShipSide_t", "RIGHT LEFT CENTER")._make(range(3))

    def __init__(self, params):
        '''
        Constructor
        '''
        pass
        