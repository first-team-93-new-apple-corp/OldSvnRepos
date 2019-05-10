package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This class shifts drive into LowGear
 * 
 * @author NAC Controls
 * @codereivew ColbyMcKinley: Would it be better for our needs for a toggler?
 */
public class GearshiftLow extends Command
{

	public GearshiftLow()
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
		Drive.shiftLowGear();
	}

	@Override
	protected void end()
	{
		// TODO Auto-generated method stub

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
