package org.usfirst.frc.team93.robot.commands.gearmanipulator;

import org.usfirst.frc.team93.robot.commands.SleepCommand;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This command group is a variation of MoveGear that verifies we close the
 * manipulator claws before moving the gear manipulator on the belt.
 */
public class MoveGearSafe extends CommandGroup
{
	/**
	 * @codereview josh.hawbaker 3.20.17 Added javadoc and lots of comments
	 */
	public static final double clawDelay = 0.5;
	
	public MoveGearSafe(double setpoint, double tolerance)
	{
		// stop the intake motors and close the claws
		addSequential(new SetIntakeMotors(0));
		addSequential(new CloseClaws());
		// wait to ensure the claws are closed
		addSequential(new SleepCommand(clawDelay));
		// move the manipulator to the desired setpoint with a tolerance
		addSequential(new MoveGear(setpoint, tolerance));
	}
	
	public MoveGearSafe(double setpoint)
	{
		// stop the intake motors and close the claws
		addSequential(new SetIntakeMotors(0));
		addSequential(new CloseClaws());
		// wait to ensure the claws are closed
		addSequential(new SleepCommand(clawDelay));
		// move the manipulator to the desired setpoint
		addSequential(new MoveGear(setpoint));
	}
	
	public MoveGearSafe(GearManipulator.GearLocation location)
	{
		// stop the intake motors and close the claws
		addSequential(new SetIntakeMotors(0));
		addSequential(new CloseClaws());
		// wait a half second
		addSequential(new SleepCommand(clawDelay));
		// move manipulator to your happy place
		addSequential(new MoveGear(location));
		
		// this switch case verifies that we open or close the proper claws
		switch (location)
		{
		case BOTTOM_BACK:
			addSequential(new CloseFrontClaw());
			addSequential(new OpenBackClaw());
			break;
		case BOTTOM_FRONT:
			addSequential(new OpenFrontClaw());
			addSequential(new CloseBackClaw());
			break;
		case PEG:
			addSequential(new CloseClaws());
			break;
		case CHUTE:
			addSequential(new OpenFrontClaw());
			addSequential(new CloseBackClaw());
			break;
		}
	}
}
