package org.usfirst.frc.team93.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DefenseSequence extends CommandGroup
{
	public DefenseSequence(double defenseSetpoint, double Time)
	{
		// addSequential(new SetManipulator(defenseSetpoint, defenseSetpoint /
		// 20, Time));
		// addParallel(new DriveForward(23, .23));
	}
}
