package org.usfirst.frc.team93.robot.commands.drive;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveAndRealignDuration extends CommandGroup
{
	double m_driveDistance;
	double m_maxError;
	
	/**
	 * This command group drives the robot straight forward for a distance and
	 * then turns the robot to be facing the direction that it was originally.
	 * 
	 * @param time The time to drive for.
	 * 
	 * @param direction the requested direction for the crab drive wheels to be
	 * facing
	 */
	public DriveAndRealignDuration(double time, double power, double direction)
	{
		// drive forward the desired distance
		addSequential(new DriveDirectionDuration(time, power, direction));
		// turn the robot to be facing the original direction
		addSequential(new ChangeRobotHeading(0));
	}
}
