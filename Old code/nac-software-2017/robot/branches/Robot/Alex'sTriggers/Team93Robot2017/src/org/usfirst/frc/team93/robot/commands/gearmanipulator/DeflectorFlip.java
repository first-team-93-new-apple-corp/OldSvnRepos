package org.usfirst.frc.team93.robot.commands.gearmanipulator;

import org.usfirst.frc.team93.robot.commands.SleepCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DeflectorFlip extends CommandGroup
{
	
	public DeflectorFlip()
	{
		addSequential(new DeflectorUp());
		addSequential(new SleepCommand(0.1));
		addSequential(new DeflectorDown());
	}
}
