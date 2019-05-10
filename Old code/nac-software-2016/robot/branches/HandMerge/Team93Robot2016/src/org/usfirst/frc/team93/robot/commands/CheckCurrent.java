package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This class checks the current at a port
 * 
 * @author NAC Controls
 *
 */
public class CheckCurrent extends Command
{

	public CheckCurrent()
	{

	}

	@Override
	protected void initialize()
	{
		// TODO Auto-generated method stub

	}

	/**
	 * @codereview ColbyMcKinley: we should check mulitple motors
	 */
	@Override
	protected void execute()
	{
		// TODO Auto-generated method stub
		/*
		 * checks to see if current is too high
		 */
		if (RobotMap.LimitedLeftMotor.IsCurrentFine())
		{
			RobotMap.LimitedLeftMotor.set(0.0);
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
