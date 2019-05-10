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
	
	public static final int BLEncoder1 = 0;
	public static final int BLEncoder2 = 1;
	public static final int FLEncoder1 = 3;
	public static final int FLEncoder2 = 2;
	public static final int BREncoder1 = 4;
	public static final int BREncoder2 = 5;
	public static final int FREncoder1 = 6;
	public static final int FREncoder2 = 7;

	public static final int ElevatorEncoderA = 21;
	public static final int ElevatorEncoderB = 22;

	public static final int ElevatorSpeedControllerLeft = 19;
	public static final int ElevatorSpeedControllerRight = 20;

public static final int LeftIntake = 21;
	public static final int RightIntake = 22;
	public static final int Rotator = 23;
	public static final int HatchRemover = 24;
	public static final int HatchManipA = 25;
	public static final int HatchManipB = 30;
	public static final int HatchDetectorLeft = 31;
	public static final int HatchDetectorRight = 32;
	public static final int LeftIntake = 21;
	public static final int RightIntake = 22;
	public static final int Rotator = 23;
	public static final int HatchRemover = 24;
	public static final int HatchManip = 25;
	public static final int LeftIntake = 12;
	public static final int RightIntake = 11;
	public static final int Rotator = 10;
	public static final int HatchRemover = 9;
	public static final int HatchManip = 8;

	public static final int RotateManip = 26;
	public static final int HatchRemoverRotator = 27;
	public static final int RotateManip = 5;
	public static final int HatchRemoverPot = 6;
	//public static final int HatchRemoverEncoderB = 28;
	public static final int LightSensor = 0;
	public static final int elevatorBottomLimit = 15;
	public static final int wristUpLimit = 16;

	public static final double hatchLevelOneHeight = 19;
	public static final double hatchLevelTwoHeight = 47;
	public static final double hatchLevelThreeHeight = 75;
	public static final double cargoLevelOneHeight = 27.5;
	public static final double cargoLevelTwoHeight = 55.5;
	public static final double cargoLevelThreeHeight = 83.5;
	public static final double cargoLoadingStationHeight = 43.5;
	public static final double hatchLoadingStationHeight = 28.5;
	public static final double cargoCargoShipHeight = 39;
	public static double m_elevatorHeight;
	public static double m_hatchAngle;

	public static final double TORQUE_RESISTANCE_THRESHOLD = 0.08;
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
}
