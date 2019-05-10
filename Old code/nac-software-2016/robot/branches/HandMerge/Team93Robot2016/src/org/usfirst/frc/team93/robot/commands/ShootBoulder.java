package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;

public class ShootBoulder extends Command
{
	/*
	 * This commands the shooter to shoot the ball when ready, if not, sleep
	 * would run.
	 */
	SleepCommand sleep;
	double m_speed;
	boolean didShoot;

	public ShootBoulder(double Speed)
	{
		requires(Robot.shooter);
		sleep = new SleepCommand(2.0);
		m_speed = Speed;
	}

	@Override
	protected void initialize()
	{
		didShoot = false;
		if (Shooter.BallPresent() && Shooter.ReadyToShoot(m_speed))
		{
			Shooter.manualShoot();
			sleep.start();
			didShoot = true;
		}

	}

	@Override
	protected void execute()
	{
		if (didShoot)
		{
			if (!sleep.isRunning())
			{
				this.end();
			}
		}
	}

	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end()
	{
		// TODO Auto-generated method stub
		Shooter.DoneShooting();
	}

	@Override
	protected void interrupted()
	{
		// TODO Auto-generated method stub

	}

}
