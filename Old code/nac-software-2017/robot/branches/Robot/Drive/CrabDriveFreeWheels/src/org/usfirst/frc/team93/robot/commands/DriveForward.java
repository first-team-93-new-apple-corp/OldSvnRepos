package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

public class DriveForward extends Command
{
	private double m_driveDistance;
	private double m_maxError;
	private double time;
	
	/*
	 * This command makes the robot drive forward a certain distance.
	 * 
	 * @param driveDistance
	 * 		How far to drive (in motor encoder ticks - One tick = 0.whatever inches)
	 * 
	 * @param maxError
	 * 		The distance tolerance for the robot to miss the setpoint by (also in ticks)
	 */
	public DriveForward(double driveDistance, double maxError)
	{
		m_driveDistance = driveDistance;
		m_maxError = maxError;
		requires(Robot.drive);
	}
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		time = 0;
		//reset and then enable everything we're using to control our drive
		Drive.LEFT_DRIVE_ENCODER.reset();
		Drive.RIGHT_DRIVE_ENCODER.reset();
		
		Drive.DRIVE_SPEED_CONTROL.reset();
		Drive.DRIVE_SPEED_CONTROL.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		Drive.DRIVE_SPEED_CONTROL.setSetpoint(m_driveDistance);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		boolean returnValue = false;
		double currentError = Drive.DRIVE_SPEED_CONTROL.getError();
		//if our current error is less than our tolerance
		if(currentError <= m_maxError)
		{
			//I thought this was easier than making a timer thread
			//this double functions as a timer to ensure we are in tolerance for
			//at least a second (we don't just fly by the setpoint)
			time += 0.02;
			//if we are within tolerance for a second
			if(time >= 1)
			{
				returnValue = true;
			}
		}
		else
		{
			//if we aren't in tolerance, reset the tolerance timer
			time = 0;
			returnValue = false;
		}
		return returnValue;
	}

	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		//disable our PIDControllers and then stop all motors
		Drive.DRIVE_SPEED_CONTROL.disable();
		Drive.setAllMotors(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		this.end();
	}
}