package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */

public class Turn45Deg extends Command
{
	public double m_tolerance;
	public int onTargetReps;
	public int onTargetThreshold;
	
	public Turn45Deg(double tolerance)
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.drive);
		m_tolerance = tolerance;
		onTargetReps = 0;
		onTargetThreshold = 50;
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		Drive.TurnPID.reset();
		Drive.TurnPID.setSetpoint(Drive.gyro.get() + 45);
		Drive.TurnPID.setAbsoluteTolerance(m_tolerance);
		Drive.TurnPID.enable();
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		SmartDashboard.putNumber("set value", Drive.TurnPID.getSetpoint());
		SmartDashboard.putNumber("gyro value", Drive.gyro.get());
	}
	
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		if (Drive.TurnPID.onTarget())
		{
			onTargetReps++;
		}
		else
		{
			onTargetReps = 0;
		}
		if (onTargetReps >= onTargetThreshold)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		Drive.turn.pidWrite(0);
		Drive.TurnPID.disable();
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		this.end();
	}
}
