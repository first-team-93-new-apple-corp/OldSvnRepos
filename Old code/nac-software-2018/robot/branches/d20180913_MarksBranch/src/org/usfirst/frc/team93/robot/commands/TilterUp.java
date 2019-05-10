package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Tilter;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TilterUp extends Command
{
	
	public TilterUp()
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.tilter);
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		// Tilter.tilterController.reset();
		// Tilter.tilterController.setSetpoint(0);// Put top-most point here later
		// Tilter.tilterController.setAbsoluteTolerance(6);
		// Tilter.tilterController.enable();
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		Tilter.tilter.set(0.4);
	}
	
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		// return Tilter.tilterController.onTarget();
		return (Tilter.tilterEncoder.getRaw() <= 5);
	}
	
	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		// Tilter.tilterController.disable();
		Tilter.tilter.set(0.4);
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		Tilter.tilter.set(0);
		this.end();
	}
}
