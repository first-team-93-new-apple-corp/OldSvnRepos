# python point_data.py windowed
# Uses the display for debug
# python point_data.py
# run this on the robot

import cv2
import platform
from VisionProcessor import VisionProcessor
import sys
import numpy as np
import socket

# blank method
def nothing(x):
    pass

show_errors = False
# prints an error output
def print_error(o):
    if show_errors:
        print str(o)

# image resolution
width = 640
height = 480
resolution = (width, height)

# sets the camera's exposure
def set_exposure(x):
    if on_pi:
        cam.stream.resolution = resolution
        cam.stream.exposure_mode = 'off'
        cam.stream.shutter_speed = 1000
        cam.stream.awb_mode = 'off'
        cam.stream.awb_gains = (1.2, 1.9)
        cam.stream.exposure_compensation = 0
        cam.stream.zoom = (0.0, 0.0, 1.0, 1.0)
    else:
        # CAP_PROP_EXPOSURE is 15
        cam.stream.set(15, -6 * x / 10000.0)
        # CAP_PROP_AUTO_EXPOSURE is 21
        cam.stream.set(21, 0)
        # CAP_PROP_GAIN is 14
        cam.stream.set(14, 0)
        # CAP_PROP_WHITE_BALANCE_BLUE_U is 17
        # CAP_PROP_WHITE_BALANCE_RED_V is 26
        cam.stream.set(17, 0)
        cam.stream.set(26, 0)

# open the video capture
on_pi = True
# check if we're on the pi
# if we're on the pi
if 'arm' in platform._syscmd_uname('-a').lower():
    on_pi = True
    from PiCameraVideoStream import PiCameraVideoStream
    cam = PiCameraVideoStream()
    # set resolution
    cam.stream.resolution = (width, height)
    # set up exposure
    # TODO
    # set FOV and adjustment values
    fov = (60.0, 60.0)
    adjust = (1.0, 1.0 * (float(width) / float(height)))
# if we're on a normal computer with a webcam
else:
    on_pi = False
    from WebcamVideoStream import WebcamVideoStream
    cam = WebcamVideoStream(0)
    # set FOV and adjustment values
    fov = (60.0, 60.0)
    adjust = (1.5, 1.5 * (float(width) / float(height)))

cam.start()

# instantiate the vision processor
windowed = False
if len(sys.argv) >= 2:
    if 'win' in sys.argv[1]:
        windowed = True
print sys.argv
vp = VisionProcessor(cam, resolution, fov, adjust, windowed = windowed)
vp.lowerHSV = np.array([55, 237, 30])
vp.upperHSV = np.array([84, 255, 159])

if windowed:
    # Create a window
    cv2.namedWindow('sliders')
    cv2.namedWindow('image', cv2.WINDOW_NORMAL)
    cv2.resizeWindow('image', width, height)

    # create sliders
    cv2.createTrackbar('HMin' ,'sliders', 0, 179, nothing) # Hue is from 0-179 for Opencv
    cv2.createTrackbar('SMin', 'sliders', 0, 255, nothing)
    cv2.createTrackbar('VMin', 'sliders', 0, 255, nothing)
    cv2.createTrackbar('HMax', 'sliders', 0, 179, nothing)
    cv2.createTrackbar('SMax', 'sliders', 0, 255, nothing)
    cv2.createTrackbar('VMax', 'sliders', 0, 255, nothing)
    cv2.createTrackbar('Exposure', 'sliders', 0, 10000, set_exposure)

    # set default value for sliders.
    cv2.setTrackbarPos('HMin', 'sliders', 55)
    cv2.setTrackbarPos('SMin', 'sliders', 237)
    cv2.setTrackbarPos('VMin', 'sliders', 30)
    cv2.setTrackbarPos('HMax', 'sliders', 84)
    cv2.setTrackbarPos('SMax', 'sliders', 255)
    cv2.setTrackbarPos('VMax', 'sliders', 159)
    cv2.setTrackbarPos('Exposure', 'sliders', 5581)
set_exposure(5581)

display_open = True
close_window = False

hsvfilter_mode = False
if len(sys.argv) >= 3:
    if 'f' in sys.argv[2] or 'h' in sys.argv[2]:
        hsvfilter_mode = True

ip = "10.0.93.2"
port = 5005

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
# Send a UDP packet
def send_packet(ip, port, data):
    # convert data to str
    status_bad = 0
    status_good = 1
    if data is None:
        data = (0.0, 0.0, 0.0, 0.0) + (status_bad,)
    else:
        data = data + (status_good,)
    data_str = ""
    for i in range(len(data)):
        if i != 0:
            data_str += ", "
        data_str += str(data[i])
    # send the UDP packet
    sock.sendto(data_str, (ip, port))
    print "Sent: " + str(data_str)

# checks whether the program should be running or not
def window_open():
    if not windowed:
        return True
    return display_open and not close_window

def update_wait():
    while True:
        data = vp.get_data()
        send_packet(ip, port, data)
        k = cv2.waitKey(1) & 0xFF
        # close if escape was pressed
        if k == 27:
            close_window = True
            break

def update_display():
    global display_open, close_window, hsvfilter_mode
    while window_open():
        # update window state
        display_open = True#cv2.getWindowProperty('image', 0) >= 0 and cv2.getWindowProperty('sliders', 0) >= 0
        if not display_open or close_window:
            break
        image = cam.read()[1]
        # Display output image
        if image is not None:
            data = vp.get_data()
            print data
            if hsvfilter_mode:
                image = vp.threshold_debug(image, *vp.get_hsv_bounds())
            else:
                if data is not None:
                    draw_on_image(image, *data)
            cv2.imshow('image', image)
        else:
            print "FAILED TO CAPTURE IMAGE!"
        # update the windows
        k = cv2.waitKey(1) & 0xFF
        # close if escape was pressed
        if k == 27:
            close_window = True
            break

def draw_on_image(img, center_x, center_y, distance, side):
    # draw circle at the center
    cv2.circle(img, (int(center_x), int(center_y)), 10, (0, 255, 0), -1)
    # draw forward distance indicator
    cv2.putText(img, "DISTANCE: " + str(round(distance, 2)), (int(center_x) - 30, int(center_y) - 40), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 255, 0))
    # draw left/right distance indicator
    cv2.putText(img, "SIDE: " + str(round(side, 2)), (int(center_x) - 30, int(center_y) - 20), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 255, 0))

def update_vp():
    # while the program is running
    while window_open():
        # run the vision processor
        vp.run()

# start the display updating thread
from threading import Thread
update_vision_processor = Thread(target = update_vp)
update_vision_processor.daemon = True
update_vision_processor.start()

# main loop
if windowed:
    update_display()
else:
    update_wait()

# release the video stream and its thread
cam.release()
# close windows at the end
cv2.destroyAllWindows()
