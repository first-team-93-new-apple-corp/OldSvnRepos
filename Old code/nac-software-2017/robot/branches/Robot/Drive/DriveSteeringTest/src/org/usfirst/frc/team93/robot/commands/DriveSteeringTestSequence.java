package org.usfirst.frc.team93.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * The main autonomous command to pickup and deliver the soda to the box.
 */
public class DriveSteeringTestSequence extends CommandGroup
{
	public DriveSteeringTestSequence()
	{

		for (int i = 0; i < 4; i++)
		// i is the motor index in RobotMap
		{
			addSequential(new DriveSteeringTest(i, true));
			addSequential(new DriveSteeringTest(i, false));
		}

		// end
	}
}
