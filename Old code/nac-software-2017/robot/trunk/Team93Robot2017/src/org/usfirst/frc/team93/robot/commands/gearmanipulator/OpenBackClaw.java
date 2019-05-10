package org.usfirst.frc.team93.robot.commands.gearmanipulator;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command opens the back/bottom claw on the gear manipulator
 */
public class OpenBackClaw extends Command
{
	/**
	 * @codereview josh.hawbaker 3.20.17 This class functions as an instant
	 * command, so the actions should be done in initialize rather than execute.
	 * Also added javadoc and a few comments
	 */
	public OpenBackClaw()
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.gearManipulator);
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		GearManipulator.openBackClaw();
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
		// this command functions as an instant command, so we end it ASAP
		return true;
	}
	
	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		// for end and interrupted: this command runs instantly, so there is no
		// need to interrupt it
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
	}
}
