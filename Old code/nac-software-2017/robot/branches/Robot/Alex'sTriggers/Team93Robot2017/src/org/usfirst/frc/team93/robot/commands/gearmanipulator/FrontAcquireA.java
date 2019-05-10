package org.usfirst.frc.team93.robot.commands.gearmanipulator;

import org.usfirst.frc.team93.robot.subsystems.GearManipulator;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator.GearLocation;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This command is used to move the gear manipulator to the bottom middle
 * position on the track. It also opens the front claw and starts the front
 * intake motor so that we can easily pick up a gear that is in front of us.
 */
public class FrontAcquireA extends CommandGroup
{
	// @codereview josh hawbaker (I don't know the code review format) - just
	// wanted to say that this name is sub par. It's very hard for somebody to
	// understand these macro commands. Changed MoveGear to use MoveGearSafe
	public FrontAcquireA()
	{
		addSequential(new MoveGearSafe(GearLocation.BOTTOM_FRONT));
		addSequential(new OpenFrontClaw());
		addSequential(new SetTopIntakeMotors(GearManipulator.intakeMotorValue));
	}
}
