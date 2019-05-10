# import the necessary packages
from threading import Thread
from picamera import PiCamera
import numpy as np
import time

class PiCameraVideoStream:

    grabbed = True
    frame = None
    
    def __init__(self):
        # initialize the PiCamera stream and read the first frame
        # from the stream
        self.stream = PiCamera()
        self.stream.resolution = (640, 480)
        self.raw_capture = np.empty((640, 480, 3), dtype=np.uint8)
        # initialize the variable used to indicate if the thread should
        # be stopped
        self.stopped = False
        # set self as active
        self.active = True
        # variable that holds the thread
        self.thread = Thread(target = self.update, args = ())
        self.thread.daemon = True
        # wait for the picam to warm up
        import time
        time.sleep(1)

    def start(self):
        # can't start a released stream
        if not self.active:
            return
        # start the thread to read frames from the video stream
        self.thread.start()
 
    def update(self):
        # keep looping infinitely until the thread is stopped
        while True:
            # if the thread indicator variable is set, stop the thread
            if self.stopped or not self.active:
                return
            # otherwise, read the next frame from the stream
            # capture from the camera
            self.raw_capture = np.empty((640 * 480 * 3), dtype = np.uint8)
            self.stream.capture(self.raw_capture, 'bgr', use_video_port = True)
            # reshape to usable format
            self.raw_capture = self.raw_capture.reshape((480, 640, 3))
            self.frame = self.raw_capture
 
    def read(self):
        # return None if not active
        if not self.active:
            return None
        # return the frame most recently read
        return (self.grabbed, self.frame)
    
    def stop(self):
        # do nothing if already released
        if not self.active:
            return
        # indicate that the thread should be stopped
        self.stopped = True
    
    def release(self):
        #release the camera and kill the thread
        self.stop()
        self.active = False
        self.thread = None
        self.stream.close()
