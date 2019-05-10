package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drives forwards for a set number or encoder ticks given in the initializer
 */
public class DriveEncoderTicksMaxVelocity extends PIDDriveVelocity
{
	
	double m_ticks;
	double m_time = 0;
	private Timer m_timer = new Timer();
	
	//yeet
	
	/**
	 * 
	 * @param ticks
	 *            The number of ticks that the encoders need to travel
	 */
	public DriveEncoderTicksMaxVelocity(double ticks)
	{
		m_ticks = ticks;
		requires(Robot.drive);
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}
	
	/**
	 * used as a failsafe if you never reach the required encoder ticks that it ends
	 * the PID, or it ends if it is on target and going slowly enough
	 * 
	 * @param ticks
	 *            The ticks at which it will end
	 * @param time
	 *            the time at which it will end
	 */
	public DriveEncoderTicksMaxVelocity(double ticks, double time)
	{
		requires(Robot.drive);
		m_ticks = ticks;
		m_time = time;
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		Drive.RightWheelDrive.reset();
		double rightSetpoint = Drive.RightDriveEncoder.getRaw() - m_ticks;
		SmartDashboard.putNumber("RightSetpoint", rightSetpoint);
		Drive.RightWheelDrive.setSetpoint(rightSetpoint);
		Drive.RightWheelDrive.setAbsoluteTolerance(Drive.ABSOLUTEDRIVEPIDTOLERANCES);
		Drive.RightWheelDrive.enable();
		Drive.LeftWheelDrive.reset();
		double leftSetpoint = m_ticks + Drive.LeftDriveEncoder.getRaw();
		Drive.LeftWheelDrive.setSetpoint((leftSetpoint));
		SmartDashboard.putNumber("LeftSetpoint", leftSetpoint);
		Drive.LeftWheelDrive.setAbsoluteTolerance(Drive.ABSOLUTEDRIVEPIDTOLERANCES);
		Drive.LeftWheelDrive.enable();
		resetEncoderVelocity();
		
		m_timer.reset();
		m_timer.start();
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
		
		return ((Drive.LeftWheelDrive.onTarget() && Drive.RightWheelDrive.onTarget() && getLEncoderVelocity() < 0.1
				&& getREncoderVelocity() < 0.1) || (m_time != 0 && m_timer.get() >= m_time));
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
