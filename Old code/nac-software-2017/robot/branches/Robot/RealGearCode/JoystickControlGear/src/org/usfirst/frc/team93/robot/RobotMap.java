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
	public static int GearTalon;
	public static int TopGearSwitch;
	public static int BottomGearSwitch;
	public static int GearLocOne;
	public static int GearLocTwo;
	public static int GearSolenoidFrontA;
	public static int GearSolenoidFrontB;
	public static int GearSolenoidBackA;
	public static int GearSolenoidBackB;
	public static int DeflectorSolenoidA;
	public static int DeflectorSolenoidB;
	public static int GearFrontIntake;
	public static int GearBackIntake;

	public RobotMap() {
		GearTalon = 1;
		TopGearSwitch = 1;
		BottomGearSwitch = 2;
		GearLocOne = 3;
		GearLocTwo = 4;
		GearSolenoidFrontA = 5;
		GearSolenoidFrontB = 6;
		GearSolenoidBackA = 7;
		GearSolenoidBackB = 8;
		DeflectorSolenoidA = 9;
		DeflectorSolenoidB = 10;
		GearFrontIntake = 11;
		GearBackIntake = 12;
	}
}
