package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.commands.CrabDriveContinuous;
import org.usfirst.frc.team93.robot.utilities.AnalogGyroPIDSource;
import org.usfirst.frc.team93.robot.utilities.DirectionalDrivePIDOutput;
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
	public static Encoder LEFT_DRIVE_ENCODER;
	public static Encoder RIGHT_DRIVE_ENCODER;
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

	// These are used for steering in autonomous
	public static DirectionalDrivePIDOutput DRIVE_FORWARD_OUTPUT;
	public static DirectionalDrivePIDOutput DRIVE_RIGHT_OUTPUT;
	public static DirectionalDrivePIDOutput DRIVE_LEFT_OUTPUT;
	public static Team93PIDController DRIVE_STRAIGHT_SPEED_CONTROL;
	public static Team93PIDController DRIVE_RIGHT_SPEED_CONTROL;
	public static Team93PIDController DRIVE_LEFT_SPEED_CONTROL;

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

	public static Timer timer = new Timer();

	public Drive()
	{
		int leftDriveEncoderPinA;
		int leftDriveEncoderPinB;
		int rightDriveEncoderPinA;
		int rightDriveEncoderPinB;

		// RobotMap will automatically handle pin configurations between
		// simulator and a real robot
		leftDriveEncoderPinA = RobotMap.getMap().LEFT_DRIVE_ENCODER_PIN.A();
		leftDriveEncoderPinB = RobotMap.getMap().LEFT_DRIVE_ENCODER_PIN.B();
		rightDriveEncoderPinA = RobotMap.getMap().RIGHT_DRIVE_ENCODER_PIN.A();
		rightDriveEncoderPinB = RobotMap.getMap().RIGHT_DRIVE_ENCODER_PIN.B();

		/// Sensors
		// Encoders
		LEFT_DRIVE_ENCODER = new Encoder(leftDriveEncoderPinA, leftDriveEncoderPinB);
		RIGHT_DRIVE_ENCODER = new Encoder(rightDriveEncoderPinA, rightDriveEncoderPinB);

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
		DRIVE_ENCODERS = new MedianPIDSource(LEFT_DRIVE_ENCODER, RIGHT_DRIVE_ENCODER);

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
			Encoder encoderLF = new Encoder(3, 4);
			Encoder encoderLB = new Encoder(5, 6);
			Encoder encoderRF = new Encoder(1, 2);
			Encoder encoderRB = new Encoder(7, 8);

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

		LFSPI_dummy = new MovingDummyPIDSource();
		LBSPI_dummy = new MovingDummyPIDSource();
		RFSPI_dummy = new MovingDummyPIDSource();
		RBSPI_dummy = new MovingDummyPIDSource();

		// Drive Motors
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

		// Drive Steering Controller so that we can verify we are driving
		// straight in autonomous
		DRIVE_FORWARD_OUTPUT = new DirectionalDrivePIDOutput(0);
		DRIVE_RIGHT_OUTPUT = new DirectionalDrivePIDOutput(90);
		DRIVE_LEFT_OUTPUT = new DirectionalDrivePIDOutput(-90);
		// could probably be a regular pid controller, but hey, why not use this
		DRIVE_STRAIGHT_SPEED_CONTROL = new Team93PIDController(0.1, 0.0, 0.0, 0.0, DRIVE_ENCODERS, DRIVE_FORWARD_OUTPUT);
		DRIVE_RIGHT_SPEED_CONTROL = new Team93PIDController(0.1, 0.0, 0.0, 0.0, DRIVE_ENCODERS, DRIVE_RIGHT_OUTPUT);
		DRIVE_LEFT_SPEED_CONTROL = new Team93PIDController(0.1, 0.0, 0.0, 0.0, DRIVE_ENCODERS, DRIVE_LEFT_OUTPUT);

		// Crab Drive Outputs
		// CrabDriveDirectionMotor
		// we believe we can go as high as 0.06 or 0.07 on the field, but we're
		// using 0.05 for testing.
		double directionP = 0.04;
		double directionI = 0.001;
		double directionD = 0.0;
		double ILimit = 0.1;
		CRAB_DRIVE_DIRECTION_LEFT_FRONT = new CrabDriveDirectionMotor(directionP, directionI, directionD, ILimit, LFSPI_dummy, DRIVE_DIRECTION_LEFT_FRONT);
		CRAB_DRIVE_DIRECTION_LEFT_BACK = new CrabDriveDirectionMotor(directionP, directionI, directionD, ILimit, LBSPI_dummy, DRIVE_DIRECTION_LEFT_BACK);
		CRAB_DRIVE_DIRECTION_RIGHT_FRONT = new CrabDriveDirectionMotor(directionP, directionI, directionD, ILimit, RFSPI_dummy, DRIVE_DIRECTION_RIGHT_FRONT);
		CRAB_DRIVE_DIRECTION_RIGHT_BACK = new CrabDriveDirectionMotor(directionP, directionI, directionD, ILimit, RBSPI_dummy, DRIVE_DIRECTION_RIGHT_BACK);
		// CrabDriveCoordinator
		CRAB_DRIVE_COORDINATOR = new CrabDriveCoordinator(-0.03, 0, 0, GYRO, DRIVE_LEFT_FRONT, DRIVE_LEFT_BACK, DRIVE_RIGHT_FRONT, DRIVE_RIGHT_BACK, CRAB_DRIVE_DIRECTION_LEFT_FRONT, CRAB_DRIVE_DIRECTION_LEFT_BACK, CRAB_DRIVE_DIRECTION_RIGHT_FRONT, CRAB_DRIVE_DIRECTION_RIGHT_BACK);
		CRAB_DRIVE_COORDINATOR.disable();

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
		LEFT_DRIVE_ENCODER.reset();
		RIGHT_DRIVE_ENCODER.reset();
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
	 * Returns the orientatino of the robot. 1.0 for front facing, -1.0 for
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
}
