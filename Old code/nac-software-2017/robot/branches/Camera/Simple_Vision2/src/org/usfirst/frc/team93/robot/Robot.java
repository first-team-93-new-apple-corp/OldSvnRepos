package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.commands.ExampleCommand;
import org.usfirst.frc.team93.robot.commands.CameraSwitcher;
import org.usfirst.frc.team93.robot.subsystems.Camera;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.wpilibj.CameraServer2;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * Uses the CameraServer class to automatically capture video from a USB webcam
 * and send it to the FRC dashboard without doing any vision processing. This
 * is the easiest way to get camera images to the dashboard. Just add this to the
 * robotInit() method in your program.
 */
public class Robot extends IterativeRobot {
	public static Camera camera;
	public static OI oi;
	public static RobotMap robotMap;
	SendableChooser<Command> chooser = new SendableChooser<>();
	
	public static boolean hasCameraSwitched = false;
	UsbCamera cam0;
	UsbCamera cam1;
	
	@Override
	public void robotInit() {
		camera = new Camera();
		oi = new OI();

		Camera.camera = Camera.camera * -1;
        Robot.hasCameraSwitched = false;
        
		chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);
		
		cam0 = new UsbCamera("USB Camera 0", 0);
		CameraServer2.getInstance().addCamera(cam0);
	    VideoSink server0 = CameraServer2.getInstance().addServer("serve_" + cam0.getName());
	    server0.setSource(cam0);
	    cam0.setResolution(800, 600);
	    cam0.setFPS(10);
	    cam1.setResolution(400, 300);
	    cam1.setFPS(20);
	   
		
	    cam1 = new UsbCamera("USB Camera 1", 1);
		CameraServer2.getInstance().addCamera(cam1);
	    VideoSink server1 = CameraServer2.getInstance().addServer("serve_" + cam1.getName());
	    server1.setSource(cam1);
	}
	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit()
	{

	}

	@Override
	public void disabledPeriodic()
	{
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit()
	{
		CameraServer2.getInstance().startAutomaticCapture(0);
		// autonomousCommand = (Command) chooser.getSelected();
		// autonomousCommand = new Autonomous();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		// if (autonomousCommand != null)
		// autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic()
	{
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit()
	{

	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic()
	{
		if (hasCameraSwitched == true)
		{
			if(Camera.camera == -1)
			{
				/*CameraServer2.getInstance().removeCamera("USB Camera 0");
				CameraServer2.getInstance().removeServer("serve_USB Camera 0");
				cam0 = null;*/
				
				cam1 = new UsbCamera("USB Camera 1", 1);
				CameraServer2.getInstance().addCamera(cam1);
			    VideoSink server1 = CameraServer2.getInstance().addServer("serve_" + cam1.getName());
			    server1.setSource(cam1);
			}
			else if(Camera.camera == 1)
			{
				cam1 = null;
				CameraServer2.getInstance().removeCamera("USB Camera 1");
				CameraServer2.getInstance().removeServer("serve_USB Camera 1");
				
				
				/*cam0 = new UsbCamera("USB Camera 0", 0);
				CameraServer2.getInstance().addCamera(cam0);
			    VideoSink server0 = CameraServer2.getInstance().addServer("serve_" + cam0.getName());
			    server0.setSource(cam0);*/
			}
		}
		hasCameraSwitched = false;
		Scheduler.getInstance().run();		
	}
	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic()
	{
		//LiveWindow.run();
	}
}
