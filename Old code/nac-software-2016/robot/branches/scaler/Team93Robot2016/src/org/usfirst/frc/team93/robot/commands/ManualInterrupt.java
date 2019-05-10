package org.usfirst.frc.team93.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This class is a manual interrupt for any system in order to stop it.
 * 
 * @author Admin93
 */
public class ManualInterrupt extends Command
{

	/**
	 * 
	 * @param subsystemToStop
	 *            This is the is subsystem you wish to stop
	 */
	public ManualInterrupt(Subsystem subsystemToStop)
	{
		// Requires the passed subsystem, which interrupts any command already
		// running on that subsystem
		requires(subsystemToStop);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{

	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		return true;
	}

	// Called once after isFinished returns true
	@Override
	protected void end()
	{

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{

	}
}
