package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */

public class TurnDeg extends AngleVelocity
{
	double onTarget;
	public double m_tolerance;
	public int onTargetReps;
	public int onTargetThreshold;
	public double m_degrees;
	
	public TurnDeg(double degrees, double tolerance)
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.drive);
		m_tolerance = tolerance;
		onTargetReps = 0;
		onTargetThreshold = 50;
		m_degrees = degrees;
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		Drive.TurnPID.reset();
		Drive.TurnPID.setSetpoint(Drive.gyro.get() + m_degrees);
		Drive.TurnPID.setAbsoluteTolerance(m_tolerance);
		Drive.TurnPID.enable();
		resetAngularVelocity();
		onTarget = 0;
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		SmartDashboard.putNumber("set value", Drive.TurnPID.getSetpoint());
		SmartDashboard.putNumber("gyro value", Drive.gyro.get());
		updateAngularVelocity();
		SmartDashboard.putNumber("Angular Velocity", getAngularVelocity());
		if (Drive.TurnPID.onTarget())
		{
			onTarget++;
		}
		else
		{
			onTarget = 0;
		}
	}
	
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		return ((getAngularVelocity() <= 0.05) && Drive.TurnPID.onTarget()) && onTarget >= 20;
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
