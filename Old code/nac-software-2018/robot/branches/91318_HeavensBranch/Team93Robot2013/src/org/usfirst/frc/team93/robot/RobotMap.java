/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team93.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Victor;

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
	public static Spark intakeWheel;
	public static Victor firstOutput;
	public static Victor secondOutput;
	public static Spark leftFront;
	public static Spark rightFront;
	public static Spark leftBack;
	public static Spark rightBack;
	
	public static DigitalInput opticalFrisbeeLoaded;
	public static DigitalInput limitFrisbeeLoading;
	
	public static DoubleSolenoid shootActuator;
	
	public RobotMap()
	{
		intakeWheel = new Spark(6);
		firstOutput = new Victor(2);
		secondOutput = new Victor(3);
		
		opticalFrisbeeLoaded = new DigitalInput(0);
		limitFrisbeeLoading = new DigitalInput(1);
		
		shootActuator = new DoubleSolenoid(0,1);
		
		leftFront = new Spark(0);
		rightFront = new Spark(5);
		
		leftBack = new Spark(1);
		rightBack = new Spark(4);
	}
}
