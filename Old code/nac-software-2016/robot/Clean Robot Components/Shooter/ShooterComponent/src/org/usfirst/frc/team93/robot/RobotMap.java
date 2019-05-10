package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.Utilities.CANTalonVelocitySource;
import org.usfirst.frc.team93.robot.Utilities.LinAcqLimitEnforcer;
import org.usfirst.frc.team93.robot.Utilities.PIDOutput2Group;
import org.usfirst.frc.team93.robot.Utilities.Team93CANTalon;
import org.usfirst.frc.team93.robot.Utilities.Team93PIDController;
import org.usfirst.frc.team93.robot.Utilities.Team93Potentiometer;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap
{	
	//Switches
	public static DigitalInput DetectBall;
	public static DigitalInput UpperFiringAngle;
	public static DigitalInput LowerFiringAngle;

	public static AnalogInput DetectBall2;

	// Solenoids
	public static DoubleSolenoid releaseArm;

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


	public static Victor LEFT_BACQUASITION;
	public static Victor RIGHT_BACQUASITION;
	public static Victor SHOOTING_ANGLE_TALON;
	public static Victor BallGuideMotor;

	public static LinAcqLimitEnforcer linAcq;
	
	// Potentiometer
	public static Team93Potentiometer linAcqPotentiometer;

	// PID Output Groups
	public static PIDOutput2Group shooterMotorGroup;

	// PID Controllers
	public static Team93PIDController firingAngleControl;
	public static Team93PIDController leftFiringSpeedControl;
	public static Team93PIDController rightFiringSpeedControl;



	public RobotMap()
	{
		NetworkTable.initialize();
		DetectBall = new DigitalInput(9);


		linAcqPotentiometer = new Team93Potentiometer(0, -38.612, 132.845);
		
		// DULUTH VALUES
		LEFT_SHOOTER = new CANTalon(1);
		RIGHT_SHOOTER = new Team93CANTalon(3, -1.0);

		LEFT_SHOOTER.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		RIGHT_SHOOTER.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		

		EncoderVelocityShooterLeft = new CANTalonVelocitySource(LEFT_SHOOTER);
		EncoderVelocityShooterRight = new CANTalonVelocitySource(RIGHT_SHOOTER, -1.0);

		// other
		LEFT_BACQUASITION = new Victor(0);
		RIGHT_BACQUASITION = new Victor(8);
		SHOOTING_ANGLE_TALON = new Victor(9);
		BallGuideMotor = new Victor(1);
		
		// change PID later
		shooterMotorGroup = new PIDOutput2Group(leftFiringSpeedControl, rightFiringSpeedControl);

		linAcq = new LinAcqLimitEnforcer(SHOOTING_ANGLE_TALON, linAcqPotentiometer, 0.6, 69.5);
		linAcq.setSecondaryLimits(20, 67, 0.5);

		firingAngleControl = new Team93PIDController(-0.17, 0.7, 0.0, 0.0, linAcqPotentiometer, linAcq);
		
		leftFiringSpeedControl = new Team93PIDController(0.000021, 0.0000025, 0.0000005, 0.000029, 0.07,
				EncoderVelocityShooterLeft, LEFT_SHOOTER);
		rightFiringSpeedControl = new Team93PIDController(0.000021, 0.0000025, 0.0000005, 0.000029, 0.07,
				EncoderVelocityShooterRight, RIGHT_SHOOTER);
	}
}
