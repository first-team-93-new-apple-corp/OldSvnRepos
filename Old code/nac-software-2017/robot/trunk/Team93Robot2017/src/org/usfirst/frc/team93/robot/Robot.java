package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.RobotMap.Platform;
import org.usfirst.frc.team93.robot.commands.Autonomous;
import org.usfirst.frc.team93.robot.commands.Autonomous.AutonomousPlan;
import org.usfirst.frc.team93.robot.subsystems.Climber;
import org.usfirst.frc.team93.robot.subsystems.Drive;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator;
import org.usfirst.frc.team93.robot.subsystems.SPIEncoders;
import org.usfirst.frc.team93.robot.subsystems.SmartDashboardWriter;
import org.usfirst.frc.team93.robot.subsystems.VisionProcessor;
import org.usfirst.frc.team93.robot.utilities.crabdrive.CrabDriveCoordinator.CrabDriveMode;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
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
	public static Preferences preferences;
	public static OI oi;
	public static Drive drive;
	public static SPIEncoders spi;
	public static GearManipulator gearManipulator;
	public static Climber climber;
	public static VisionProcessor visionProcessor;
	public static SmartDashboardWriter smartDashboardWriter;
	
	UsbCamera cam0;
	UsbCamera cam1;
	public static Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	
	enum camera_config_t
	{
		CAMERAS_DISABLED, CAMERAS_SINGLE_TEST, CAMERAS_FULL
	};
	
	final public camera_config_t camerasEnabled = camera_config_t.CAMERAS_FULL;
	
	public boolean usePreferences = true;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit()
	{
		if (usePreferences)
		{
			preferences = Preferences.getInstance();
			switch (preferences.getString("Robot", "NULL"))
			{
				case "COMPETITION":
					RobotMap.platform = Platform.COMPETITION;
					System.out.println("Initializing competition robot pins.");
					break;
				case "PRACTICE":
					RobotMap.platform = Platform.PRACTICE;
					System.out.println("Initializing practice robot pins.");
					break;
				case "NULL":
					RobotMap.platform = Platform.COMPETITION;
					System.out.println("Robot not tagged - Defaulting to Competition robot pins.");
					break;
				default:
					System.out.println("Error - corrupted tag. Please tag with \"COMPETITION\" or \"PRACTICE\".");
					System.out.println("Current tag: " + preferences.getString("Robot", "NULL"));
			}
		}
		drive = new Drive();
		spi = new SPIEncoders(Drive.DRIVE_DIRECTION_SPI_LEFT_FRONT, Drive.DRIVE_DIRECTION_SPI_LEFT_BACK, Drive.DRIVE_DIRECTION_SPI_RIGHT_FRONT, Drive.DRIVE_DIRECTION_SPI_RIGHT_BACK, Drive.LFSPI_dummy,
				Drive.LBSPI_dummy, Drive.RFSPI_dummy, Drive.RBSPI_dummy);
		gearManipulator = new GearManipulator();
		climber = new Climber();
		// visionProcessor = new VisionProcessor();
		// initialized last since it prints the other subsystems' sensor values
		smartDashboardWriter = new SmartDashboardWriter();
		// initialized more last since it uses all the commands
		oi = new OI();
		
		// init cameras
		if (Robot.isReal())
		{
			if (camerasEnabled == camera_config_t.CAMERAS_FULL || camerasEnabled == camera_config_t.CAMERAS_SINGLE_TEST)
			{
				cam0 = CameraServer.getInstance().startAutomaticCapture(0);
				cam0.setResolution(160, 120);
				cam0.setFPS(10);
				System.out.println("Camera 0 Enabled");
			}
			
			if (camerasEnabled == camera_config_t.CAMERAS_FULL)
			{
				cam1 = CameraServer.getInstance().startAutomaticCapture(1);
				cam1.setResolution(320, 240);
				cam1.setFPS(10);
			}
		}
		
		// init auto commands
		chooser.addDefault("Auto Plan No Autonomous", new Autonomous(AutonomousPlan.NO_AUTONOMOUS));
		chooser.addObject("Auto Plan Drive Forward", new Autonomous(AutonomousPlan.PLAN_DRIVE_FORWARD_SIDE));
		chooser.addObject("Auto Plan Deliver Center Gear", new Autonomous(AutonomousPlan.PLAN_DELIVER_GEAR_CENTER));
		chooser.addObject("Auto Plan Deliver Left Gear Red", new Autonomous(AutonomousPlan.PLAN_DELIVER_GEAR_LEFT_RED));
		chooser.addObject("Auto Plan Deliver Right Gear Red", new Autonomous(AutonomousPlan.PLAN_DELIVER_GEAR_RIGHT_RED));
		chooser.addObject("Auto Plan Deliver Left Gear Blue", new Autonomous(AutonomousPlan.PLAN_DELIVER_GEAR_LEFT_BLUE));
		chooser.addObject("Auto Plan Deliver Right Gear Blue", new Autonomous(AutonomousPlan.PLAN_DELIVER_GEAR_RIGHT_BLUE));
		
		SmartDashboard.putData("Autonomous Plan", chooser);
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
		
		autonomousCommand = chooser.getSelected();
		if (autonomousCommand != null)
		{
			autonomousCommand.cancel();
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
		// TODO REMOVEME
		// Drive.resetSensors(); // This will break things in a competition flow
		if (autonomousCommand != null)
		{
			autonomousCommand.cancel();
		}
		Drive.CRAB_DRIVE_COORDINATOR.setMode(CrabDriveMode.FieldCentric);
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
