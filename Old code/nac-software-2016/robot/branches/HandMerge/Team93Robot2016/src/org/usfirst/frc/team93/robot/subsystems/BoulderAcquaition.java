package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class BoulderAcquaition extends Subsystem
{

	/*
	 * diff between expectingBoulder here and hasBoulder in Shooter sub?
	 */
	private static boolean expectingBoulder = true;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	@Override
	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		// setDefaultCommand(new RecoverBall());
	}

	public static boolean getBallDetect()
	{
		return (RobotMap.DetectBall.get());
	}

	public static void turnBallRollers(double speed)
	{
		RobotMap.LEFT_BACQUASITION.set(speed);
		RobotMap.RIGHT_BACQUASITION.set(speed);

	}

}
