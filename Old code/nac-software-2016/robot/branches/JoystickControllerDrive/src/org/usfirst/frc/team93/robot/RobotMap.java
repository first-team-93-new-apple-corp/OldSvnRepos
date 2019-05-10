package org.usfirst.frc.team93.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Victor;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap
{
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	public static Victor frontRight;
	public static Victor frontLeft;
	public static Victor middleRight;
	public static Victor middleLeft;
	public static Victor backRight;
	public static Victor backLeft;
	public static DoubleSolenoid Gearshift;
	public static PowerDistributionPanel powerDistribution;

	public RobotMap()
	{
		powerDistribution = new PowerDistributionPanel();
		frontLeft = new Victor(3);
		middleLeft = new Victor(5);
		backLeft = new Victor(4);
		frontRight = new Victor(2);
		middleRight = new Victor(0);
		backRight = new Victor(1);
		Gearshift = new DoubleSolenoid(2, 3);
	}
}
