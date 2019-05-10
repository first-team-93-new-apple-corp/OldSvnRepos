package org.usfirst.frc.team93.robot.commands.gearmanipulator;

import org.usfirst.frc.team93.robot.subsystems.GearManipulator.GearLocation;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class FrontAcquireB extends CommandGroup
{
	
	public FrontAcquireB()
	{
		addSequential(new CloseFrontClaw());
		addSequential(new SetTopIntakeMotors(0));
		addSequential(new MoveGear(GearLocation.PEG));
	}
}
