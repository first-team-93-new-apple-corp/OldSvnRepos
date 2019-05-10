package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.Utilities.PIDOutput2Group;
import org.usfirst.frc.team93.robot.Utilities.PIDOutput3Group;
import org.usfirst.frc.team93.robot.Utilities.SteeringAndSpeedCoordinator;
import org.usfirst.frc.team93.robot.Utilities.Team93Potentiometer;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Servo;
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
	{ new DigitalInput(14), new DigitalInput(15), new DigitalInput(16), new DigitalInput(17) };
	public static final DigitalInput detectBallZero = new DigitalInput(18);
	public static final DigitalInput CatapultReady = new DigitalInput(39);
	public static final DigitalInput UpperFiringAngle = new DigitalInput(40);
	public static final DigitalInput LowerFiringAngle = new DigitalInput(41);
	public static final AnalogInput LIGHT_SENSOR = new AnalogInput(21);

	// Solenoids
	public static DoubleSolenoid leftShooterSolenoid;
	public static DoubleSolenoid rightShooterSolenoid;

	// Encoders
	public static final Encoder LEFT_DRIVE_ENCODER = new Encoder(7, 8, true, EncodingType.k1X);
	public static final Encoder RIGHT_DRIVE_ENCODER = new Encoder(9, 10, true, EncodingType.k1X);
	public static final Encoder RAISE_CANNONPULT_ENCODER = new Encoder(22, 23, true, EncodingType.k1X);
	public static final Encoder SHOOTER_ENCODER_ZERO = new Encoder(25, 26, true, EncodingType.k1X);
	// Motors
	public static final Talon LEFT_DRIVE_LEFT = new Talon(1);
	public static final Talon LEFT_DRIVE_RIGHT = new Talon(2);
	public static final Talon LEFT_DRIVE_TOP = new Talon(18);
	public static final Talon RIGHT_DRIVE_LEFT = new Talon(3);
	public static final Talon RIGHT_DRIVE_RIGHT = new Talon(4);
	public static final Talon RIGHT_DRIVE_TOP = new Talon(19);
	public static final Talon LEFT_BACQUASITION = new Talon(5);
	public static final Talon RIGHT_BACQUASITION = new Talon(6);
	public static final Talon RETRACT_SHOOTER = new Talon(21);
	public static final Talon TURN_TIMING_BELT = new Talon(42);
	public static final Talon TURN_LEFT_SHOOTER = new Talon(43);
	public static final Talon TURN_RIGHT_SHOOTER = new Talon(44);
	// Potentiometer
	public static final Team93Potentiometer MANIPULATOR_POSITION = new Team93Potentiometer(11, 12, 13);
	public static final Team93Potentiometer ARM_POSITION = new Team93Potentiometer(23, 24, 25);

	// PID Output Groups
	public static PIDOutput3Group leftDriveGroup;
	public static PIDOutput3Group rightDriveGroup;
	public static PIDOutput2Group driveAllMotorsGroup;
	// PID Controllers
	public static PIDController driveCoordinator;
	public static PIDController driveSpeedControl;
	public static PIDController driveEncoderSteering;
	public static PIDController cannonpultEncoder;

	// Steer and Speed
	public static SteeringAndSpeedCoordinator steerAndSpeed;

	// Solenoid
	public final static DoubleSolenoid DOZER_LEFT = new DoubleSolenoid(13, 14);
	public final static DoubleSolenoid DOZER_RIGHT = new DoubleSolenoid(15, 16);

	// Servos
	public final static Servo MECHANICAL_STOP = new Servo(30);

}
