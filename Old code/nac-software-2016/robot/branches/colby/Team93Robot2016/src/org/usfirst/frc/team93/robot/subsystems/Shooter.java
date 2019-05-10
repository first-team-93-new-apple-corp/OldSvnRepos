package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Shooter extends Subsystem
{

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
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

	public static double GetAngle()
	{
		// change back later
		return RobotMap.ARM_POSITION.getAngle();
	}

	public static void AdjustShootingAngle(double Speed)
	{
		// change if needed later
		if (!RobotMap.UpperFiringAngle.get())
		{
			if (!RobotMap.LowerFiringAngle.get())
			{
				// RobotMap.FiringAngle.set(Speed);
			} else if (Speed >= 0)
			{
				// RobotMap.FiringAngle.set(Speed);
			}
		} else if (Speed <= 0)
		{
			// RobotMap.FiringAngle.set(Speed);
		}
	}
	/*
	 * Goes in Shooter subsystem
	 */
	// public void setMotors(double searchDistance)
	// {
	// double speed;
	// speed = getRPMInterpolation(searchDistance) / 4; // gear box is 4:1
	// // rpms
	// RobotMap.TURN_LEFT_SHOOTER.set(speed);
	// RobotMap.TURN_RIGHT_SHOOTER.set(speed);
	// // configure angle of shooter based on output angle
	// double angle = getAngleInterpulation(searchDistance);
	// RobotMap.RETRACT_SHOOTER.set(angle);
	// }
	// public static void ShootCatapult()
	// {
	// // code for catapult
	// RobotMap.ShootingMotor.set(0.0);
	// }
	//
	// public static void RetractCatapult()
	// {
	// // code for catapult
	// RobotMap.ShootingMotor.set(-1.0);
	// }

	// public static double TestShootingMotorsL()
	// {
	// //return RobotMap.FireWheelsL.getSpeed();
	// }
	//
	// public static double TestShootingMotorsR()
	// {
	// //return RobotMap.FireWheelsR.getSpeed();
	// }

	public static boolean TestSpeed(double WantedSpeed)
	{
		// if (TestShootingMotorsL() <= ((.05 * WantedSpeed) + WantedSpeed)
		// && TestShootingMotorsL() >= (-1 * (.05 * WantedSpeed) + WantedSpeed))
		// {
		// if (TestShootingMotorsR() <= ((.05 * WantedSpeed) + WantedSpeed)
		// && TestShootingMotorsR() >= (-1 * (.05 * WantedSpeed) + WantedSpeed))
		// {
		// return true;
		// }
		// }
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
		// RobotMap.FireWheelsL.set(Speed);
		// RobotMap.FireWheelsR.set(Speed);
	}

	public static void Shoot()
	{
		// add shooting code
		// RobotMap.GuideMotor.set(1.0);
		// RobotMap.BACQUASITION.set(1.0);

	}

	public static void DoneShooting()
	{
		// RobotMap.GuideMotor.set(0.0);
		// RobotMap.BACQUASITION.set(0.0);
		SetShooterWheels(0.0);
	}
}
