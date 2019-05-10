package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.subsystems.Scaler;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.internal.HardwareTimer;

/**
 * Wrapper used for getting the velocity of the scaler
 */

public class ScalerVelocity extends Command
{
	private double pastTime;
	private double currentTime;
	private double pastLocation;
	private double currentLocation;
	private double velocity;
	private HardwareTimer timer;
	
	public ScalerVelocity()
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
	
	public double getVelocity()
	{
		return velocity;
	}
	
	protected void resetEncoderVelocity()
	{
		pastTime = currentTime;
		currentTime = timer.getFPGATimestamp();
		pastLocation = currentLocation;
		currentLocation = Scaler.ScalerEncoder.getRaw();
		pastTime = 0;
		currentTime = 1;
		pastLocation = 0;
		currentLocation = 999999999;
	}
	
	protected void updateEncoderVelocity()
	{
		pastTime = currentTime;
		currentTime = timer.getFPGATimestamp();
		pastLocation = currentLocation;
		currentLocation = Scaler.ScalerEncoder.getRaw();
		double deltaT = currentTime - pastTime;
		double deltaLoc = currentLocation - pastLocation;
		velocity = deltaLoc / deltaT;
	}
}
