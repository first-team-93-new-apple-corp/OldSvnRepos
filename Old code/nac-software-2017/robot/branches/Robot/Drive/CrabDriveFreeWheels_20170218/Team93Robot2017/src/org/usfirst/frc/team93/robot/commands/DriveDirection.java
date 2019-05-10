package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;
import org.usfirst.frc.team93.robot.utilities.Team93PIDController;

import edu.wpi.first.wpilibj.command.Command;

public class DriveDirection extends Command
{
	public static enum DirectionToDrive
	{
		STRAIGHT, LEFT, RIGHT;
	}

	private double m_driveDistance;
	private double m_maxError;
	private DirectionToDrive m_direction;
	private Team93PIDController m_drivePID;
	private double time;

	/*
	 * This command makes the robot drive forward a certain distance.
	 * 
	 * @param driveDistance How far to drive (in motor encoder ticks - One tick
	 * = 0.5105 inches)
	 * 
	 * @param maxError The distance tolerance for the robot to miss the setpoint
	 * by (also in ticks)
	 * 
	 * @param direction the requested direction for the crab drive wheels to be
	 * facing (STRAIGHT, LEFT, OR RIGHT)
	 */
	public DriveDirection(double driveDistance, double maxError, DirectionToDrive direction)
	{
		m_driveDistance = driveDistance;
		m_maxError = maxError;
		m_direction = direction;
		requires(Robot.drive);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		time = 0;
		// reset and then enable everything we're using to control our drive
		Drive.LEFT_DRIVE_ENCODER.reset();
		Drive.RIGHT_DRIVE_ENCODER.reset();

		if (m_direction == DirectionToDrive.STRAIGHT)
		{
			m_drivePID = Drive.DRIVE_STRAIGHT_SPEED_CONTROL;
		}
		if (m_direction == DirectionToDrive.LEFT)
		{
			m_drivePID = Drive.DRIVE_LEFT_SPEED_CONTROL;
		}
		if (m_direction == DirectionToDrive.RIGHT)
		{
			m_drivePID = Drive.DRIVE_RIGHT_SPEED_CONTROL;
		}
		m_drivePID.reset();
		m_drivePID.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		m_drivePID.setSetpoint(m_driveDistance);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		boolean returnValue = false;
		double currentError = m_drivePID.getError();
		// if our current error is less than our tolerance
		if (currentError <= m_maxError)
		{
			// I thought this was easier than making a timer thread
			// this double functions as a timer to ensure we are in tolerance
			// for a half second (we don't just fly by the setpoint)
			time += 0.02;
			// if we are within tolerance for half a second
			if (time >= 0.5)
			{
				returnValue = true;
			}
		}
		else
		{
			// if we aren't in tolerance, reset the tolerance timer
			time = 0;
			returnValue = false;
		}
		return returnValue;
	}

	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		// disable our PIDControllers and then stop all motors
		m_drivePID.disable();
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