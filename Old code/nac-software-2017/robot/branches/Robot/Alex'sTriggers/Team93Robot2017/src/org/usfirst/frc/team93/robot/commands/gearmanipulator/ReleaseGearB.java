package org.usfirst.frc.team93.robot.commands.gearmanipulator;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This command is used to release a gear and push it onto the peg
 */
public class ReleaseGearB extends CommandGroup
{
	// josh - I added the deflector flip line here and combined the front and
	// back claw open lines to use the open claws command. This command will now
	// be used to release a gear with a button press.
	public ReleaseGearB()
	{
		// open both the top and bottom claws
		addSequential(new OpenClaws());
		// push out the gear with the deflector
		addSequential(new DeflectorFlip());
	}
}
