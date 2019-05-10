package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeBall extends Command
{

	double m_speed;
	int m_time;
	int m_maxTime;

	public IntakeBall(double Speed, int time)
	{
		requires(Robot.shooter);
		m_speed = Speed;
	}

	@Override
	protected void initialize()
	{
		m_time = 0;
	}

	@Override
	protected void execute()
	{
		// CHANGE IN DULUTH
		RobotMap.LEFT_BACQUASITION.set(m_speed);
		RobotMap.RIGHT_BACQUASITION.set(-m_speed);
		RobotMap.BallGuideMotor.set(-1 * m_speed);
		if (Shooter.ballPresent())
		{
			m_time += 20;
		} else
		{
			m_time = 0;
		}
	}

	@Override
	protected boolean isFinished()
	{
		if (Shooter.ballPresent() && m_time >= m_maxTime)
		{
			return true;
		}
		return false;
	}

	@Override
	protected void end()
	{
		RobotMap.LEFT_BACQUASITION.set(0.0);
		RobotMap.RIGHT_BACQUASITION.set(0.0);
		RobotMap.BallGuideMotor.set(0.0);
	}

	@Override
	protected void interrupted()
	{
		this.end();
	}

}
