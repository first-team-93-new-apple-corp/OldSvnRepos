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
	public static final int FrontRightAngleEncoder = 0;
	public static final int FrontLeftAngleEncoder = 1;
	public static final int BackLeftAngleEncoder = 3;
	public static final int BackRightAngleEncoder = 2;

	public static final int BLDrive = 1;
	public static final int BLRotation = 3;
	public static final int BRRotation = 7;
	public static final int BRDrive = 0;
	public static final int FLDrive = 4;
	public static final int FLRotation = 2;
	public static final int FRDrive = 6;
	public static final int FRRotation = 5;
	
	public static final int ElevatorEncoderA = 2; //changed form 27
	public static final int ElevatorEncoderB = 3; //changed from 28

	public static final int ElevatorSpeedControllerLeft = 19;
	public static final int ElevatorSpeedControllerRight = 20;

	public static final int intake = 21;
	public static final int Wrist = 23;
	public static final int kicker = 24; 
	public static final int HatchManipulatorServoRight = 1; //right : 1
	public static final int HatchManipulatorServoLeft = 0; //left : 0

	public static final int WristSensor = 4; 
	public static final int kickerSensor = 5; 
	public static final int HatchRemoverPot = 6;
	//public static final int HatchRemverEncoderB = 28;
	public static final int IntakeLightSensor = 8; //changed
	public static final int elevatorBottomLimit = 4; //changed from 26
	public static final int wristUpLimit = 7; //changed

	public static final double hatchLevelOneHeight = 0.0; //in encoder ticks
	public static final double hatchLevelTwoHeight = 306.0;//in endcoder ticks
	public static final double hatchLevelThreeHeight = 274;
	public static final double cargoLevelOneHeight = 27.5;
	public static final double cargoLevelTwoHeight = 55.5;
	public static final double cargoLevelThreeHeight = 83.5;
	public static final double cargoLoadingStationHeight = 43.5;
	public static final double hatchLoadingStationHeight = 28.5;
	public static final double cargoCargoShipHeight = 39;

	public static final int habDrive = 25;
	public static final int habLift = 26;

	public static double m_elevatorHeight;
	public static double m_hatchVoltage;
	public static boolean stop = true;

	public static final int HabLiftEncoderA = 0;
	public static final int HabLiftEncoderB = 1;

	public static final double TORQUE_RESISTANCE_THRESHOLD = 0.08;
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;
	//I broke heaven's code
	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;

	//servo for bottom

	//climber encoder 0 1 
}
