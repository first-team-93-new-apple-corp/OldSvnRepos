package org.usfirst.frc.team93.robot;

import edu.wpi.first.wpilibj.Talon;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//Hardware and Robot Components go here.
	public static Talon LEFT_MOTOR_ONE = new Talon(0);
	public static Talon LEFT_MOTOR_TWO = new Talon(1);
	
	public static Talon RIGHT_MOTOR_ONE = new Talon(2);
	public static Talon RIGHT_MOTOR_TWO = new Talon(3);
}
