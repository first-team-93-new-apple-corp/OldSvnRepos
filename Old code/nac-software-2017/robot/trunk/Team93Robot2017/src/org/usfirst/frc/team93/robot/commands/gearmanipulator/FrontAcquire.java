package org.usfirst.frc.team93.robot.commands.gearmanipulator;

import org.usfirst.frc.team93.robot.subsystems.GearManipulator;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator.GearLocation;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This command is used to move the gear manipulator to the bottom middle
 * position on the track. It also opens the front claw and starts the front
 * intake motor so that we can easily pick up a gear that is in front of us.
 */
public class FrontAcquire extends CommandGroup
{
	/**
	 * @codereview josh.hawbaker 3.20.17 Refactored by removing the "A" in
	 * "FrontAcquireA" since we aren't using the two-step macro command things.
	 * Also, the codereview below this made me smile. Silly past josh.
	 */
	// @codereview josh hawbaker (I don't know the code review format) - just
	// wanted to say that this name is sub par. It's very hard for somebody to
	// understand these macro commands. Changed MoveGear to use MoveGearSafe
	/**
	 * This command group moves the gear manipulator to the front and starts the
	 * intake motors.
	 */
	public FrontAcquire()
	{
		addSequential(new MoveGearSafe(GearLocation.BOTTOM_FRONT));
		addSequential(new OpenFrontClaw());
		addSequential(new SetTopIntakeMotor(GearManipulator.intakeMotorValue));
	}
}
