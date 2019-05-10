package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.utilities.TwoEncoderPIDSource;
import org.usfirst.frc.team93.robot.utilities.PIDOutput2Group;
import org.usfirst.frc.team93.robot.utilities.PIDOutput3Group;
import org.usfirst.frc.team93.robot.utilities.SteeringAndSpeedCoordinator;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
	
	public static Talon DRIVE_LEFT_MOTOR_FRONT;
	public static Talon DRIVE_LEFT_MOTOR_TOP;
	public static Talon DRIVE_LEFT_MOTOR_BACK;
	
	public static Talon DRIVE_RIGHT_MOTOR_FRONT;
	public static Talon DRIVE_RIGHT_MOTOR_TOP;
	public static Talon DRIVE_RIGHT_MOTOR_BACK;
	
	public static PIDController driveSpeedControl;
	
	public static Encoder LEFT_DRIVE_ENCODER;
	public static Encoder RIGHT_DRIVE_ENCODER;
	
	public static PIDOutput3Group leftDriveGroup;
	public static PIDOutput3Group rightDriveGroup;
	
	public static PIDOutput2Group driveAllMotorsGroup;
	
	public static SteeringAndSpeedCoordinator steerAndSpeed;
	
	public static PIDController driveSteeringControl;
	
	public static TwoEncoderPIDSource driveSteeringFeedback;
	
	public RobotMap()
	{
		DRIVE_LEFT_MOTOR_FRONT = new Talon(3);
		DRIVE_LEFT_MOTOR_TOP = new Talon(2);
		DRIVE_LEFT_MOTOR_BACK = new Talon(4);
		
		DRIVE_RIGHT_MOTOR_FRONT = new Talon(6);
		DRIVE_RIGHT_MOTOR_TOP = new Talon(7);
		DRIVE_RIGHT_MOTOR_BACK = new Talon(5);
		
		LEFT_DRIVE_ENCODER = new Encoder(22, 23, true, EncodingType.k1X);
		RIGHT_DRIVE_ENCODER = new Encoder(24, 25, false, EncodingType.k1X);
		
		leftDriveGroup = new PIDOutput3Group(DRIVE_LEFT_MOTOR_FRONT, DRIVE_LEFT_MOTOR_TOP, DRIVE_LEFT_MOTOR_BACK, 1, 1, 1);
		rightDriveGroup = new PIDOutput3Group(DRIVE_RIGHT_MOTOR_FRONT, DRIVE_RIGHT_MOTOR_TOP, DRIVE_RIGHT_MOTOR_BACK, 1, 1, 1);

		driveAllMotorsGroup = new PIDOutput2Group(leftDriveGroup, rightDriveGroup);

		driveSteeringFeedback = new TwoEncoderPIDSource(RIGHT_DRIVE_ENCODER, LEFT_DRIVE_ENCODER, 1.0, -1.0);
		
		steerAndSpeed = new SteeringAndSpeedCoordinator(leftDriveGroup, rightDriveGroup);
		steerAndSpeed.setSteeringGain(0.5);
		
		driveSteeringControl = new PIDController(.03, 0.0, 0.0, 0.0, driveSteeringFeedback, steerAndSpeed.SteeringReceiver);
	}
}