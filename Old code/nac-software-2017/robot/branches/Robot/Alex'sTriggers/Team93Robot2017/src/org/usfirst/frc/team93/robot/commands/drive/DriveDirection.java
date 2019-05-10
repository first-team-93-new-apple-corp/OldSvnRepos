package org.usfirst.frc.team93.robot.commands.drive;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

public class DriveDirection extends Command
{
	/*
	 * @codereview josh hawbaker We were no longer using the direction to drive
	 * enum that I created before, so I deleted it. The javadoc parameters for
	 * the command should have been edited when we changed it to ask for a
	 * double.
	 */
	
	private double m_driveDistance;
	private double m_maxError;
	private double m_direction;
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
	 * facing (heading in degrees)
	 */
	public DriveDirection(double driveDistance, double maxError, double direction)
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
		Drive.resetEncoders();
		Drive.CRAB_DRIVE_COORDINATOR.enable();
		Drive.DRIVE_DISTANCE_CONTROLLER.reset();
		Drive.DRIVE_DISTANCE_CONTROLLER.enable();
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		Drive.DRIVE_DISTANCE_CONTROLLER.setSetpoint(m_driveDistance);
		Drive.CRAB_DRIVE_COORDINATOR.directionReceiver.set(m_direction);
		Drive.CRAB_DRIVE_COORDINATOR.yawReceiver.set(Drive.GYRO.get());
	}
	
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		boolean returnValue = false;
		double currentError = Drive.DRIVE_DISTANCE_CONTROLLER.getError();
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
		Drive.DRIVE_DISTANCE_CONTROLLER.disable();
		Drive.CRAB_DRIVE_COORDINATOR.disable();
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