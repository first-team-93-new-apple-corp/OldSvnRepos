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

	public static final int FRONT_LEFT_INDEX = 0;
	public static final int FRONT_RIGHT_INDEX = 1;
	public static final int BACK_LEFT_INDEX = 2;
	public static final int BACK_RIGHT_INDEX = 3;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	public static Victor frontRight;
	public static Victor frontLeft;
	// public static Victor middleRight;
	// public static Victor middleLeft;
	public static Victor backRight;
	public static Victor backLeft;
	public static DoubleSolenoid Gearshift;
	public static PowerDistributionPanel powerDistribution;

	public static int[] SteeringMotorTestSequence = new int[]
	{ FRONT_RIGHT_INDEX, BACK_RIGHT_INDEX, FRONT_LEFT_INDEX, BACK_LEFT_INDEX };

	public RobotMap()
	{
		powerDistribution = new PowerDistributionPanel();
		frontLeft = new Victor(FRONT_LEFT_INDEX);
		// middleLeft = new Victor(5);
		backLeft = new Victor(BACK_LEFT_INDEX);
		frontRight = new Victor(FRONT_RIGHT_INDEX);
		// middleRight = new Victor(0);
		backRight = new Victor(BACK_RIGHT_INDEX);
		Gearshift = new DoubleSolenoid(2, 3);

	}
}
