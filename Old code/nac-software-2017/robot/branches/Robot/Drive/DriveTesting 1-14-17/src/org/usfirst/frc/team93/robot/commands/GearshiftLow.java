package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.subsystems.GearshiftSub;

import edu.wpi.first.wpilibj.command.Command;

public class GearshiftLow extends Command
{

	public GearshiftLow()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initialize()
	{
	}

	@Override
	protected void execute()
	{
		GearshiftSub.shiftLowgear();
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
