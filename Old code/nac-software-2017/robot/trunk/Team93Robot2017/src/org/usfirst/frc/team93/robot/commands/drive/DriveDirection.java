package org.usfirst.frc.team93.robot.commands.drive;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;
import org.usfirst.frc.team93.robot.utilities.crabdrive.CrabDriveCoordinator.CrabDriveMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This command makes the robot drive straight forward for a certain distance.
 */
public class DriveDirection extends Command
{
	
	private double m_driveDistance;
	private double m_maxError;
	private double m_direction;
	private double m_yaw;
	
	// the timer that helps calculate the robot's speed
	private Timer speedTimer;
	private double oldError;
	
	// The minimum speed to be considered moving. Otherwise, is considered
	// stopped
	private double stoppedThreshold = 2.0;
	// the amount of time to wait while stopped before ending command
	private double stoppedTime = 0.5;
	
	// the timer that keeps track of whether to end the command or not
	private Timer finishTimer;
	
	/**
	 * This command makes the robot drive forward a certain distance.
	 * 
	 * @param driveDistance
	 *            How far to drive (in motor encoder ticks - One tick = 0.5105
	 *            inches)
	 * 
	 * @param maxError
	 *            The distance tolerance for the robot to miss the setpoint by
	 *            (also in ticks)
	 * 
	 * @param direction
	 *            The requested direction for the crab drive wheels to be facing
	 *            (heading in degrees)
	 */
	public DriveDirection(double driveDistance, double maxError, double direction)
	{
		m_driveDistance = driveDistance;
		m_maxError = maxError;
		m_direction = direction;
		speedTimer = new Timer();
		m_yaw = 0;
		finishTimer = new Timer();
		requires(Robot.drive);
	}
	
	/**
	 * This command makes the robot drive forward a certain distance.
	 * 
	 * @param driveDistance
	 *            How far to drive (in motor encoder ticks - One tick = 0.5105
	 *            inches)
	 * 
	 * @param maxError
	 *            The distance tolerance for the robot to miss the setpoint by
	 *            (also in ticks)
	 * 
	 * @param direction
	 *            The requested direction for the crab drive wheels to be facing
	 *            (heading in degrees)
	 * 
	 * @param yaw
	 *            The requested heading direction for the body of the robot to
	 *            be facing (heading in degrees)
	 */
	public DriveDirection(double driveDistance, double maxError, double direction, double yaw)
	{
		this(driveDistance, maxError, direction);
		m_yaw = yaw;
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		// reset and then enable everything we're using to control our drive
		Drive.resetEncoders();
		Drive.CRAB_DRIVE_COORDINATOR.enable();
		Drive.DRIVE_DISTANCE_CONTROLLER.reset();
		Drive.DRIVE_DISTANCE_CONTROLLER.enable();
		speedTimer.start();
		speedTimer.reset();
		oldError = 0;
		
		finishTimer.start();
		finishTimer.reset();
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		Drive.CRAB_DRIVE_COORDINATOR.setMode(CrabDriveMode.FieldCentric);
		// set our motors to go the given distance
		Drive.DRIVE_DISTANCE_CONTROLLER.setSetpoint(m_driveDistance);
		// set our crab wheels to go straight forward
		Drive.CRAB_DRIVE_COORDINATOR.directionReceiver.set(m_direction);
		// set our heading to our current heading (does nothing)
		Drive.CRAB_DRIVE_COORDINATOR.yawReceiver.set(m_yaw);
	}
	
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		// calculate speed
		double error = Drive.DRIVE_DISTANCE_CONTROLLER.getError();
		double speed = (error - oldError) / speedTimer.get();
		oldError = error;
		speedTimer.reset();
		// if our current error is less than our tolerance
		if ((Math.abs(error) <= m_maxError) || (Math.abs(speed) < stoppedThreshold))
		{
			// if we are within tolerance for half a second, finish
			if (finishTimer.get() >= stoppedTime)
			{
				// end
				return true;
			}
		}
		else
		{
			// if we aren't in tolerance, reset the tolerance timer
			finishTimer.reset();
		}
		return false;
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