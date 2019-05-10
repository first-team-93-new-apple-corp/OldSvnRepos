package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class SetFiringAngle extends Command
{

	private double m_FiringAngle;
	double m_FiringTicks;

	public SetFiringAngle(double FiringAngle)
	{
		m_FiringAngle = FiringAngle;
	}

	@Override
	protected void initialize()
	{
		// TODO Auto-generated method stub
		m_FiringTicks = 255 * (m_FiringAngle / 360);
		RobotMap.firingAngleControl.enable();
		RobotMap.firingAngleControl.setSetpoint(m_FiringTicks);
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
		RobotMap.firingAngleControl.disable();
		RobotMap.ShootinAngleTalon.set(0.0);
	}

	@Override
	protected void interrupted()
	{
		// TODO Auto-generated method stub

	}

}
