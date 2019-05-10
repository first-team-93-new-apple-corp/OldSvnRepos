package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Claw;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Intakes the cube until it reaches both limit switches
 */
public class RollerIntakeController extends Command
{
	
	public RollerIntakeController()
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.claw);
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
		if (!Claw.leftLimit.get())
		{
			Claw.leftIntake.set(1);
		}
		else
		{
			Claw.leftIntake.set(0);
		}
		if (!Claw.RightLimit.get())
		{
			Claw.rightIntake.set(1);
		}
		else
		{
			Claw.leftIntake.set(0);
		}
	}
	
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		return (Claw.leftLimit.get() && Claw.RightLimit.get());
	}
	
	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		Claw.leftIntake.set(0);
		Claw.rightIntake.set(0);
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		this.end();
	}
}
