package org.usfirst.frc.team93.robot.commands.drive;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ChangeRobotHeading extends Command
{
	double m_robotHeading;
	// josh - I'm using this timer because I don't know how to use the FRC one
	// and this is a quick fix
	long time = 0;
	
	/*
	 * This command turns the robot to be facing a certain heading
	 * 
	 * @param robotHeading The desired heading in degrees (0 is straight
	 * forward, 180 is backwards, etc)
	 */
	public ChangeRobotHeading(double robotHeading)
	{
		requires(Robot.drive);
		m_robotHeading = robotHeading;
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		Drive.enableCrabDrive();
		Drive.CRAB_DRIVE_COORDINATOR.yawSetTolerance(10);
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		Drive.CRAB_DRIVE_COORDINATOR.request(0.0, 0.0, m_robotHeading);
	}
	
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		// if the PID Controller for the yaw detects that we are on target
		if (Drive.CRAB_DRIVE_COORDINATOR.yawIsOnTarget())
		{
			time += .02;
		}
		else
		{
			time = 0;
		}
		// we make sure that we are on target for a half second
		return time > .5;
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