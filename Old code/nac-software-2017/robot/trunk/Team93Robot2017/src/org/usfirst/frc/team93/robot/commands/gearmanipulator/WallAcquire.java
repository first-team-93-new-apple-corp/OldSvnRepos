package org.usfirst.frc.team93.robot.commands.gearmanipulator;

import org.usfirst.frc.team93.robot.subsystems.GearManipulator;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator.GearLocation;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This command is used to move the gear manipulator to the front top setpoint,
 * open the front claw, and start the top intake motor. This is useful for
 * getting a gear from the player station.
 */
public class WallAcquire extends CommandGroup
{
	/**
	 * @codereview josh.hawbaker 3.20.17 Refactored removing the "A" in
	 * "WallAcquireA" since we don't use two-step macros anymore.
	 */
	
	/**
	 * Move the gear manipulator to the top setpoint, open top claw, and start
	 * the top intake motor.
	 */
	public WallAcquire()
	{
		addSequential(new MoveGearSafe(GearLocation.CHUTE));
		addSequential(new SetTopIntakeMotor(GearManipulator.intakeMotorValue));
		addSequential(new OpenFrontClaw());
	}
}
