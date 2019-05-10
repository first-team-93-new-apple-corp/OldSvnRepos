package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveStraightForward extends CommandGroup
{
	double m_driveDistance;
	double m_maxError;
	double m_originalHeading;
	/*
	 * This command group drives the robot straight forward for a distance
	 * and then turns the robot to be facing the direction that it was originally.
	 * 
	 * @param driveDistance
	 * 		The desired distance to drive forward in ticks (1 tick = 0.5105 in)
	 * 
	 * @param maxError
	 * 		The maximum tolerance range for driving distance (also in ticks)
	 */
    public DriveStraightForward(double driveDistance, double maxError)
    {
    	//record the original heading
    	m_originalHeading = Drive.GYRO.get();
    	//drive forward the desired distance
    	addSequential(new DriveForward(driveDistance, maxError));
    	//turn the robot to be facing the original direction
    	addSequential(new ChangeRobotHeading(m_originalHeading));
    }
}
