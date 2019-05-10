package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.Utilities.CANTalonVelocitySource;
import org.usfirst.frc.team93.robot.Utilities.LinAcqLimitEnforcer;
import org.usfirst.frc.team93.robot.Utilities.PIDOutput2Group;
import org.usfirst.frc.team93.robot.Utilities.PIDOutput3Group;
import org.usfirst.frc.team93.robot.Utilities.SteeringAndSpeedCoordinator;
import org.usfirst.frc.team93.robot.Utilities.Team93CANTalon;
import org.usfirst.frc.team93.robot.Utilities.Team93PIDController;
import org.usfirst.frc.team93.robot.Utilities.Team93Potentiometer;
import org.usfirst.frc.team93.robot.Utilities.TwoEncoderPIDSource;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogTrigger;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

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

	public static SendableChooser autonomousModeControl;
	public static SendableChooser autonomousBallControl;
	public static SendableChooser autonomousDefenseControl;

	public static DigitalInput DetectBall;
	public static DigitalInput UpperFiringAngle;
	public static DigitalInput LowerFiringAngle;

	public static AnalogInput DetectBall2;

	// Solenoids
	public static DoubleSolenoid GearShift;
	public static DoubleSolenoid releaseScaler;
	public static DoubleSolenoid releaseArm;

	// Encoders

	public static AnalogTrigger LEFT_DRIVE_TRIGGER;
	public static Encoder LEFT_DRIVE_ENCODER;
	public static Encoder RIGHT_DRIVE_ENCODER;
	public static Encoder SCALER_ENCODER;

	// Motors
	/**
	 * @Review NLuther TODO: Instantiate as SpeedController, not as Talon. Only
	 *         initialize as Talon.
	 * @Review NLuther TODO: Add Current Limiter for speed controller instances
	 *         for shooter. This is to safely handle ball jams.
	 */
	public static CANTalon LEFT_SHOOTER;
	public static CANTalonVelocitySource EncoderVelocityShooterLeft;
	public static Team93CANTalon RIGHT_SHOOTER;
	public static CANTalonVelocitySource EncoderVelocityShooterRight;
	public static CANTalon DefenseArm;
	public static CANTalonVelocitySource EncoderVelocityManipulator;

	public static Victor frontRight;
	public static Victor frontLeft;
	public static Victor topRight;
	public static Victor topLeft;
	public static Victor backRight;
	public static Victor backLeft;

	public static Victor LEFT_BACQUASITION;
	public static Victor RIGHT_BACQUASITION;
	public static Victor SHOOTING_ANGLE_TALON;
	public static Victor BallGuideMotor;
	public static Victor ScalerMotor;

	public static LinAcqLimitEnforcer linAcq;
	public static LinAcqLimitEnforcer manipulator;

	// Potentiometer
	public static Team93Potentiometer linAcqPotentiometer;
	public static Team93Potentiometer manipulatorPotentiometer;

	// PID Output Groups
	public static PIDOutput3Group leftDriveGroup;
	public static PIDOutput3Group rightDriveGroup;

	public static PIDOutput2Group driveAllMotorsGroup;
	public static PIDOutput2Group shooterMotorGroup;

	// PID Controllers
	public static Team93PIDController driveSteeringControl;
	public static Team93PIDController driveSpeedControl;
	public static Team93PIDController rightDriveSpeedControl;
	public static Team93PIDController leftSpeedControl;
	public static Team93PIDController rightSpeedControl;
	public static Team93PIDController firingAngleControl;
	public static Team93PIDController scalerControl;
	public static Team93PIDController ManipulatorPositionControl;
	public static Team93PIDController leftFiringSpeedControl;
	public static Team93PIDController rightFiringSpeedControl;

	// Two Encoder
	public static TwoEncoderPIDSource driveEncoders;
	public static TwoEncoderPIDSource driveSteeringFeedback;

	// Steer and Speed
	public static SteeringAndSpeedCoordinator steerAndSpeed;

	public static double speedA = .5;
	public static double speedB = .5;

	public RobotMap()
	{
		NetworkTable.initialize();
		autonomousModeControl = new SendableChooser();
		autonomousBallControl = new SendableChooser();
		autonomousDefenseControl = new SendableChooser();

		DetectBall = new DigitalInput(9);

		// DetectBall2 = new AnalogInput(1);

		// linAcqPotentiometer = new Team93Potentiometer(0, -41.229, 71.6);
		linAcqPotentiometer = new Team93Potentiometer(0, -38.612, 132.845);
		manipulatorPotentiometer = new Team93Potentiometer(1, 1.0, 0.0);

		// RIGHT_DRIVE_TRIGGER = new AnalogTrigger(3);
		// LEFT_DRIVE_TRIGGER = new AnalogTrigger(0);
		// LEFT_DRIVE_TRIGGER.setFiltered(true);
		// LEFT_DRIVE_TRIGGER.setLimitsVoltage(1.0, 3.0);
		// LEFT_DRIVE_ENCODER = new Counter();
		// LEFT_DRIVE_ENCODER.setUpDownCounterMode();
		// LEFT_DRIVE_ENCODER.setUpSource(LEFT_DRIVE_TRIGGER,
		// AnalogTriggerType.kRisingPulse);
		// LEFT_DRIVE_ENCODER.setDownSource(LEFT_DRIVE_TRIGGER,
		// AnalogTriggerType.kFallingPulse);
		LEFT_DRIVE_ENCODER = new Encoder(22, 23, true, EncodingType.k1X);
		RIGHT_DRIVE_ENCODER = new Encoder(24, 25, false, EncodingType.k1X);
		driveEncoders = new TwoEncoderPIDSource(RIGHT_DRIVE_ENCODER, LEFT_DRIVE_ENCODER, .5, .5);
		driveSteeringFeedback = new TwoEncoderPIDSource(RIGHT_DRIVE_ENCODER, LEFT_DRIVE_ENCODER, 1.0, -1.0);
		// LEFT_DRIVE_ENCODER = new Encoder(24, 25, true, EncodingType.k1X);
		// RIGHT_DRIVE_ENCODER = new Encoder(22, 23, false, EncodingType.k1X);

		// RIGHT_DRIVE_ENCODER = new Encoder(0,1, true, EncodingType.k1X);

		// // SCALER_ENCODER = new Encoder(17, 18, true, EncodingType.k1X);

		// DULUTH VALUES
		DefenseArm = new CANTalon(2);
		LEFT_SHOOTER = new CANTalon(1);
		RIGHT_SHOOTER = new Team93CANTalon(3, -1.0);

		LEFT_SHOOTER.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		RIGHT_SHOOTER.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		DefenseArm.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);

		EncoderVelocityShooterLeft = new CANTalonVelocitySource(LEFT_SHOOTER);
		EncoderVelocityShooterRight = new CANTalonVelocitySource(RIGHT_SHOOTER, -1.0);
		EncoderVelocityManipulator = new CANTalonVelocitySource(DefenseArm);

		// DefenseArm.changeControlMode(TalonControlMode.Position);
		// drive
		topLeft = new Victor(2);
		frontLeft = new Victor(3);
		backLeft = new Victor(4);

		topRight = new Victor(7);
		frontRight = new Victor(6);
		backRight = new Victor(5);

		// other
		LEFT_BACQUASITION = new Victor(0);
		RIGHT_BACQUASITION = new Victor(8);
		SHOOTING_ANGLE_TALON = new Victor(9);
		BallGuideMotor = new Victor(1);
		// ScalerMotor = new Victor(12);

		GearShift = new DoubleSolenoid(0, 1);

		// releaseArm = new DoubleSolenoid(2, 3);
		// releaseScaler = new DoubleSolenoid(4, 5);
		// change PID later
		leftDriveGroup = new PIDOutput3Group(frontLeft, topLeft, backLeft, -speedB, speedB, -speedB);
		rightDriveGroup = new PIDOutput3Group(frontRight, topRight, backRight, speedA, -speedA, speedA);

		driveAllMotorsGroup = new PIDOutput2Group(leftDriveGroup, rightDriveGroup);
		shooterMotorGroup = new PIDOutput2Group(leftFiringSpeedControl, rightFiringSpeedControl);

		steerAndSpeed = new SteeringAndSpeedCoordinator(leftDriveGroup, rightDriveGroup);
		steerAndSpeed.setSteeringGain(0.5);

		linAcq = new LinAcqLimitEnforcer(SHOOTING_ANGLE_TALON, linAcqPotentiometer, -54.5, -53.0);
		linAcq.setSecondaryLimits(20, 67, 0.5);

		firingAngleControl = new Team93PIDController(-0.17, 0.7, 0.0, 0.0, linAcqPotentiometer, linAcq);
		driveSpeedControl = new Team93PIDController(.06, 0.03, 0.2, 1.0, driveEncoders, steerAndSpeed.SpeedReceiver);
		driveSteeringControl = new Team93PIDController(.03, 0.0, 0.0, 0.0, driveSteeringFeedback,
				steerAndSpeed.SteeringReceiver);
		// rightDriveSpeedControl = new Team93PIDController(.12, 0.03, 0.2, 0.0,
		// RIGHT_DRIVE_ENCODER,
		// steerAndSpeed.SpeedReceiver);
		// rightEncDriveSpeedControl = new Team93PIDController(.02, 0.01, 0.0,
		// 0.0, RIGHT_DRIVE_ENCODER,
		// steerAndSpeed.SpeedReceiver);
		leftSpeedControl = new Team93PIDController(.006, 0.01, 0.0, 0.0, LEFT_DRIVE_ENCODER, leftDriveGroup);
		rightSpeedControl = new Team93PIDController(.006, 0.01, 0.0, 0.0, RIGHT_DRIVE_ENCODER, rightDriveGroup);
		// scalerControl = new Team93PIDController(-0.000021, -0.0000025,
		// -0.0000005, -0.000029, 0.07, SCALER_ENCODER,
		// ScalerMotor);
		leftFiringSpeedControl = new Team93PIDController(0.000021, 0.0000025, 0.0000005, 0.000029, 0.07,
				EncoderVelocityShooterLeft, LEFT_SHOOTER);
		// leftFiringSpeedControl.setReversedSensor(true);
		rightFiringSpeedControl = new Team93PIDController(0.000021, 0.0000025, 0.0000005, 0.000029, 0.07,
				EncoderVelocityShooterRight, RIGHT_SHOOTER);
		// rightFiringSpeedControl.setReversedSensor(true);
		manipulator = new LinAcqLimitEnforcer(DefenseArm, manipulatorPotentiometer, 1.96, 2.17, true);

		ManipulatorPositionControl = new Team93PIDController(3, 0.0, 0.0, 0.0, manipulatorPotentiometer, manipulator);
	}
}
