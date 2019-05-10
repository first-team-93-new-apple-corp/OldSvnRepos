package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.subsystems.GearManipulator;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This class is used to give the operator the ability to disable the compressor
 * for the pneumatics. This saves us a bit of current usage to avoid brown outs.
 */
public class ToggleCompressor extends Command
{
	/**
	 * @codereview josh.hawbaker 4.6.17 Added a javadoc and a few comments. Not
	 * sure how necessary this command is.
	 */
	
	/**
	 * This command turns the compressor on or off depending on its current
	 * state.
	 */
	public ToggleCompressor()
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		GearManipulator.setCompressorActive(!GearManipulator.getCompressorActive());
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
		// this command functions as an instant command, so end ASAP
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
		// not only does this not require a subsystem, but it runs instantly, so
		// there should never be time to interrupt it.
	}
}
