package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */

public class TurnToAngle extends Command
{
	public double m_angle;
	public double m_error;
	
	public TurnToAngle(double angle, double error)
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.drive);
		m_angle = angle;
		m_error = error;
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		Drive.TurnPID.reset();
		Drive.TurnPID.setSetpoint(m_angle);
		Drive.TurnPID.setAbsoluteTolerance(m_error);
		Drive.TurnPID.enable();
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		SmartDashboard.putNumber("gyro value", Drive.gyro.get());
	}
	
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		return Drive.TurnPID.onTarget();
	}
	
	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		Drive.turn.pidWrite(0);
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
	}
}
