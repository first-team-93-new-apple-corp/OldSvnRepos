
package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;

public class SetShootWheels extends Command
{
	/*
	 * This resets the shooter wheels to zero.
	 */
	double m_speed;

	public SetShootWheels(double Speed)
	{
		m_speed = Speed;
		requires(Robot.shooter);
	}

	@Override
	protected void initialize()
	{
		// TODO Auto-generated method stub
		Shooter.SetShooterWheels(m_speed);
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
		Shooter.SetShooterWheels(0.0);
	}

	@Override
	protected void interrupted()
	{
		// TODO Auto-generated method stub
		this.end();
	}

}
