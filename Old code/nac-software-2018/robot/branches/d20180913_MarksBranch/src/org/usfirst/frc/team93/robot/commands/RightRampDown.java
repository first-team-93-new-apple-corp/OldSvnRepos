package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RightRampDown extends Command
{
	
	public RightRampDown()
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.rightRamp);
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
		Robot.rightRamp.setMotors(1);
	}
	
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		return false;
	}
	
	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		Robot.rightRamp.setMotors(0);
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		this.end();
	}
}
