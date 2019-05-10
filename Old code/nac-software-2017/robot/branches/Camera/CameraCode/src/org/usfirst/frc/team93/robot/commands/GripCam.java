package org.usfirst.frc.team93.robot.commands;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.vision.VisionThread;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.util.GripPipeline;
/**
 *
 */
public class GripCam extends Command {

	GripPipeline gp;
	double centerX = 0.0;

	UsbCamera camera;
	//camera.setResoultion(imageWidth, imageHeight);
    public GripCam() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	//gp = new GripPipeline();
    }

    
    // Called just before this Command runs the first time
    protected void initialize() {
    	//CameraServer.getInstance().startAutomaticCapture(0);
//    	camera = CameraServer.getInstance().startAutomaticCapture("basic", 0);
//        camera.setResolution(320, 240);
    	camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setResolution(320, 240);
    	
//        new Thread(() -> {
//            CvSink cvSink = CameraServer.getInstance().getVideo();
//            CvSource outputStream = CameraServer.getInstance().putVideo("Blur", 640, 480);
//            
//            Mat source = new Mat();
//            Mat output = new Mat();
//            
//            while(!Thread.interrupted()) {
//                cvSink.grabFrame(source);
//                Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
//                outputStream.putFrame(output);
//            }
        // all code below is not tested.  Should be tested on monday
//        final Object imgLock = new Object();
//
//        VisionThread visionThread;
//        visionThread = new VisionThread(camera, gp, pipeline -> {
//            if (!pipeline.filterContoursOutput().isEmpty()) {
//                Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
//                synchronized (imgLock) {
//                    centerX = r.x + (r.width / 2);
//                }
//            }
//        });
//        visionThread.start();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//gp.process(CameraServer.getInstance().getVideo().grabFrame(image));
 
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
