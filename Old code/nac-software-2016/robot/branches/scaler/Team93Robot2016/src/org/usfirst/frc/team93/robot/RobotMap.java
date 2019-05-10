package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.utilities.PIDOutput2Group;
import org.usfirst.frc.team93.robot.utilities.PIDOutput3Group;
import org.usfirst.frc.team93.robot.utilities.PoorMansEncoder;
import org.usfirst.frc.team93.robot.utilities.SteeringAndSpeedCoordinator;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
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
	public static DigitalInput[] AutoSwiches =
	{ new DigitalInput(14), new DigitalInput(15), new DigitalInput(16), new DigitalInput(17) };

	// Encoders
	public static Encoder LEFT_DRIVE_ENCODER = new Encoder(7, 8, true, EncodingType.k1X);
	public static Encoder RIGHT_DRIVE_ENCODER = new Encoder(9, 10, true, EncodingType.k1X);
	public static Encoder ARM_CONTROLLER = new Encoder(11, 12, true, EncodingType.k1X);

	// Motors
	public static Talon LEFT_DRIVE_LEFT;
	public static Talon LEFT_DRIVE_RIGHT;
	public static Talon LEFT_DRIVE_TOP;
	public static Talon RIGHT_DRIVE_LEFT;
	public static Talon RIGHT_DRIVE_RIGHT;
	public static Talon RIGHT_DRIVE_TOP;
	public static Talon LEFT_BACQUAITION_LEFT;
	public static Talon RIGHT_BACQUAITION_RIGHT;
	public static Talon lowerArm;
	public static Talon RETRACTER;

	// Potentiometer
	// public static final Team93Potentiometer Manipulator_POT = new
	// Team93Potentiometer(11,12,13);
	public static AnalogPotentiometer ArmPotentiometer;

	// PID Output Groups
	public static PIDOutput3Group leftDriveGroup;
	public static PIDOutput3Group rightDriveGroup;
	public static PIDOutput2Group driveAllMotorsGroup;
	// PID Controllers
	public static PIDController driveCoordinator;
	public static PIDController driveSpeedControl;
	public static PIDController driveEncoderSteering;
	public static PIDController scalerControl;

	// Steer and Speed
	public static SteeringAndSpeedCoordinator steerAndSpeed;

	// Limit Switches
	public static DigitalInput frontBarLimit;
	public static DigitalInput backBarLimit;
	// Solenoids
	public static DoubleSolenoid rightScaler;
	public static DoubleSolenoid leftScaler;
	public static DoubleSolenoid releaseRightArm;
	public static DoubleSolenoid releaseLeftArm;

	public static DigitalInput lightSensor;
	public static PoorMansEncoder poorMansEncoder;

	public static Encoder scalerEncoder;

	RobotMap()
	{
		frontBarLimit = new DigitalInput(1);
		backBarLimit = new DigitalInput(2);
		LEFT_DRIVE_LEFT = new Talon(5);
		LEFT_DRIVE_RIGHT = new Talon(6);
		LEFT_DRIVE_TOP = new Talon(7);
		RIGHT_DRIVE_LEFT = new Talon(3);
		RIGHT_DRIVE_RIGHT = new Talon(4);
		RIGHT_DRIVE_TOP = new Talon(1);
		// LEFT_BACQUAITION_LEFT = new Talon(5);
		// RIGHT_BACQUAITION_RIGHT = new Talon(6);
		lowerArm = new Talon(8);
		ArmPotentiometer = new AnalogPotentiometer(3);
		rightScaler = new DoubleSolenoid(2, 3);
		leftScaler = new DoubleSolenoid(0, 1);
		RETRACTER = new Talon(0);
		lightSensor = new DigitalInput(5);
		poorMansEncoder = new PoorMansEncoder(lightSensor, RETRACTER);
		scalerControl = new PIDController(-0.1, 0, 0, poorMansEncoder, RETRACTER);
		releaseRightArm = new DoubleSolenoid(4, 5);
		releaseLeftArm = new DoubleSolenoid(6, 7);
	}
}