package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This class shifts into HighGear
 * 
 * @author NAC Controls
 * @codereview ColbyMcKinley: Would it be better for our needs for a toggler?
 */
public class GearshiftHigh extends Command
{

	public GearshiftHigh()
	{
		// TODO Auto-generated constructor stub
		requires(Robot.drive);
	}

	@Override
	protected void initialize()
	{
	}

	@Override
	protected void execute()
	{
		Drive.shiftHighGear();
	}

	@Override
	protected void end()
	{

	}

	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void interrupted()
	{
		// TODO Auto-generated method stub

	}

}
