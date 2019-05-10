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
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	
	//Rotation is for horizontal, Spin is for vertical wheel movement
	
	public static int driveFLRot = 0;
	public static int driveFRRot = 1;
	public static int driveBLRot = 2;
	public static int driveBRRot = 3;
	
	public static int flRotEncOne = 0;
	public static int flRotEncTwo = 1;
	public static int frRotEncOne = 2;
	public static int frRotEncTwo = 3;
	public static int blRotEncOne = 4;
	public static int blRotEncTwo = 5;
	public static int brRotEncOne = 6;
	public static int brRotEncTwo = 7;
}
