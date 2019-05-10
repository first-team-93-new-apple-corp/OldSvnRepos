package org.usfirst.frc.team93.robot.commands.gearmanipulator;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command closes the gear manipulator's back claw.
 * 
 * @author NAC Controls
 *
 */
public class CloseBackClaw extends Command
{
	/**
	 * This command closes the gear manipulator's back claw.
	 */
	public CloseBackClaw()
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.gearManipulator);
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		GearManipulator.closeBackClaw();
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
		// have a wonderful day :]
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
