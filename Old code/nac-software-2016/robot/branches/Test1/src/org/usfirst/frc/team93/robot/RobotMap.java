package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.utilities.CANTalonVelocitySource;
//import org.usfirst.frc.team93.robot.utilities.CurrentLimitedSpeedController;
import org.usfirst.frc.team93.robot.utilities.LightSensor;
import org.usfirst.frc.team93.robot.utilities.Team93PIDController;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
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
	public static DigitalInput test;
	public static Talon PIDMotor;
	public static PIDController normalPID;
	public static Team93PIDController joshsPID;
	public static DigitalInput lightSensor;
	public static LightSensor poorMansEncoder;
	public static PowerDistributionPanel powerDistribution;
	public static SpeedController leftMotor;
	// public static CurrentLimitedSpeedController LimitedLeftMotor;
	public static Encoder PIDEncoder;
	public static CANTalon TestingMotor;
	public static CANTalonVelocitySource EncoderVelocityShooterLeft;

	public RobotMap()
	{
		leftMotor = new Talon(1);
		// LimitedLeftMotor = new CurrentLimitedSpeedController(leftMotor, 5.5,
		// 1);
		test = new DigitalInput(8);
		PIDMotor = new Talon(8);
		lightSensor = new DigitalInput(7);
		// poorMansEncoder = new LightSensor();
		powerDistribution = new PowerDistributionPanel();
		// PIDEncoder = new Encoder(21, 20, true, EncodingType.k1X);
		// normalPID = new PIDController(0.05, 0, 0, PIDEncoder, PIDMotor);
		TestingMotor = new CANTalon(3);

		TestingMotor.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		// TestingMotor.configEncoderCodesPerRev(256);
		// TestingMotor.changeControlMode(CANTalon.TalonControlMode.Speed);
		EncoderVelocityShooterLeft = new CANTalonVelocitySource(TestingMotor);
		// joshsPID = new Team93PIDController(-0.000025, -0.00000025, 0,
		// -0.000024, 0.07, EncoderVelocityShooterLeft,
		// TestingMotor);
		joshsPID = new Team93PIDController(-0.000021, -0.0000025, -0.0000005, -0.000029, 0.07,
				EncoderVelocityShooterLeft, TestingMotor);
	}
}
