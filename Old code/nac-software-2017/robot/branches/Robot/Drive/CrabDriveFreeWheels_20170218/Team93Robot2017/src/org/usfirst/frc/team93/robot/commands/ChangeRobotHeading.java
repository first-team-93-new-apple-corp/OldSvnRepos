package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ChangeRobotHeading extends Command
{
    double m_robotHeading;
	/*
	 * This command turns the robot to be facing a certain heading
	 * 
	 * @param robotHeading
	 * 		The desired heading in degrees (0 is straight forward, 180 is backwards, etc)
	 */
    public ChangeRobotHeading(double robotHeading)
    {
        requires(Robot.drive);
        m_robotHeading = robotHeading;
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
    	Drive.CRAB_DRIVE_COORDINATOR.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
    	Drive.CRAB_DRIVE_COORDINATOR.request(0.0, 0.0, m_robotHeading);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
    	//if the PID Controller for the yaw detects that we are on target
        return Drive.CRAB_DRIVE_COORDINATOR.yawIsOnTarget();
    }

    // Called once after isFinished returns true
    protected void end()
    {
    	//disable our pid controller and stop all motors when we're done
    	Drive.CRAB_DRIVE_COORDINATOR.disable();
    	Drive.setAllMotors(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted()
    {
    	this.end();
    }
}