package org.usfirst.frc.team93.robot.commands.gearmanipulator;

import org.usfirst.frc.team93.robot.commands.SleepCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ManualControlDown extends CommandGroup
{
	public ManualControlDown()
	{
		addSequential(new CloseFrontClaw());
		addSequential(new CloseBackClaw());
		addSequential(new SleepCommand(1.0));
		addSequential(new GearMoveDown());
	}
}
