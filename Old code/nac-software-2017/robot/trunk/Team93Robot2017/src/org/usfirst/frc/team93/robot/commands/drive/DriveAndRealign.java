package org.usfirst.frc.team93.robot.commands.drive;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This command group drives the robot forward for a distance and then realigns
 * to its original heading.
 */
public class DriveAndRealign extends CommandGroup
{
	/**
	 * @codereview josh.hawbaker 3.14.17 I made the large comment into a
	 * javadoc. Silly past Josh, not knowing how to javadoc.
	 */
	double m_driveDistance;
	double m_maxError;
	
	/**
	 * This command group drives the robot straight forward for a distance and
	 * then turns the robot to be facing the direction that it was originally.
	 * 
	 * @param driveDistance The desired distance to drive forward in ticks (1
	 * tick = 0.5105 in)
	 * 
	 * @param maxError The maximum tolerance range for driving distance (also in
	 * ticks)
	 * 
	 * @param direction the requested direction for the crab drive wheels to be
	 * facing (heading in degrees)
	 */
	public DriveAndRealign(double driveDistance, double maxError, double direction)
	{
		// drive forward the desired distance
		addSequential(new DriveDirection(driveDistance, maxError, direction));
		// turn the robot to be facing the original direction
		addSequential(new ChangeRobotHeading(0));
	}
}
