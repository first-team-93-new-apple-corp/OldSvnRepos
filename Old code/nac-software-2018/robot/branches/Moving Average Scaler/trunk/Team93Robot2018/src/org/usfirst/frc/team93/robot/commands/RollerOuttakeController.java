package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Claw;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Outtakes the cube until it is interrupted
 */
public class RollerOuttakeController extends Command
{
	double m_percent = 0;
	
	public RollerOuttakeController()
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.claw);
	}
	
	public RollerOuttakeController(double percent)
	{
		m_percent = percent;
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
		Claw.leftIntake.set(-0.75);
		Claw.rightIntake.set(0.75);
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
		/*
		 * Claw.leftIntake.set(0);
		 * Claw.rightIntake.set(0);
		 */
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		this.end();
	}
}
