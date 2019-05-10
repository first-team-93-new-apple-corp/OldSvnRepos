package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.internal.HardwareTimer;

/**
 *
 */

public class AngleVelocity extends Command
{
	private double pastTime;
	private double currentTime;
	private double pastAngle;
	private double currentAngle;
	private double angularVelocity;
	private HardwareTimer timer;
	
	public AngleVelocity()
	{
		timer = new HardwareTimer();
		
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
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
	
	public double getAngularVelocity()
	{
		return angularVelocity;
	}
	
	protected void resetAngularVelocity()
	{
		pastTime = currentTime;
		currentTime = timer.getFPGATimestamp();
		pastAngle = currentAngle;
		currentAngle = Drive.gyro.getAngle();
		pastTime = 0;
		currentTime = 1;
		pastAngle = 0;
		currentAngle = 999999999;
	}
	
	protected void updateAngularVelocity()
	{
		pastTime = currentTime;
		currentTime = timer.getFPGATimestamp();
		pastAngle = currentAngle;
		currentAngle = Drive.gyro.getAngle();
		double deltaT = currentTime - pastTime;
		double deltaAngle = currentAngle - pastAngle;
		angularVelocity = deltaAngle / deltaT;
	}
}
