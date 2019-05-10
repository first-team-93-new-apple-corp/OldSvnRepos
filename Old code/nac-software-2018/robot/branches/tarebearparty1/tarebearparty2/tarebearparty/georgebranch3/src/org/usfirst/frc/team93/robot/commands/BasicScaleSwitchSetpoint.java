package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Scaler;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BasicScaleSwitchSetpoint extends Command
{
	
	/**
	 * Enables a basic PID with just a P to get the scaler to the switch setpoint:
	 * WHEN IN FINISHED STATE DO NOT USE!!!!!
	 * 
	 */
	public BasicScaleSwitchSetpoint()
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.scaler);
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		Scaler.ScaleController.reset();
		Scaler.ScaleController.setSetpoint(6000);
		Scaler.ScaleController.setAbsoluteTolerance(5);
		Scaler.ScaleController.enable();
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
		return false;
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
