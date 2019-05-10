package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.commands.RecoverBall;
import org.usfirst.frc.team93.robot.commands.SleepCommand;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntegratedBallAcqShooter extends Subsystem
{

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	private static boolean hasBoulder = false;
	private static boolean expectingBoulder = true;

	private static double m_speed = 0.0;
	private static double m_angle = 0.0;
	private static double m_waitTime = 1.0; // This time should be how long it
											// take to get motors up to speed
	private static double m_distance = 0.0;
	/*
	 * private enum ArmPosition{ apTop_t, apBottom_t };
	 */

	@Override
	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		setDefaultCommand(new RecoverBall(1.0));
	}

	public static boolean getBallDetect()
	{
		// Create threshold for light sensor
		return (RobotMap.DetectBall.get());
	}

	public static void turnRollers(double motorSpeed)
	{
		if (expectingBoulder)
		{
			RobotMap.LEFT_BACQUASITION.set(motorSpeed);
			RobotMap.RIGHT_BACQUASITION.set(motorSpeed);
		}

	}

	public static void setArmPositionLow()
	{
		RobotMap.firingAngleControl.setSetpoint(1);
		setExecptingBoulder();
	}

	public static void setArmPositionHigh()
	{
		RobotMap.firingAngleControl.setSetpoint(10);

	}

	// public static void raiseShooter()
	// {
	// RobotMap.DOZER_LEFT.set(DoubleSolenoid.Value.kForward);
	// RobotMap.DOZER_RIGHT.set(DoubleSolenoid.Value.kForward);
	// }
	//
	// public static void lowerShooter()
	// {
	// expectingBoulder = true;
	// RobotMap.DOZER_LEFT.set(DoubleSolenoid.Value.kReverse);
	// RobotMap.DOZER_RIGHT.set(DoubleSolenoid.Value.kReverse);
	// }

	// public static void acquireBall()
	// {
	// if (!getBallDetect() && expectingBoulder)
	// {
	// lowerShooter();
	// } else
	// {
	// raiseShooter();
	// }
	// }

	private static void setExecptingBoulder()
	{
		expectingBoulder = true;

	}

	private static void setHasBoulder(boolean boulderState)
	{
		// incorperate some algorythem to determine if we have the ball using
		// pre-exsiting boolean expectingBoulder
		hasBoulder = boulderState;
	}

	public static void fireBall(double speed)
	{
		m_speed = speed;
		RobotMap.LEFT_SHOOTER.set(m_speed);
		RobotMap.RIGHT_SHOOTER.set(m_speed);
		new SleepCommand(m_waitTime);
		RobotMap.LEFT_BACQUASITION.set(speed); // Should not be speed
		RobotMap.RIGHT_BACQUASITION.set(speed);
	}

	public static void fireBallAngle(double speed, double angle)
	{
		m_speed = speed;
		m_angle = angle;
		// TODO develope algortyhem using desire angle position to deterime on
		// how to shoot it
		// refernce linear interpulation
	}

	public static void fireBallDistance(double speed, double distance)
	{
		// reference linear interpulation
		m_speed = speed;
		m_distance = distance;
	}

	/*
	 * Use linear interpulation code inplace
	 */
}
