package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This class chan
 */
public class ChangeDriveDirection extends Command
{
	/*
	 * Resets a drive direction as front. (check to confirm)
	 */
	public ChangeDriveDirection()
	{
		requires(Robot.drive);
	}

	@Override
	protected void initialize()
	{
		// TODO Auto-generated method stub
		Drive.SetDirection(-1);
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
		Drive.SetDirection(1);
	}

	@Override
	protected void interrupted()
	{
		// TODO Auto-generated method stub
		this.end();
	}

}
