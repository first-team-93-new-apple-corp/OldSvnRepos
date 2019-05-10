package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.RobotMapSimulation;
import org.usfirst.frc.team93.robot.commands.CrabDriveContinuous;
import org.usfirst.frc.team93.robot.utilities.AnalogGyroPIDSource;
import org.usfirst.frc.team93.robot.utilities.GyroPIDSource;
import org.usfirst.frc.team93.robot.utilities.MedianPIDSource;
import org.usfirst.frc.team93.robot.utilities.MovingDummyPIDSource;
import org.usfirst.frc.team93.robot.utilities.NavxGyroPIDSource;
import org.usfirst.frc.team93.robot.utilities.PIDOutputExtended;
import org.usfirst.frc.team93.robot.utilities.PIDOutputGroup;
import org.usfirst.frc.team93.robot.utilities.SPIEncoderPIDSource;
import org.usfirst.frc.team93.robot.utilities.SimEncoderPIDSource;
import org.usfirst.frc.team93.robot.utilities.Team93PIDController;
import org.usfirst.frc.team93.robot.utilities.WheelAnglePIDSource;
import org.usfirst.frc.team93.robot.utilities.crabdrive.CrabDriveCoordinator;
import org.usfirst.frc.team93.robot.utilities.crabdrive.CrabDriveDirectionMotor;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Drive extends Subsystem
{
	
	// the orientation of the robot.
	// 1.0 for front facing, -1.0 for backwards facing.
	private static double m_orientation = 1.0;
	
	/// Sensors
	// Encoders
	public static Encoder DRIVE_ENCODER_LEFT_FRONT;
	public static Encoder DRIVE_ENCODER_LEFT_BACK;
	public static Encoder DRIVE_ENCODER_RIGHT_FRONT;
	public static Encoder DRIVE_ENCODER_RIGHT_BACK;
	// SPI Encoders
	public static SPI DRIVE_SPI_LEFT_FRONT;
	public static SPI DRIVE_SPI_LEFT_BACK;
	public static SPI DRIVE_SPI_RIGHT_FRONT;
	public static SPI DRIVE_SPI_RIGHT_BACK;
	// Gyro
	public static GyroPIDSource GYRO;
	
	/// PID Sources
	// Crab Drive Speed PIDSource
	public static MedianPIDSource DRIVE_ENCODERS;
	// Crab Drive Direction PIDSources
	public static WheelAnglePIDSource DRIVE_DIRECTION_SPI_LEFT_FRONT;
	public static WheelAnglePIDSource DRIVE_DIRECTION_SPI_LEFT_BACK;
	public static WheelAnglePIDSource DRIVE_DIRECTION_SPI_RIGHT_FRONT;
	public static WheelAnglePIDSource DRIVE_DIRECTION_SPI_RIGHT_BACK;
	// Aggregation of the 4 SPIEncoderPIDSources
	public static MedianPIDSource DRIVE_DIRECTION_SPI;
	
	/// Actuators
	// Drive Talons
	public static SpeedController DRIVE_LEFT_FRONT_TALON;
	public static SpeedController DRIVE_LEFT_BACK_TALON;
	public static SpeedController DRIVE_RIGHT_FRONT_TALON;
	public static SpeedController DRIVE_RIGHT_BACK_TALON;
	// Drive Talons
	public static SpeedController DRIVE_DIRECTION_LEFT_FRONT_TALON;
	public static SpeedController DRIVE_DIRECTION_LEFT_BACK_TALON;
	public static SpeedController DRIVE_DIRECTION_RIGHT_FRONT_TALON;
	public static SpeedController DRIVE_DIRECTION_RIGHT_BACK_TALON;
	// Motors
	public static PIDOutputExtended DRIVE_LEFT_FRONT;
	public static PIDOutputExtended DRIVE_LEFT_BACK;
	public static PIDOutputExtended DRIVE_RIGHT_FRONT;
	public static PIDOutputExtended DRIVE_RIGHT_BACK;
	// Crab Drive Wheel Direction motors
	public static PIDOutputExtended DRIVE_DIRECTION_LEFT_FRONT;
	public static PIDOutputExtended DRIVE_DIRECTION_LEFT_BACK;
	public static PIDOutputExtended DRIVE_DIRECTION_RIGHT_FRONT;
	public static PIDOutputExtended DRIVE_DIRECTION_RIGHT_BACK;
	
	/// PID Outputs
	// Drive
	public static PIDOutputGroup DRIVE_ALL;
	public static PIDOutputGroup DRIVE_LEFT;
	public static PIDOutputGroup DRIVE_RIGHT;
	
	public static PIDOutputGroup DRIVE_DIRECTION;
	
	// init SPIEncoders subsystem
	public static SimEncoderPIDSource LFSPI;
	public static SimEncoderPIDSource LBSPI;
	public static SimEncoderPIDSource RFSPI;
	public static SimEncoderPIDSource RBSPI;
	
	// We should be able to use either this implementation or the one above
	public static MovingDummyPIDSource LFSPI_dummy;
	public static MovingDummyPIDSource LBSPI_dummy;
	public static MovingDummyPIDSource RFSPI_dummy;
	public static MovingDummyPIDSource RBSPI_dummy;
	
	// CrabDriveDirectionMotor
	public static CrabDriveDirectionMotor CRAB_DRIVE_DIRECTION_LEFT_FRONT;
	public static CrabDriveDirectionMotor CRAB_DRIVE_DIRECTION_LEFT_BACK;
	public static CrabDriveDirectionMotor CRAB_DRIVE_DIRECTION_RIGHT_FRONT;
	public static CrabDriveDirectionMotor CRAB_DRIVE_DIRECTION_RIGHT_BACK;
	// CrabDrievCoordinator
	public static CrabDriveCoordinator CRAB_DRIVE_COORDINATOR;
	
	public static Team93PIDController DRIVE_DISTANCE_CONTROLLER;
	
	public static Timer timer = new Timer();
	
	public Drive()
	{
		
		// RobotMap will automatically handle pin configurations between
		// simulator and a real robot
		int leftFrontDriveEncoderPinA = RobotMap.getMap().LEFT_FRONT_DRIVE_ENCODER_PIN.A();
		int leftFrontDriveEncoderPinB = RobotMap.getMap().LEFT_FRONT_DRIVE_ENCODER_PIN.B();
		
		int leftBackDriveEncoderPinA = RobotMap.getMap().LEFT_BACK_DRIVE_ENCODER_PIN.A();
		int leftBackDriveEncoderPinB = RobotMap.getMap().LEFT_BACK_DRIVE_ENCODER_PIN.B();
		
		int rightFrontDriveEncoderPinA = RobotMap.getMap().RIGHT_FRONT_DRIVE_ENCODER_PIN.A();
		int rightFrontDriveEncoderPinB = RobotMap.getMap().RIGHT_FRONT_DRIVE_ENCODER_PIN.B();
		
		int rightBackDriveEncoderPinA = RobotMap.getMap().RIGHT_BACK_DRIVE_ENCODER_PIN.A();
		int rightBackDriveEncoderPinB = RobotMap.getMap().RIGHT_BACK_DRIVE_ENCODER_PIN.B();
		
		/// Sensors
		// Encoders
		DRIVE_ENCODER_LEFT_FRONT = new Encoder(leftFrontDriveEncoderPinA, leftFrontDriveEncoderPinB);
		DRIVE_ENCODER_LEFT_BACK = new Encoder(leftBackDriveEncoderPinA, leftBackDriveEncoderPinB);
		DRIVE_ENCODER_RIGHT_FRONT = new Encoder(rightFrontDriveEncoderPinA, rightFrontDriveEncoderPinB);
		DRIVE_ENCODER_RIGHT_BACK = new Encoder(rightBackDriveEncoderPinA, rightBackDriveEncoderPinB);
		
		// Gyro
		if (Robot.isReal())
		{
			GYRO = new NavxGyroPIDSource(new AHRS(RobotMap.getMap().MXP_PORT));
		}
		else
		{
			GYRO = new AnalogGyroPIDSource(new AnalogGyro(0));
		}
		
		/// PID Sources
		// Crab Drive Speed PIDSource
		DRIVE_ENCODERS = new MedianPIDSource(DRIVE_ENCODER_LEFT_FRONT, DRIVE_ENCODER_LEFT_BACK, DRIVE_ENCODER_RIGHT_FRONT, DRIVE_ENCODER_RIGHT_BACK);
		
		if (Robot.isReal())
		{
			// SPI Encoders
			DRIVE_SPI_LEFT_FRONT = new SPI(RobotMap.getMap().DRIVE_SPI_LEFT_FRONT_PORT);
			DRIVE_SPI_LEFT_BACK = new SPI(RobotMap.getMap().DRIVE_SPI_LEFT_BACK_PORT);
			DRIVE_SPI_RIGHT_FRONT = new SPI(RobotMap.getMap().DRIVE_SPI_RIGHT_FRONT_PORT);
			DRIVE_SPI_RIGHT_BACK = new SPI(RobotMap.getMap().DRIVE_SPI_RIGHT_BACK_PORT);
			
			// Crab Drive Direction PIDSources
			double SPIConversion = (360.0 / 8636) * (3600.0 / 3628.221398795739);
			DRIVE_DIRECTION_SPI_LEFT_FRONT = new SPIEncoderPIDSource(DRIVE_SPI_LEFT_FRONT, new DigitalOutput(RobotMap.getMap().DRIVE_SPI_LEFT_FRONT_CHIPSELECT));
			DRIVE_DIRECTION_SPI_LEFT_FRONT.setGain(SPIConversion);
			DRIVE_DIRECTION_SPI_LEFT_BACK = new SPIEncoderPIDSource(DRIVE_SPI_LEFT_BACK, new DigitalOutput(RobotMap.getMap().DRIVE_SPI_LEFT_BACK_CHIPSELECT));
			DRIVE_DIRECTION_SPI_LEFT_BACK.setGain(SPIConversion);
			DRIVE_DIRECTION_SPI_RIGHT_FRONT = new SPIEncoderPIDSource(DRIVE_SPI_RIGHT_FRONT, new DigitalOutput(RobotMap.getMap().DRIVE_SPI_RIGHT_FRONT_CHIPSELECT));
			DRIVE_DIRECTION_SPI_RIGHT_FRONT.setGain(SPIConversion);
			DRIVE_DIRECTION_SPI_RIGHT_BACK = new SPIEncoderPIDSource(DRIVE_SPI_RIGHT_BACK, new DigitalOutput(RobotMap.getMap().DRIVE_SPI_RIGHT_BACK_CHIPSELECT));
			DRIVE_DIRECTION_SPI_RIGHT_BACK.setGain(SPIConversion);
			
			/// Actuators
			// Drive Talons
			DRIVE_LEFT_FRONT_TALON = new CANTalon(RobotMap.getMap().DRIVE_LEFT_FRONT_PIN);
			DRIVE_LEFT_BACK_TALON = new CANTalon(RobotMap.getMap().DRIVE_LEFT_BACK_PIN);
			DRIVE_RIGHT_FRONT_TALON = new CANTalon(RobotMap.getMap().DRIVE_RIGHT_FRONT_PIN);
			DRIVE_RIGHT_BACK_TALON = new CANTalon(RobotMap.getMap().DRIVE_RIGHT_BACK_PIN);
			// Drive Direction Talons
			DRIVE_DIRECTION_LEFT_FRONT_TALON = new CANTalon(RobotMap.getMap().DRIVE_DIRECTION_LEFT_FRONT_PIN);
			DRIVE_DIRECTION_LEFT_BACK_TALON = new CANTalon(RobotMap.getMap().DRIVE_DIRECTION_LEFT_BACK_PIN);
			DRIVE_DIRECTION_RIGHT_FRONT_TALON = new CANTalon(RobotMap.getMap().DRIVE_DIRECTION_RIGHT_FRONT_PIN);
			DRIVE_DIRECTION_RIGHT_BACK_TALON = new CANTalon(RobotMap.getMap().DRIVE_DIRECTION_RIGHT_BACK_PIN);
		}
		else // simulation
		{
			RobotMap map = RobotMap.getMap();
			RobotMapSimulation simMap;
			
			if (map instanceof RobotMapSimulation)
			{
				simMap = (RobotMapSimulation) map;
				
				Encoder encoderLF = new Encoder(simMap.LEFT_FRONT_DIRECTION_ENCODER_PIN.A(), simMap.LEFT_FRONT_DIRECTION_ENCODER_PIN.B());
				Encoder encoderLB = new Encoder(simMap.LEFT_BACK_DIRECTION_ENCODER_PIN.A(), simMap.LEFT_BACK_DIRECTION_ENCODER_PIN.B());
				Encoder encoderRF = new Encoder(simMap.RIGHT_FRONT_DIRECTION_ENCODER_PIN.A(), simMap.RIGHT_FRONT_DIRECTION_ENCODER_PIN.B());
				Encoder encoderRB = new Encoder(simMap.RIGHT_BACK_DIRECTION_ENCODER_PIN.A(), simMap.RIGHT_BACK_DIRECTION_ENCODER_PIN.B());
				
				// Crab Drive Direction PIDSources
				DRIVE_DIRECTION_SPI_LEFT_FRONT = new SimEncoderPIDSource(encoderLF);
				DRIVE_DIRECTION_SPI_LEFT_FRONT.reset();
				DRIVE_DIRECTION_SPI_LEFT_BACK = new SimEncoderPIDSource(encoderLB);
				DRIVE_DIRECTION_SPI_LEFT_BACK.reset();
				DRIVE_DIRECTION_SPI_RIGHT_FRONT = new SimEncoderPIDSource(encoderRF);
				DRIVE_DIRECTION_SPI_RIGHT_FRONT.reset();
				DRIVE_DIRECTION_SPI_RIGHT_BACK = new SimEncoderPIDSource(encoderRB);
				DRIVE_DIRECTION_SPI_RIGHT_BACK.reset();
				
				DRIVE_LEFT_FRONT_TALON = new Talon(RobotMap.getMap().DRIVE_LEFT_FRONT_PIN);
				DRIVE_LEFT_BACK_TALON = new Talon(RobotMap.getMap().DRIVE_LEFT_BACK_PIN);
				DRIVE_RIGHT_FRONT_TALON = new Talon(RobotMap.getMap().DRIVE_RIGHT_FRONT_PIN);
				DRIVE_RIGHT_BACK_TALON = new Talon(RobotMap.getMap().DRIVE_RIGHT_BACK_PIN);
				// Drive Direction Talons
				DRIVE_DIRECTION_LEFT_FRONT_TALON = new Talon(RobotMap.getMap().DRIVE_DIRECTION_LEFT_FRONT_PIN);
				DRIVE_DIRECTION_LEFT_BACK_TALON = new Talon(RobotMap.getMap().DRIVE_DIRECTION_LEFT_BACK_PIN);
				DRIVE_DIRECTION_RIGHT_FRONT_TALON = new Talon(RobotMap.getMap().DRIVE_DIRECTION_RIGHT_FRONT_PIN);
				DRIVE_DIRECTION_RIGHT_BACK_TALON = new Talon(RobotMap.getMap().DRIVE_DIRECTION_RIGHT_BACK_PIN);
			}
			else
			{
				System.err.println("Error: Expected simulated environment");
				System.exit(1);
			}
		}
		
		LFSPI_dummy = new MovingDummyPIDSource();
		LBSPI_dummy = new MovingDummyPIDSource();
		RFSPI_dummy = new MovingDummyPIDSource();
		RBSPI_dummy = new MovingDummyPIDSource();
		
		// Drive Motors
		// These are the "real" PIDOutputs
		DRIVE_LEFT_FRONT = new PIDOutputExtended(DRIVE_LEFT_FRONT_TALON);
		DRIVE_LEFT_BACK = new PIDOutputExtended(DRIVE_LEFT_BACK_TALON);
		DRIVE_RIGHT_FRONT = new PIDOutputExtended(DRIVE_RIGHT_FRONT_TALON);
		DRIVE_RIGHT_BACK = new PIDOutputExtended(DRIVE_RIGHT_BACK_TALON);
		if (Robot.isReal())
		{
			DRIVE_RIGHT_FRONT.setGain(-1.0);
			DRIVE_RIGHT_BACK.setGain(-1.0);
		}
		// Drive Wheel Direction Motors
		DRIVE_DIRECTION_LEFT_FRONT = new PIDOutputExtended(DRIVE_DIRECTION_LEFT_FRONT_TALON);
		DRIVE_DIRECTION_LEFT_BACK = new PIDOutputExtended(DRIVE_DIRECTION_LEFT_BACK_TALON);
		DRIVE_DIRECTION_RIGHT_FRONT = new PIDOutputExtended(DRIVE_DIRECTION_RIGHT_FRONT_TALON);
		DRIVE_DIRECTION_RIGHT_BACK = new PIDOutputExtended(DRIVE_DIRECTION_RIGHT_BACK_TALON);
		/// PID Output Groups
		// Drive
		DRIVE_LEFT = new PIDOutputGroup(DRIVE_LEFT_FRONT, DRIVE_LEFT_BACK);
		DRIVE_RIGHT = new PIDOutputGroup(DRIVE_RIGHT_FRONT, DRIVE_RIGHT_BACK);
		DRIVE_ALL = new PIDOutputGroup(DRIVE_LEFT, DRIVE_RIGHT);
		
		// These are wheel motor slaves, which means their drive gains are
		// flipped by the CrabDriveDirectionMotors in order to find the shortest
		// path to the wheel angle setpoint.
		// these are passed into the CrabDrive components.
		// these are not in the outside scope because only the Crab Drive
		// Components should access these.
		PIDOutputExtended CRAB_DRIVE_LEFT_FRONT = new PIDOutputExtended(DRIVE_LEFT_FRONT);
		PIDOutputExtended CRAB_DRIVE_LEFT_BACK = new PIDOutputExtended(DRIVE_LEFT_BACK);
		PIDOutputExtended CRAB_DRIVE_RIGHT_FRONT = new PIDOutputExtended(DRIVE_RIGHT_FRONT);
		PIDOutputExtended CRAB_DRIVE_RIGHT_BACK = new PIDOutputExtended(DRIVE_RIGHT_BACK);
		// Crab Drive Outputs
		// CrabDriveDirectionMotor
		
		// we believe we can go as high as 0.06 or 0.07 on the field, but we're
		// using 0.05 for testing.
		double directionP = 0.04;
		double directionI = 0.001;
		double directionD = 0.0;
		double ILimit = 0.1;
		CRAB_DRIVE_DIRECTION_LEFT_FRONT = new CrabDriveDirectionMotor(directionP, directionI, directionD, ILimit, LFSPI_dummy, DRIVE_DIRECTION_LEFT_FRONT, CRAB_DRIVE_LEFT_FRONT);
		CRAB_DRIVE_DIRECTION_LEFT_BACK = new CrabDriveDirectionMotor(directionP, directionI, directionD, ILimit, LBSPI_dummy, DRIVE_DIRECTION_LEFT_BACK, CRAB_DRIVE_LEFT_BACK);
		CRAB_DRIVE_DIRECTION_RIGHT_FRONT = new CrabDriveDirectionMotor(directionP, directionI, directionD, ILimit, RFSPI_dummy, DRIVE_DIRECTION_RIGHT_FRONT, CRAB_DRIVE_RIGHT_FRONT);
		CRAB_DRIVE_DIRECTION_RIGHT_BACK = new CrabDriveDirectionMotor(directionP, directionI, directionD, ILimit, RBSPI_dummy, DRIVE_DIRECTION_RIGHT_BACK, CRAB_DRIVE_RIGHT_BACK);
		// CrabDriveCoordinator
		CRAB_DRIVE_COORDINATOR = new CrabDriveCoordinator(-0.03, 0, 0, GYRO, CRAB_DRIVE_LEFT_FRONT, CRAB_DRIVE_LEFT_BACK, CRAB_DRIVE_RIGHT_FRONT, CRAB_DRIVE_RIGHT_BACK, CRAB_DRIVE_DIRECTION_LEFT_FRONT, CRAB_DRIVE_DIRECTION_LEFT_BACK, CRAB_DRIVE_DIRECTION_RIGHT_FRONT, CRAB_DRIVE_DIRECTION_RIGHT_BACK);
		disableCrabDrive();
		
		// Drive Distance Control
		DRIVE_DISTANCE_CONTROLLER = new Team93PIDController(0.001, 0, 0, 0, DRIVE_ENCODERS, CRAB_DRIVE_COORDINATOR.speedReceiver);
		
		resetSensors();
	}
	
	@Override
	public void initDefaultCommand()
	{
		// Crab Drive Command
		setDefaultCommand(new CrabDriveContinuous());
	}
	
	/**
	 * Set left drive motors to a speed.
	 * 
	 * @param speed
	 */
	public static void setLeftMotors(double speed)
	{
		DRIVE_LEFT.set(speed);
	}
	
	/**
	 * Set right drive motors to a speed.
	 * 
	 * @param speed
	 */
	public static void setRightMotors(double speed)
	{
		DRIVE_RIGHT.set(speed);
	}
	
	/**
	 * Reset encoders and gyro.
	 */
	public static void resetSensors()
	{
		GYRO.reset();
		DRIVE_ENCODER_LEFT_FRONT.reset();
		DRIVE_ENCODER_LEFT_BACK.reset();
		DRIVE_ENCODER_RIGHT_FRONT.reset();
		DRIVE_ENCODER_RIGHT_BACK.reset();
	}
	
	/**
	 * Reset the encoders.
	 */
	public static void resetEncoders()
	{
		DRIVE_ENCODER_LEFT_FRONT.reset();
		DRIVE_ENCODER_LEFT_BACK.reset();
		DRIVE_ENCODER_RIGHT_FRONT.reset();
		DRIVE_ENCODER_RIGHT_BACK.reset();
	}
	
	/**
	 * Set all motors to a speed. Commonly used with 0.0 to stop the robot.
	 * 
	 * @param speed
	 */
	public static void setAllMotors(double speed)
	{
		setLeftMotors(speed);
		setRightMotors(speed);
	}
	
	/**
	 * Returns the orientation of the robot. 1.0 for front facing, -1.0 for
	 * backwards facing.
	 * 
	 * @return
	 */
	public static double getOrientation()
	{
		return m_orientation;
	}
	
	/**
	 * Sets the orientation of the robot. 1.0 for front facing, -1.0 for
	 * backwards facing.
	 * 
	 * @param orientation
	 */
	public static void setOrientation(double orientation)
	{
		m_orientation = orientation;
	}
	
	/**
	 * Toggles the robot orientation, whether front or back.
	 */
	public static void toggleOrientation()
	{
		m_orientation = -m_orientation;
	}
	
	/**
	 * Disables the crab drive.
	 */
	public static void disableCrabDrive()
	{
		// disables PIDs
		Drive.CRAB_DRIVE_COORDINATOR.speedReceiver.set(0);
		Drive.CRAB_DRIVE_COORDINATOR.disable();
		// sets drive motors to 0.0 to stop
		Drive.setAllMotors(0.0);
	}
	
	/**
	 * Enables the crab drive.
	 */
	public static void enableCrabDrive()
	{
		Drive.CRAB_DRIVE_COORDINATOR.enable();
	}
}