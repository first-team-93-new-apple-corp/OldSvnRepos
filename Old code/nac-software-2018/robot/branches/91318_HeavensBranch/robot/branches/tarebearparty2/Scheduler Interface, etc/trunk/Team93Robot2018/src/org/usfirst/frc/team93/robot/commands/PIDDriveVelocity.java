package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.internal.HardwareTimer;

/**
 * Gets the velocity of the Drive and uses it to end the DriveEncoderTicks
 * command
 */
public class PIDDriveVelocity extends Command
{
	private double pastTime;
	private double currentTime;
	private double pastLEncoderLocation;
	private double pastREncoderLocation;
	private double currentLEncoderLocation;
	private double currentREncoderLocation;
	private double LEncoderVel;
	private double REncoderVel;
	private HardwareTimer timer;
	
	public PIDDriveVelocity()
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
	
	public double getLEncoderVelocity()
	{
		return LEncoderVel;
	}
	
	public double getREncoderVelocity()
	{
		return REncoderVel;
	}
	
	protected void updateEncoderVelocity()
	{
		pastTime = currentTime;
		currentTime = timer.getFPGATimestamp();
		pastLEncoderLocation = currentLEncoderLocation;
		pastREncoderLocation = currentREncoderLocation;
		currentLEncoderLocation = Drive.LeftDriveEncoder.getRaw();
		currentREncoderLocation = Drive.RightDriveEncoder.getRaw();
		double deltaT = (currentTime - pastTime) * 1000;
		double deltaREncoder = currentREncoderLocation - pastREncoderLocation;
		double deltaLEncoder = currentLEncoderLocation - pastLEncoderLocation;
		LEncoderVel = deltaLEncoder / deltaT;
		REncoderVel = deltaREncoder / deltaT;
		
	}
	
	protected void resetEncoderVelocity()
	{
		pastTime = currentTime;
		currentTime = timer.getFPGATimestamp();
		currentLEncoderLocation = Drive.LeftDriveEncoder.getRaw();
		currentREncoderLocation = Drive.RightDriveEncoder.getRaw();
		pastTime = 0;
		currentTime = 1;
		pastREncoderLocation = 0;
		pastREncoderLocation = 0;
		currentREncoderLocation = 999999999;
		currentLEncoderLocation = 999999999;
	}
}
