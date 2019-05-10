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
	 public static int frontRight;
	 public static int backRight;
	 public static int frontLeft;
	 public static int backLeft;
	 
	 public static int limitSwitch;
	 
	 public static int piston;
	 
	 public static int forwardChannel;
	 public static int reverseChannel;
	 public RobotMap() {
		 frontRight = 4;
		 backRight = 10;
		 frontLeft = 5;
		 backLeft = 0;
		 
		 limitSwitch = 1;
		 
		 piston = 3;
		 
		 forwardChannel = 6;
		 reverseChannel = 9;
		 
	 }

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
}
