
package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.commands.DisplayDashboard;
import org.usfirst.frc.team93.robot.commands.DoNothingCommand;
import org.usfirst.frc.team93.robot.commands.DriveForward;
import org.usfirst.frc.team93.robot.commands.LowBarNoShotAuto;
import org.usfirst.frc.team93.robot.commands.LowBarShotAuto;
import org.usfirst.frc.team93.robot.commands.PortcullisAutonomous;
//import org.usfirst.frc.team93.robot.commands.TeleopCamera;
import org.usfirst.frc.team93.robot.subsystems.BoulderAcquisition;
import org.usfirst.frc.team93.robot.subsystems.Drive;
import org.usfirst.frc.team93.robot.subsystems.LinearActuator;
import org.usfirst.frc.team93.robot.subsystems.Manipulator;
import org.usfirst.frc.team93.robot.subsystems.Scaler;
import org.usfirst.frc.team93.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot
{

	public static OI oi;
	public static Drive drive;
	public static Shooter shooter;
	public static BoulderAcquisition bacquisition;
	public static Scaler scaler;
	public static RobotMap robotMap;
	public static Command autonomousCommand;
	public static Command limitCommand;
	public static DisplayDashboard dashboard;
	public static Manipulator defenseManipulator;
	public static LinearActuator linearActuator;
	// public static TeleopCamera cam;

	SendableChooser autonomousSelector;
	SendableChooser manipulatorLimitSelector;
	// public static UDPReceiver UDP;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit()
	{
		robotMap = new RobotMap();
		drive = new Drive();
		shooter = new Shooter();
		bacquisition = new BoulderAcquisition();
		scaler = new Scaler();
		defenseManipulator = new Manipulator();
		linearActuator = new LinearActuator();
		oi = new OI();
		// UDP = new UDPReceiver();

		// CameraServer server = CameraServer.getInstance();
		// server.setQuality(50);
		// server.startAutomaticCapture("cam0");

		// manipulatorLimitSelector = new SendableChooser();
		// manipulatorLimitSelector.addDefault("Portcullis Limits", new
		// SetManipulatorLimits(1.975, 2.17));
		// manipulatorLimitSelector.addObject("Cheval Limits", new
		// SetManipulatorLimits(1.975, 2.17));
		// SmartDashboard.putData("Defense A", manipulatorLimitSelector);

		autonomousSelector = new SendableChooser();
		autonomousSelector.addDefault("Default No Action Autonomous", new DoNothingCommand());
		autonomousSelector.addObject("Low Bar Autonomous No Shot", new LowBarNoShotAuto());
		autonomousSelector.addObject("Low Bar Autonomous With Shot", new LowBarShotAuto());
		autonomousSelector.addObject("Rock Wall Autonomous No Shot", new DriveForward(750, 5, 1.5));
		autonomousSelector.addObject("Portcullis Autonomous", new PortcullisAutonomous());
		SmartDashboard.putData("Auto", autonomousSelector);
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

		// limitCommand = (Command) manipulatorLimitSelector.getSelected();
		//
		// if (limitCommand != null)
		// {
		// limitCommand.start();
		// }

		autonomousCommand = (Command) autonomousSelector.getSelected();

		if (autonomousCommand != null)
		{
			autonomousCommand.start();
		}
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
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();

		// UDP.start();

		// UDP.start();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic()
	{
		SmartDashboard.putBoolean("Ball Present", Shooter.ballPresent());

		// System.out.println("Left enc: " + RobotMap.LEFT_DRIVE_ENCODER.get());
		// System.out.println("Right enc: " +
		// RobotMap.RIGHT_DRIVE_ENCODER.get());

		System.out.println("Pot Angle: " + RobotMap.linAcqPotentiometer.pidGet());
		// System.out.println("Voltage: " +
		// RobotMap.linAcqPotentiometer.getVoltage());

		// RobotMap.LEFT_DRIVE_ENCODER.get() + " Trigger Value = "
		// + RobotMap.LEFT_DRIVE_TRIGGER.getTriggerState());
		// System.out.println("Ball Prescence = " + Shooter.ballPresent());

		// System.out.println("Manipulator Potentiometer = " +
		// RobotMap.manipulatorPotentiometer.getAngle());

		/*
		 * 
		 * 
		 * Do Not Comment Me
		 * 
		 * 
		 */
		Scheduler.getInstance().run();
		/*
		 * 
		 * Do Not Comment Me
		 * 
		 * 
		 */

		// System.out.print("Left Sensor Reading = " +
		// RobotMap.EncoderVelocityShooterLeft.pidGet());
		// System.out.println(" Right Sensor Reading = " +
		// RobotMap.EncoderVelocityShooterRight.pidGet());

		// System.out.print("Left Motor Setting = " +
		// RobotMap.LEFT_SHOOTER.get());
		// System.out.println(" Right Motor Setting = " +
		// RobotMap.RIGHT_SHOOTER.get());

		// System.out.print("Left Error = " +
		// RobotMap.leftFiringSpeedControl.get());
		// System.out.println(" Right Error = " +
		// RobotMap.rightFiringSpeedControl.get());

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
