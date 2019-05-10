/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	//ANALOG IN 0-3
	public static final int FrontRightAngleEncoder = 1;
	public static final int FrontLeftAngleEncoder = 2;
	public static final int BackLeftAngleEncoder = 3;
	public static final int BackRightAngleEncoder = 0;

	public static final int BLDrive = 1;
	public static final int BLRotation = 3;
	public static final int BRRotation = 7;
	public static final int BRDrive = 0;
	public static final int FLDrive = 4;
	public static final int FLRotation = 2;
	public static final int FRDrive = 6;
	public static final int FRRotation = 5;

	public static final int ElevatorEncoderLeftA = 17;
	public static final int ElevatorEncoderLeftB = 18;
	public static final int ElevatorEncoderRightA = 21;
	public static final int ElevatorEncoderRightB = 22;

	public static final int ElevatorSpeedControllerLeft = 19;
	public static final int ElevatorSpeedControllerRight = 20;

	public static final int ExtenderForward = 23;
	public static final int ExtenderReverse = 25;
	public static final int Grabber1Forward = 24;
	public static final int Grabber1Reverse = 26;
	public static final int Grabber2Forward = 27;
	public static final int Grabber2Reverse = 28;

	public static final int IntakeSCLeft = 29;
	public static final int IntakeSCRight = 30;
	public static final int HingeSC = 31;

	public static final int HingeEncoderA = 36;
	public static final int HingeEncoderB = 37;
	
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
}
