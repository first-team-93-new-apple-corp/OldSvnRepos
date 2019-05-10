package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.Utilities.Interpolate;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Does all basic functions for shooter including but not limited to detecting
 * if a ball is there, getting the angle of the shooter, setting the angle of
 * the shooter, and setting the speed for the shooter wheels.
 */
public class Shooter extends Subsystem
{

	private static boolean hasBoulder = false;
	private static boolean expectingBoulder = true;

	private static double m_FiringTicks = 0.0;
	private static double m_speed = 0.0;
	private static double m_angle = 0.0;
	private static double m_waitTime = 1.0; // This time should be how long it
											// take to get motors up to speed
	private static double m_distance = 0.0;
	private static double m_angleTicks = 0.0;

	@Override
	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		// setDefaultCommand(new SetFiringAngle(1.0));
	}

	/**
	 * 
	 * @codereview ColbyMcKinley: is DetectBall a limit switch or light sensor?
	 */
	public static boolean BallPresent()
	{
		return RobotMap.DetectBall.get();
	}

	/**
	 * 
	 * @codereview ColbyMcKinley: Are we still using a pot
	 */
	public static double GetAngle()
	{
		// change back later
		return RobotMap.SHOOTING_ANGLE.getAngle();
	}

	public static void ManualAdjustShootingAngle(double Speed)
	{
		// change if needed later
		// Are limit switches used?
		if (!RobotMap.UpperFiringAngle.get())
		{
			if (!RobotMap.LowerFiringAngle.get())
			{
				RobotMap.ShootinAngleTalon.set(Speed);
			} else if (Speed >= 0)
			{
				RobotMap.ShootinAngleTalon.set(Speed);
			}
		} else if (Speed <= 0)
		{
			RobotMap.ShootinAngleTalon.set(Speed);
		}
	}

	public void setMotors(double searchDistance)
	{
		m_distance = searchDistance;
		m_speed = findFireSpeed(m_distance);
		RobotMap.LEFT_SHOOTER.set(m_speed);
		RobotMap.RIGHT_SHOOTER.set(m_speed);
		// configure angle of shooter based on output angle
		m_angle = findFireAngleTicks(m_distance);
		setAngle(m_angle);
	}

	public static void setAngle(double angle)
	{

		m_FiringTicks = 255 * (angle / 360);
		RobotMap.firingAngleControl.enable();
		RobotMap.firingAngleControl.setSetpoint(m_FiringTicks);

	}

	/**
	 * This function determines if the shooter is ready to shoot
	 * 
	 * @param WantedSpeed
	 * @return the boolean state if two motors are the same speed the shooter
	 *         has a ball
	 */
	public static boolean ReadyToShoot(double WantedSpeed)
	{
		if (TestSpeed(WantedSpeed) && BallPresent())
		{
			return true;
		}
		return false;
	}

	public static void SetShooterWheels(double Speed)
	{
		RobotMap.LEFT_SHOOTER.set(Speed);
		RobotMap.RIGHT_SHOOTER.set(Speed);
	}

	public static void manualShoot()
	{
		RobotMap.BallGuideMotor.set(1.0);

		RobotMap.LEFT_BACQUASITION.set(1.0);
		RobotMap.RIGHT_BACQUASITION.set(1.0);
	}

	public static void DoneShooting()
	{
		RobotMap.BallGuideMotor.set(0.0);
		RobotMap.LEFT_BACQUASITION.set(0.0);
		RobotMap.RIGHT_BACQUASITION.set(0.0);
		hasBoulder = false;
		// RobotMap.BACQUASITION.set(0.0);
		SetShooterWheels(0.0);
	}

	// public static void fireBall(double speed)
	// {
	// m_speed = speed;
	// RobotMap.LEFT_SHOOTER.set(m_speed);
	// RobotMap.RIGHT_SHOOTER.set(m_speed);
	// new SleepCommand(m_waitTime);
	// RobotMap.LEFT_BACQUASITION.set(speed);
	// RobotMap.RIGHT_BACQUASITION.set(speed);
	// }
	//
	// public static void fireBallDistance(double distance)
	// {
	// m_distance = distance;
	// m_speed = findFireSpeed(m_distance);
	// m_angle = findFireAngleTicks(m_distance);
	// RobotMap.LEFT_SHOOTER.set(m_speed);
	// RobotMap.RIGHT_SHOOTER.set(m_speed);
	// setAngle(m_angle);
	// if (TestSpeed(m_speed))
	// {
	// RobotMap.LEFT_BACQUASITION.set(1.0); // Should not be speed //?
	// RobotMap.RIGHT_BACQUASITION.set(1.0);
	// }
	// }

	/**
	 * This function finds the angle then converts it to ticks
	 * 
	 * @param distance
	 *            the distance to search on
	 * @return the angle in encoder ticks
	 */
	public static double findFireAngleTicks(double distance)
	{
		m_distance = distance;

		m_angleTicks = 255 * Interpolate.getAngle(m_distance) / 360;
		return m_angleTicks;
	}

	public static double findFireSpeed(double distance)
	{
		m_distance = distance;
		m_speed = Interpolate.getRPM(m_distance) / 4; // 4:1 gear ratio
		return m_speed;
	}

	/**
	 * Tests the speed of the motors to see if they are equal
	 * 
	 * @param WantedSpeed
	 *            desired speed for both motors
	 * @return the boolean state if two motors are at the same speed
	 */
	public static boolean TestSpeed(double WantedSpeed)
	{
		if (RobotMap.LEFT_SHOOTER.getSpeed() <= ((.05 * WantedSpeed) + WantedSpeed)
				&& RobotMap.LEFT_SHOOTER.getSpeed() >= (-1 * (.05 * WantedSpeed) + WantedSpeed))
		{
			if (RobotMap.RIGHT_SHOOTER.getSpeed() <= ((.05 * WantedSpeed) + WantedSpeed)
					&& RobotMap.RIGHT_SHOOTER.getSpeed() >= (-1 * (.05 * WantedSpeed) + WantedSpeed))
			{
				return true;
			}
		}
		return false;
	}

}
