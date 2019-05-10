
package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.commands.BasicDrive;
//import org.usfirst.frc.team93.robot.commands.TeleopCamera;
import org.usfirst.frc.team93.robot.commands.DualDrive;
import org.usfirst.frc.team93.robot.commands.JoystickDrive;
import org.usfirst.frc.team93.robot.subsystems.DriveSub;
import org.usfirst.frc.team93.robot.subsystems.GearshiftSub;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class Robot extends IterativeRobot
{
	// public static TeleopCamera cam;
	public static OI oi;
	public static RobotMap robotMap;
	Command autonomousCommand;
	Command JoystickDrive;
	Command DualDrive;
	public static GearshiftSub gearShift;
	public static DriveSub driveSub;
	SendableChooser chooser;
	Command gettingPowerDistribution;

	public static BasicDrive basicDrive;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit()
	{
		robotMap = new RobotMap();
		oi = new OI();
		gearShift = new GearshiftSub();
		chooser = new SendableChooser();
		JoystickDrive = new JoystickDrive();
		DualDrive = new DualDrive();
		driveSub = new DriveSub();
		basicDrive = new BasicDrive();
		CameraServer server = CameraServer.getInstance();
		// server.setQuality(50);
		server.startAutomaticCapture(0);
		CameraServer servertwo = CameraServer.getInstance();
		// server.setQuality(50);
		servertwo.startAutomaticCapture(1);

		// chooser.addObject("My Auto", new MyAutoCommand());
		// SmartDashboard.putData("Auto mode", chooser);
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
		autonomousCommand = (Command) chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
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
		// These are the different methods for driving the robot

		// JoystickDrive.start(); //Drives using a single joystick
		// DualDrive.start(); // Drives using two joysticks
		// gettingPowerDistribution.start();
		basicDrive.start(); // Drives using a playstation controller
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic()
	{
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic()
	{
		LiveWindow.run();
	}
}
