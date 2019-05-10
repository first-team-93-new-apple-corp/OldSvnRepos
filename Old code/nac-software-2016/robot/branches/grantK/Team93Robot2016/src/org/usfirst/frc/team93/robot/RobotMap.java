package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.Utilities.CurrentLimitedSpeedController;
import org.usfirst.frc.team93.robot.Utilities.PIDOutput2Group;
import org.usfirst.frc.team93.robot.Utilities.PIDOutput3Group;
import org.usfirst.frc.team93.robot.Utilities.SteeringAndSpeedCoordinator;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */

public class RobotMap
{
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;

	// Switches
	public static final DigitalInput[] AutoSwiches =
	{ new DigitalInput(13), new DigitalInput(14), new DigitalInput(15), new DigitalInput(16) };
	public static final DigitalInput[] DefensePositionSwiches =
	{ new DigitalInput(17), new DigitalInput(18) };
	public static DigitalInput DetectBall;
	public static DigitalInput CatapultReady;
	public static DigitalInput UpperFiringAngle;
	public static DigitalInput LowerFiringAngle;
	public static DigitalInput UpperScalarLimitSwitch;
	public static DigitalInput LowerScalarLimitSwitch;

	// Solenoids
	public static DoubleSolenoid leftShooterSolenoid;
	public static DoubleSolenoid rightShooterSolenoid;

	// Encoders
	public static Encoder FiringAngleEncoder;
	public static Encoder LEFT_DRIVE_ENCODER;
	public static Encoder RIGHT_DRIVE_ENCODER;

	// Motors
	public static SpeedController LEFT_DRIVE_LEFT;
	public static SpeedController LEFT_DRIVE_RIGHT;
	public static SpeedController LEFT_DRIVE_TOP;
	public static SpeedController RIGHT_DRIVE_LEFT;
	public static SpeedController RIGHT_DRIVE_RIGHT;
	public static SpeedController RIGHT_DRIVE_TOP;

	public static SpeedController BACQUASITION;
	public static SpeedController FiringAngleTalon;
	public static SpeedController ShootingMotor;

	public static SpeedController FireWheelsL;
	public static SpeedController FireWheelsR;
	public static SpeedController GuideMotor;

	public static SpeedController ScalarMotor;

	// PID Output Groups
	public static PIDOutput3Group leftDriveGroup;
	public static PIDOutput3Group rightDriveGroup;
	public static PIDOutput2Group driveAllMotorsGroup;
	public static PIDOutput2Group shooterMotorGroup;

	// PID Controllers
	public static PIDController driveCoordinator;
	public static PIDController driveSpeedControl;
	public static PIDController driveEncoderSteering;
	public static PIDController shooterSpeedControl;
	public static PIDController firingAngleControl;

	// Steer and Speed
	public static SteeringAndSpeedCoordinator steerAndSpeed;

	// Current Controlled Talons
	public static PowerDistributionPanel powerDistribution;
	public static SpeedController leftMotor;
	public static CurrentLimitedSpeedController LimitedLeftMotor;

	public RobotMap()
	{
		// change PID later
		leftDriveGroup = new PIDOutput3Group(LEFT_DRIVE_LEFT, LEFT_DRIVE_RIGHT, LEFT_DRIVE_TOP, -1.0, -1.0, -1.0);
		rightDriveGroup = new PIDOutput3Group(RIGHT_DRIVE_LEFT, RIGHT_DRIVE_RIGHT, RIGHT_DRIVE_TOP, -1.0, -1.0, -1.0);
		driveAllMotorsGroup = new PIDOutput2Group(leftDriveGroup, rightDriveGroup);

		firingAngleControl = new PIDController(0.0, 0.0, 0.0, FiringAngleEncoder, FiringAngleTalon);
		shooterMotorGroup = new PIDOutput2Group(FireWheelsL, FireWheelsR);
		steerAndSpeed = new SteeringAndSpeedCoordinator(leftDriveGroup, rightDriveGroup);
		steerAndSpeed.setSteeringGain(0.5);
		driveSpeedControl = new PIDController(-0.0015, -0.00001, 0.0, LEFT_DRIVE_ENCODER, steerAndSpeed.SpeedReceiver);

		// DetectBall = new DigitalInput(1);
		// CatapultReady = new DigitalInput(2);
		UpperFiringAngle = new DigitalInput(3);
		LowerFiringAngle = new DigitalInput(4);
		LowerScalarLimitSwitch = new DigitalInput(1);
		UpperScalarLimitSwitch = new DigitalInput(2);

		LEFT_DRIVE_ENCODER = new Encoder(9, 10, true, EncodingType.k1X);
		RIGHT_DRIVE_ENCODER = new Encoder(11, 12, true, EncodingType.k1X);
		FiringAngleEncoder = new Encoder(13, 14, true, EncodingType.k1X);

		// leftMotor = new Talon(12);
		// LimitedLeftMotor = new CurrentLimitedSpeedController(leftMotor, 5.5,
		// 1);
		LEFT_DRIVE_LEFT = new Talon(8);
		LEFT_DRIVE_RIGHT = new Talon(2);
		LEFT_DRIVE_TOP = new Talon(3);
		RIGHT_DRIVE_LEFT = new Talon(5);
		RIGHT_DRIVE_RIGHT = new Talon(9);
		RIGHT_DRIVE_TOP = new Talon(6);
		BACQUASITION = new Talon(7);
		FiringAngleTalon = new Talon(1);
		// FireWheelsL = new Talon(9);
		// FireWheelsR = new Talon(10);
		// GuideMotor = new Talon(11);
		ScalarMotor = new Talon(4);

		leftShooterSolenoid = new DoubleSolenoid(4, 5);
		rightShooterSolenoid = new DoubleSolenoid(6, 7);
	}
}
