package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;

public class PrepareSetShooterWheels extends Command
{
	private double m_speed;
	private int timer;
	private int m_maxTime;

	public PrepareSetShooterWheels(double Speed, int maxTime)
	{
		m_speed = Speed;
		requires(Robot.shooter);
		timer = 0;
		m_maxTime = maxTime;
	}

	@Override
	protected void initialize()
	{
	}

	@Override
	protected void execute()
	{
		if (!Shooter.isBallPulled())
		{
			timer += 20;
			RobotMap.BallGuideMotor.set(m_speed);
		}
	}

	@Override
	protected boolean isFinished()
	{
		return (!Shooter.isBallPulled()) ? timer >= m_maxTime : true;
	}

	@Override
	protected void end()
	{
		RobotMap.BallGuideMotor.set(0.0);
		timer = 0;
		Shooter.setBallPulled(true);
	}

	@Override
	protected void interrupted()
	{
		RobotMap.BallGuideMotor.set(0.0);
		timer = 0;
	}

}
