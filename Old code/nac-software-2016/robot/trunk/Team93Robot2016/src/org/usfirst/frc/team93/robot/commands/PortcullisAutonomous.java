package org.usfirst.frc.team93.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PortcullisAutonomous extends CommandGroup
{

	public PortcullisAutonomous()
	{
		addParallel(new SetFiringAngle(2, 4));
		addParallel(new SetManipulator(2.17, 0.01));
		addSequential(new DriveForward(700, 12, 1.0));
	}
}
