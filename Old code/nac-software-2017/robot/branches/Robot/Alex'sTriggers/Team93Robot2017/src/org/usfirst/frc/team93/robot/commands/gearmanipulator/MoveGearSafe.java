package org.usfirst.frc.team93.robot.commands.gearmanipulator;

import org.usfirst.frc.team93.robot.commands.SleepCommand;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MoveGearSafe extends CommandGroup
{
	public static final double clawDelay = 0.5;
	
	public MoveGearSafe(double setpoint, double tolerance)
	{
		addSequential(new CloseClaws());
		addSequential(new SleepCommand(clawDelay));
		addSequential(new MoveGear(setpoint, tolerance));
	}
	
	public MoveGearSafe(double setpoint)
	{
		addSequential(new CloseClaws());
		addSequential(new SleepCommand(clawDelay));
		addSequential(new MoveGear(setpoint));
	}
	
	public MoveGearSafe(GearManipulator.GearLocation location)
	{
		addSequential(new CloseClaws());
		addSequential(new SleepCommand(clawDelay));
		addSequential(new MoveGear(location));
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
