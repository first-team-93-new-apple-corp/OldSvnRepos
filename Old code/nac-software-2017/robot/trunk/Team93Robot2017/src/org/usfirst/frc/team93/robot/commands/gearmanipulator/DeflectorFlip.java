package org.usfirst.frc.team93.robot.commands.gearmanipulator;

import org.usfirst.frc.team93.robot.commands.SleepCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This command group is used to quickly extend and retract our deflector. This
 * is useful for getting a gear from the player station off of our roof and into
 * our claw.
 */
public class DeflectorFlip extends CommandGroup
{
	/**
	 * @codereview josh.hawbaker 3.20.17 Added javadocs. Tried to clarify what
	 *             the deflector was.
	 */
	
	/**
	 * Quickly extend and retract the gear deflector (used to acquire gears from
	 * player station)
	 */
	public DeflectorFlip()
	{
		// Push out with the deflector
		addSequential(new DeflectorDown());
		addSequential(new SleepCommand(0.1));
		// Retract the deflector
		addSequential(new DeflectorUp());
	}
}
