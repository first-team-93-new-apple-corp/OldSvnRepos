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
	public static int MoveGearAcq;
	public static int UpperSwitch;
	public static int MidSwitch;
	public static int LowerSwitch;
	public static int GearLocEncA;
	public static int GearLocEncB;
	
	public RobotMap()
	{
		MoveGearAcq = 0;
		UpperSwitch = 0;
		MidSwitch = 1;
		LowerSwitch = 2;
		GearLocEncA = 0;
		GearLocEncB = 1;
	}
}
