package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This class changes the set drive direction.
 */
public class ChangeDriveDirection extends Command
{
	/*
	 * Resets a drive direction as front. (check to confirm)
	 */

	int m_direction;

	public ChangeDriveDirection()
	{
		requires(Robot.drive);
	}

	@Override
	protected void initialize()
	{

		// TODO Auto-generated method stub
	}

	@Override
	protected void execute()
	{
		// TODO Auto-generated method stub
		m_direction = Drive.getDirection();
		Drive.SetDirection(-m_direction);
	}

	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		return true;
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
		this.end();
	}

}
