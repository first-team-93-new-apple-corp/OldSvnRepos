package org.usfirst.frc.team93.robot.commands.gearmanipulator;

import org.usfirst.frc.team93.robot.subsystems.GearManipulator.GearLocation;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ReleaseGearA extends CommandGroup
{
	
	public ReleaseGearA()
	{
		addSequential(new MoveGear(GearLocation.PEG));
		addSequential(new SetBottomIntakeMotors(0));
		addSequential(new SetTopIntakeMotors(0));
	}
}
