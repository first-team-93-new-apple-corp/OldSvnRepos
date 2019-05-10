package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;

/**
 * Used for Auto, set the Robot to a certain relative angle
 */

public class TurnDeg extends AngleVelocity
{
	double m_waitTime;
	Boolean isFinished;
	public double m_tolerance;
	public int onTargetThreshold;
	public double m_degrees;
	
	public TurnDeg(double degrees, double tolerance)
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.drive);
		m_tolerance = tolerance;
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
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		// SmartDashboard.putNumber("set value", Drive.TurnPID.getSetpoint());
		// SmartDashboard.putNumber("gyro value", Drive.gyro.get());
		updateAngularVelocity();
		// SmartDashboard.putNumber("Angular Velocity", getAngularVelocity());
	}
	
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		return (Math.abs(getAngularVelocity()) <= 0.05) && Drive.TurnPID.onTarget();
	}
	
	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		Drive.TurnPID.disable();
		Drive.turn.pidWrite(0);
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		this.end();
	}
}
