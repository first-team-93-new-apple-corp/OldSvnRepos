/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team93.robot;

import edu.wpi.first.wpilibj.SpeedController;

public class RobotMap {
	public static int Intake;
	public static int SubShooter;
	public static int MainShooter;
	
	 public static int piston;
	 public static int piston1;

	
	public RobotMap(){
		Intake = 1;
		SubShooter = 3;
		MainShooter = 2;
		
		piston = 0;
		piston1 = 1;
		
	}
}
	
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;

