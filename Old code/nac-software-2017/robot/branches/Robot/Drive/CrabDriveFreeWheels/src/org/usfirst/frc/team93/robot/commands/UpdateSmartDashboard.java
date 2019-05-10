package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Put SmartDashboard updating logic here.
 */
public class UpdateSmartDashboard extends Command
{
	
	public UpdateSmartDashboard()
	{
		// Use requires() here to declare subsystem dependencies
		requires(Robot.smartDashboardWriter);
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
		SmartDashboard.putNumber("LEFT FRONT ENCODER: ", Drive.DRIVE_ENCODER_LEFT_FRONT.get());
		SmartDashboard.putNumber("LEFT BACK ENCODER: ", Drive.DRIVE_ENCODER_LEFT_BACK.get());
		SmartDashboard.putNumber("RIGHT FRONT ENCODER: ", Drive.DRIVE_ENCODER_RIGHT_FRONT.get());
		SmartDashboard.putNumber("RIGHT BACK ENCODER: ", Drive.DRIVE_ENCODER_RIGHT_BACK.get());
		SmartDashboard.putNumber("MEDIAN ENCODER READING: ", Drive.DRIVE_ENCODERS.get());
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
