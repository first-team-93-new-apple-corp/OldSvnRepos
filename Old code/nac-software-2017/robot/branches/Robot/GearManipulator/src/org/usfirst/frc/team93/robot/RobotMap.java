package org.usfirst.frc.team93.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//CHECK - obviously we don't know the ports yet, these are just placeholders.

	//Motors
	public static int GearGrabberTopMotor = 7;
	public static int GearGrabberBottomMotor = 8;
	
	//Digital Inputs
	//I was told we'd have a photogate to tell if we've aquired a gear
	//From updates, I'm fairly certain we won't have a photogate anymore
	//public static int gearObtained = 1;
}
