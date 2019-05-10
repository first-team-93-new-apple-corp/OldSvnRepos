package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.Utilities.Interpolate;
import org.usfirst.frc.team93.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;

public class InterpolationFiring extends Command
{

	double m_distance;
	double m_angle;
	double m_speed;
	double m_FiringTicks;
	double m_FiringAngle;

	public InterpolationFiring(double distance)
	{
		m_distance = distance;
		m_FiringAngle = Interpolate.getAngle(m_distance);
		requires(Robot.shooter);
	}

	@Override
	protected void initialize()
	{
		// TODO Auto-generated method stub
		m_angle = Shooter.findFireAngleTicks(m_distance);
		m_speed = Shooter.findFireSpeed(m_distance);
	}

	@Override
	protected void execute()
	{
		// TODO Auto-generated method stub
		if (RobotMap.firingAngleControl.get()
				- RobotMap.firingAngleControl.getError() < (m_FiringTicks = 255 * (m_FiringAngle / 360)))
		{
			RobotMap.firingAngleControl.enable();
			RobotMap.firingAngleControl.setSetpoint(m_FiringTicks);
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

	}

	@Override
	protected void interrupted()
	{
		// TODO Auto-generated method stub

	}

}
