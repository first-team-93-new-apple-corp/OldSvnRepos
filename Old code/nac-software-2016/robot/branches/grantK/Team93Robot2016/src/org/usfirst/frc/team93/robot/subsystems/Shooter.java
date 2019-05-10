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

	double m_Angle;

	@Override
	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		// setDefaultCommand(new SetFiringAngle(1.0));
	}

	public static boolean BallPresent()
	{
		return RobotMap.CatapultReady.get();
	}

	public static void AdjustShootingAngle(double Speed)
	{
		// change later if needed
		if (!RobotMap.UpperFiringAngle.get())
		{
			if (!RobotMap.LowerFiringAngle.get())
			{
				RobotMap.FiringAngleTalon.set(Speed);
			} else if (Speed >= 0)
			{
				RobotMap.FiringAngleTalon.set(Speed);
			}
		} else if (Speed <= 0)
		{
			RobotMap.FiringAngleTalon.set(Speed);
		}
	}

	public static double TestShootingMotorsL()
	{
		return RobotMap.FireWheelsL.get();
	}

	public static double TestShootingMotorsR()
	{
		return RobotMap.FireWheelsR.get();
	}

	public static boolean TestSpeed(double WantedSpeed)
	{
		if (TestShootingMotorsL() <= ((.05 * WantedSpeed) + WantedSpeed)
				&& TestShootingMotorsL() >= (-1 * (.05 * WantedSpeed) + WantedSpeed))
		{
			if (TestShootingMotorsR() <= ((.05 * WantedSpeed) + WantedSpeed)
					&& TestShootingMotorsR() >= (-1 * (.05 * WantedSpeed) + WantedSpeed))
			{
				return true;
			}
		}
		return false;
	}

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
		RobotMap.FireWheelsL.set(Speed);
		RobotMap.FireWheelsR.set(Speed);
	}

	public static void Shoot()
	{
		RobotMap.GuideMotor.set(1.0);
		RobotMap.BACQUASITION.set(1.0);
	}

	public static void DoneShooting()
	{
		RobotMap.GuideMotor.set(0.0);
		RobotMap.BACQUASITION.set(0.0);
		SetShooterWheels(0.0);
	}
}