package org.usfirst.frc.team93.robot.commands.gearmanipulator;

import org.usfirst.frc.team93.robot.subsystems.GearManipulator;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command sets the gear manipulator's deflector to the up position.
 * 
 * @author NAC Controls
 *
 */
public class DeflectorUp extends Command
{
	/**
	 * This command sets the gear manipulator's deflector to the up position.
	 */
	public DeflectorUp()
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
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
		GearManipulator.setDeflectorUp();
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