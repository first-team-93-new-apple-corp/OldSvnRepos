/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team93.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap
{
	// CAN motor ports
	public static int DriveMotorFL;
	public static int DriveMotorBL;
	public static int DriveMotorFR;
	public static int DriveMotorBR;
	public static int leftIntake;
	public static int rightIntake;
	public static int ScalerMotor;
	public static int ClawAngleMotor;
	public static int LifterLeftMotor;
	public static int LifterRightMotor;
	
	public static int LeftRampMotor;
	public static int RightRampMotor;
	
	// DIO ports
	public static int ScalerBottomLimit;
	public static int ScalerTopLimit;
	public static int LeftSwitch;
	public static int RightSwitch;
	public static int ScalerEncoderA;
	public static int ScalerEncoderB;
	public static int ClawAngleTopLimit;
	public static int ClawAngleBottomLimit;
	public static int LeftDriveEncoderA;
	public static int LeftDriveEncoderB;
	public static int RightDriveEncoderA;
	public static int RightDriveEncoderB;
	
	// AIO ports
	public static int ClawAnglePot;
	
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;
	
	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	/**
	 * Used for assigning values that the sensors in subsystems refrence for the
	 * physical ports on the RoboRio
	 */
	public RobotMap()
	{
		// Motor CAN ports
		DriveMotorFL = 2;
		DriveMotorBL = 1;
		DriveMotorFR = 4;
		DriveMotorBR = 5;
		leftIntake = 0;
		rightIntake = 3;
		ScalerMotor = 6;
		ClawAngleMotor = 7;
		LeftRampMotor = 8;
		RightRampMotor = 9;
		
		// DIO ports
		LeftSwitch = 1;
		RightSwitch = 12;
		ScalerBottomLimit = 2;
		ScalerTopLimit = 3;
		ClawAngleTopLimit = 11;
		ClawAngleBottomLimit = 10;
		ScalerEncoderA = 4;
		ScalerEncoderB = 5;
		LeftDriveEncoderA = 6;
		LeftDriveEncoderB = 7;
		RightDriveEncoderA = 8;
		RightDriveEncoderB = 9;
		
		// AIO ports
		ClawAnglePot = 0;
		
	}
}