package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Scaler;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetScalerLocation extends Command
{
	Scaler.ScalerLocation m_location;
	
	public SetScalerLocation(Scaler.ScalerLocation location)
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.scaler);
		m_location = location;
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
		// Scaler.
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
