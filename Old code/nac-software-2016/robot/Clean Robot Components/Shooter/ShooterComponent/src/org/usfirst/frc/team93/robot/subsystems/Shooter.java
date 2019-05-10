package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Does all basic functions for shooter including but not limited to detecting
 * if a ball is there, getting the angle of the shooter, setting the angle of
 * the shooter, and setting the speed for the shooter wheels.
 */
public class Shooter extends Subsystem
{

	private static boolean m_errorStable;

	private static boolean m_ballPulled = false;

	@Override
	public void initDefaultCommand()
	{

	}

	public static boolean ballPresent()
	{
		return !RobotMap.DetectBall.get();
	}

	public static void setStableError(boolean errorStable)
	{
		m_errorStable = errorStable;
	}

	public static boolean getStableError()
	{
		return m_errorStable;
	}

	public static void SetShooterWheels(double Speed)
	{
		RobotMap.LEFT_SHOOTER.set(Speed);
		RobotMap.RIGHT_SHOOTER.set(Speed);
	}

	public static void DoneShooting()
	{
		RobotMap.BallGuideMotor.set(0.0);
		RobotMap.LEFT_BACQUASITION.set(0.0);
		RobotMap.RIGHT_BACQUASITION.set(0.0);
		// RobotMap.BACQUASITION.set(0.0);
		SetShooterWheels(0.0);
		RobotMap.leftFiringSpeedControl.disable();
		RobotMap.rightFiringSpeedControl.disable();
	}

	public static boolean isBallPulled()
	{
		return m_ballPulled;
	}

	public static void setBallPulled(boolean ballPulled)
	{
		m_ballPulled = ballPulled;
	}
}
