# import the necessary packages
from threading import Thread
import cv2
class WebcamVideoStream:
    
    def __init__(self, src = 0):
        # initialize the video camera stream and read the first frame
        # from the stream
        self.stream = cv2.VideoCapture(src)
        self.grabbed, self.frame = self.stream.read()
        # initialize the variable used to indicate if the thread should
        # be stopped
        self.stopped = False
        # set self as active
        self.active = True
        # variable that holds the thread
        self.thread = Thread(target = self.update, args = ())
        self.thread.daemon = True

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
            self.grabbed, self.frame = self.stream.read()
 
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
        self.stream.release()
        self.thread = None