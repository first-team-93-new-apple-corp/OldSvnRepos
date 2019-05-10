package org.usfirst.frc.team93.robot;

import edu.wpi.first.wpilibj.Talon;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	public static Talon frontRightMotor;
	public static Talon topRightMotor;
	public static Talon backRightMotor;
	public static Talon frontLeftMotor;
	public static Talon topLeftMotor;
	public static Talon backLeftMotor;
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
	RobotMap(){
		frontRightMotor = new Talon(6);
		topRightMotor = new Talon(7);
		backRightMotor = new Talon(5);
		frontLeftMotor = new Talon(3);
		topLeftMotor = new Talon(2);
		backLeftMotor = new Talon(4);
	}
}
