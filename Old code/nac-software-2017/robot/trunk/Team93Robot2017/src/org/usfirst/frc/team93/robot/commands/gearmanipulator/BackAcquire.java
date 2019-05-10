package org.usfirst.frc.team93.robot.commands.gearmanipulator;

import org.usfirst.frc.team93.robot.subsystems.GearManipulator;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator.GearLocation;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This command is used to move the gear manipulator to the bottom setpoint,
 * open the back claw, and start the back intake motors so that we can easily
 * pick up a gear that is behind us.
 * 
 * @author NAC Controls
 * 
 */
public class BackAcquire extends CommandGroup
{
	/**
	 * @codereview josh.hawbaker 3.20.17 Refactored name to get rid of the "A"
	 * in "BackAcquireA" since we aren't using the two-step macro command
	 * things.
	 */
	/**
	 * This command group is used to move the gear manipulator to the bottom
	 * setpoint and run the back intake.
	 */
	public BackAcquire()
	{
		addSequential(new MoveGearSafe(GearLocation.BOTTOM_BACK));
		addSequential(new OpenBackClaw());
		addSequential(new SetBottomIntakeMotor(GearManipulator.intakeMotorValue));
	}
}