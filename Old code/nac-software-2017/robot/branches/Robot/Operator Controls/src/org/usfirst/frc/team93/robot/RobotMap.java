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
	public static int ClimberMotor;
	public static int GearLocMotor;
	public static int GearHighSwitch;
	public static int GearLowSwitch;
	public static int ClawEncoderInOne;
	public static int ClawEncoderInTwo;
	public RobotMap()
	{
		ClimberMotor = 0;
		GearLocMotor = 1;
		GearHighSwitch = 0;
		GearLowSwitch = 1;
		ClawEncoderInOne = 2;
		ClawEncoderInTwo = 3;
	}
}
