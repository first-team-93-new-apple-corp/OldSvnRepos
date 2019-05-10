package org.usfirst.frc.team93.robot.commands.drive;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This command is used to turn the robot tank style.
 */
public class ChangeRobotHeading extends Command
{
	double m_robotHeading;
	double error;
	double speed;
	
	// the timer that keeps track of when to finish
	private Timer finishTimer;
	
	private double maxError = 2.0;
	
	// The minimum yaw speed to be considered moving. Otherwise, is considered
	// stopped
	private double stoppedThreshold = 0.8;
	// the amount of time to wait while stopped before ending command
	private double stoppedTime = 0.75;
	
	private Timer speedTimer;
	private double oldError = 0.0;
	
	/**
	 * This command turns the robot to be facing a certain heading
	 * 
	 * @param robotHeading The desired heading in degrees (0 is straight
	 * forward, 180 is backwards, -90 is left, etc)
	 */
	public ChangeRobotHeading(double robotHeading)
	{
		requires(Robot.drive);
		m_robotHeading = robotHeading;
		finishTimer = new Timer();
		speedTimer = new Timer();
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		Drive.enableCrabDrive();
		finishTimer.reset();
		finishTimer.start();
		speedTimer.reset();
		speedTimer.start();
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		// stop the robot and reset the crab wheel positions
		Drive.CRAB_DRIVE_COORDINATOR.request(0.0, 0.0, m_robotHeading);
	}
	
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		// calculate
		error = Drive.CRAB_DRIVE_COORDINATOR.yawError();
		speed = (error - oldError) / speedTimer.get();
		System.out.println(speed);
		oldError = error;
		speedTimer.reset();
		// if the PID Controller for the yaw detects that we are on target
		if ((Math.abs(error) < maxError) || (Math.abs(speed) < stoppedThreshold))
		{
			
		}
		else
		{
			finishTimer.reset();
		}
		// we make sure that we are on target for a half second
		return finishTimer.get() > stoppedTime;
	}
	
	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		// disable our pid controller and stop all motors when we're done
		Drive.disableCrabDrive();
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		this.end();
	}
}