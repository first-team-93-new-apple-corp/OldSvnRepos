package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.subsystems.Drive;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator;
import org.usfirst.frc.team93.robot.subsystems.SPIEncoders;
import org.usfirst.frc.team93.robot.subsystems.SmartDashboardWriter;
import org.usfirst.frc.team93.robot.utilities.CameraServer2;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

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
	public static SPIEncoders spi;
	public static GearManipulator gearManipulator;
	public static SmartDashboardWriter smartDashboardWriter;
	
	Command spiPrinter;
	UsbCamera cam0;
	UsbCamera cam1;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit()
	{
		drive = new Drive();
		spi = new SPIEncoders(Drive.DRIVE_DIRECTION_SPI_LEFT_FRONT, Drive.DRIVE_DIRECTION_SPI_LEFT_BACK, Drive.DRIVE_DIRECTION_SPI_RIGHT_FRONT, Drive.DRIVE_DIRECTION_SPI_RIGHT_BACK, Drive.LFSPI_dummy, Drive.LBSPI_dummy, Drive.RFSPI_dummy, Drive.RBSPI_dummy);
		gearManipulator = new GearManipulator();
		smartDashboardWriter = new SmartDashboardWriter();
		
		oi = new OI();
		
		if (Robot.isReal())
		{
			cam0 = CameraServer2.getInstance().startAutomaticCapture(0);
			cam0.setResolution(64, 48);
			cam0.setFPS(10);
			
			cam1 = CameraServer2.getInstance().startAutomaticCapture(1);
			cam1.setResolution(320, 240);
			cam1.setFPS(20);
		}
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
		// start autonomous
		Drive.resetSensors();
		Drive.DRIVE_DIRECTION_SPI_LEFT_FRONT.reset();
		Drive.DRIVE_DIRECTION_SPI_LEFT_BACK.reset();
		Drive.DRIVE_DIRECTION_SPI_RIGHT_FRONT.reset();
		Drive.DRIVE_DIRECTION_SPI_RIGHT_BACK.reset();
		
		Drive.CRAB_DRIVE_DIRECTION_LEFT_FRONT.reset();
		Drive.CRAB_DRIVE_DIRECTION_LEFT_BACK.reset();
		Drive.CRAB_DRIVE_DIRECTION_RIGHT_FRONT.reset();
		Drive.CRAB_DRIVE_DIRECTION_RIGHT_BACK.reset();
		
		Drive.CRAB_DRIVE_DIRECTION_LEFT_FRONT.disable();
		Drive.CRAB_DRIVE_DIRECTION_LEFT_BACK.disable();
		Drive.CRAB_DRIVE_DIRECTION_RIGHT_FRONT.disable();
		Drive.CRAB_DRIVE_DIRECTION_RIGHT_BACK.disable();
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
		// TODO REMOVEME
		// Drive.resetSensors(); // This will break things in a competition flow
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
