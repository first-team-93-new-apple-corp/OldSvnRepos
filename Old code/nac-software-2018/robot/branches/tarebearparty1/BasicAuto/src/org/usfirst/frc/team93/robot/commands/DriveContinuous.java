package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;
import org.usfirst.frc.team93.robot.utilities.DriveChooser;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveContinuous extends AngleVelocity
{
	
	public DriveContinuous()
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.drive);
		
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		resetAngularVelocity();
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		SmartDashboard.putNumber("gyro value", Drive.gyro.get());
		Drive.driveControlMode = (int) Robot.DriveMode.getSelected();
		Drive.left.set(DriveChooser.getDriverL());
		Drive.right.set(DriveChooser.getDriverR());
		updateAngularVelocity();
		SmartDashboard.putNumber("Angular Velocity", getAngularVelocity());
		
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
