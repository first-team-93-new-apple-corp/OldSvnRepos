package org.usfirst.frc.team93.robot.commands.gearmanipulator;

import org.usfirst.frc.team93.robot.subsystems.GearManipulator;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator.GearLocation;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This command is used to mvoe the gear manipulator to the front top setpoint,
 * open the front claw, and start the top intake motor. This is useful for
 * getting a gear from the player station.
 */
public class WallAcquireA extends CommandGroup
{
	// we now use this command for the setpoint to move the gear manipulator to
	// the top
	public WallAcquireA()
	{
		addSequential(new MoveGearSafe(GearLocation.CHUTE));
		addSequential(new SetTopIntakeMotors(GearManipulator.intakeMotorValue));
		addSequential(new OpenFrontClaw());
	}
}
