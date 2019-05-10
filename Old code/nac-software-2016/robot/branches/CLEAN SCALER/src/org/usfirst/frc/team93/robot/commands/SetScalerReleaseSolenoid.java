package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Scaler;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetScalerReleaseSolenoid extends Command
{
	boolean m_released;

	public SetScalerReleaseSolenoid(boolean released)
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		m_released = released;
		requires(Robot.scaler);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		Scaler.setReleaseSolenoid(m_released);
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
