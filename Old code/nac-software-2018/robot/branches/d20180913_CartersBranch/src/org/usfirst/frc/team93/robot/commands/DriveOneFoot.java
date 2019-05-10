package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class DriveOneFoot extends PIDDriveVelocity
{
	int m_ticks;
	double m_time;
	private Timer m_timer = new Timer();
	
	public DriveOneFoot(int ticks)
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.drive);
		m_ticks = ticks;
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
	public DriveOneFoot(double ticks, double time)
	{
		requires(Robot.drive);
		m_ticks = (int) ticks;
		m_time = time;
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		
		Drive.RightOneFoot.reset();
		double rightSetpoint = Drive.RightDriveEncoder.getRaw() - m_ticks;
		Drive.RightOneFoot.setSetpoint(rightSetpoint);
		Drive.RightOneFoot.setAbsoluteTolerance(Drive.ONEFOOTPIDTOLERANCES);
		Drive.RightOneFoot.enable();
		Drive.LeftOneFoot.reset();
		double leftSetpoint = m_ticks + Drive.LeftDriveEncoder.getRaw();
		Drive.LeftOneFoot.setSetpoint((leftSetpoint));
		Drive.LeftOneFoot.setAbsoluteTolerance(Drive.ONEFOOTPIDTOLERANCES);
		Drive.LeftOneFoot.enable();
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
		return (Drive.LeftOneFoot.onTarget() && Drive.RightOneFoot.onTarget() && getLEncoderVelocity() < 0.1
				&& getREncoderVelocity() < 0.1 || (m_time != 0 && m_timer.get() >= m_time));
	}
	
	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		Drive.RightOneFoot.disable();
		Drive.LeftOneFoot.disable();
		Drive.RightOneFoot.reset();
		Drive.LeftOneFoot.reset();
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
