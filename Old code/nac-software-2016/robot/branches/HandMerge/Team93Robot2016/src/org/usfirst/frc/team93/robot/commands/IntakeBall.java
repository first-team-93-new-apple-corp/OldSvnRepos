package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeBall extends Command
{

	double m_speed;

	public IntakeBall(double Speed)
	{
		requires(Robot.shooter);
		m_speed = Speed;
	}

	@Override
	protected void initialize()
	{
		// TODO Auto-generated method stub
		RobotMap.BACQUASITION.set(m_speed);
		RobotMap.BallGuideMotor.set(m_speed);
	}

	@Override
	protected void execute()
	{
		// TODO Auto-generated method stub
		if (Shooter.BallPresent())
		{
			this.end();
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
		RobotMap.BACQUASITION.set(0.0);
		RobotMap.BallGuideMotor.set(0.0);
	}

	@Override
	protected void interrupted()
	{
		// TODO Auto-generated method stub
		this.end();
	}

}
