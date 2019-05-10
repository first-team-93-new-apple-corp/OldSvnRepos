package org.usfirst.frc.team93.robot.commands.gearmanipulator;

import org.usfirst.frc.team93.robot.subsystems.GearManipulator.GearLocation;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class WallAcquireB extends CommandGroup
{
	// Was going to use this command to move the gear manipulator to the
	// middle point, but I don't think we need anything besides
	// MoveGearSafe(that also makes sure it's closed)
	public WallAcquireB()
	{
		addSequential(new CloseFrontClaw());
		addSequential(new SetTopIntakeMotors(0));
		addSequential(new MoveGear(GearLocation.PEG));
	}
}