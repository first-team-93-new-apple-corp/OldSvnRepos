from VisionProcessor2017 import VisionProcessor2017
from Scheduler import Scheduler
'''
This is the main file.  It calls the functions to properly run.  There are two modes, a regular and debug.
'''

vp_main = VisionProcessor2017()
if vp_main.debug_mode:
    run_scheduler = Scheduler(vp_main)
    #run_scheduler.start()
    run_scheduler.task_run()
else:
    #vp_main.start()
    vp_main.processor()
