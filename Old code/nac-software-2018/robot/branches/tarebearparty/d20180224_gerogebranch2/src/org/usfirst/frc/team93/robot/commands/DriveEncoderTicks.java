package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drives forwards for a set number or encoder ticks given in the initializer
 */
public class DriveEncoderTicks extends PIDDriveVelocity
{
	
	double m_ticks;
	
	/**
	 * 
	 * @param ticks
	 *            The number of ticks that the encoders need to travel
	 */
	public DriveEncoderTicks(double ticks)
	{
		m_ticks = ticks;
		requires(Robot.drive);
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		Drive.RightWheelDrive.reset();
		SmartDashboard.putNumber("RightSetpoint", m_ticks + Drive.RightDriveEncoder.getRaw());
		Drive.RightWheelDrive.setSetpoint((m_ticks + Drive.RightDriveEncoder.getRaw()));
		Drive.RightWheelDrive.setAbsoluteTolerance(Drive.ABSOLUTEDRIVEPIDTOLERANCES);
		// Drive.RightWheelDrive.enable();
		Drive.LeftWheelDrive.reset();
		Drive.LeftWheelDrive.setSetpoint((m_ticks + Drive.LeftDriveEncoder.getRaw()));
		SmartDashboard.putNumber("LeftSetpoint", m_ticks + Drive.LeftDriveEncoder.getRaw());
		Drive.LeftWheelDrive.setAbsoluteTolerance(Drive.ABSOLUTEDRIVEPIDTOLERANCES);
		// Drive.LeftWheelDrive.enable();
		resetEncoderVelocity();
		
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		updateEncoderVelocity();
	}
	
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		SmartDashboard.putNumber("LEncoderVel", getLEncoderVelocity());
		SmartDashboard.putNumber("REncoderVel", getREncoderVelocity());
		SmartDashboard.putNumber("REncoderVal", Drive.RightDriveEncoder.getRaw());
		SmartDashboard.putNumber("LEncoderVal", Drive.LeftDriveEncoder.getRaw());
		return ((Math.abs(getLEncoderVelocity()) <= 0.001) && Math.abs(getREncoderVelocity()) <= 0.001
				&& Drive.LeftWheelDrive.onTarget() && Drive.RightWheelDrive.onTarget());
	}
	
	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		Drive.RightWheelDrive.disable();
		Drive.LeftWheelDrive.disable();
		Drive.RightWheelDrive.reset();
		Drive.LeftWheelDrive.reset();
		Drive.left.pidWrite(0);
		Drive.right.pidWrite(0);
		Drive.left.set(0);
		Drive.right.set(0);
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		this.end();
	}
}
