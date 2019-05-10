package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class SetShootWheelsPID extends Command
{

	double m_speed;
	double m_error;

	public SetShootWheelsPID(double Speed, double Error)
	{
		m_speed = Speed;
		m_error = Error;
		requires(Robot.shooter);
	}

	@Override
	protected void initialize()
	{
		// TODO Auto-generated method stub
		RobotMap.shooterSpeedControl.reset();
		RobotMap.shooterSpeedControl.enable();
		RobotMap.shooterSpeedControl.setSetpoint(m_speed);
	}

	@Override
	protected void execute()
	{
		// TODO Auto-generated method stub

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
		RobotMap.shooterSpeedControl.disable();
	}

	@Override
	protected void interrupted()
	{
		// TODO Auto-generated method stub

	}

}
