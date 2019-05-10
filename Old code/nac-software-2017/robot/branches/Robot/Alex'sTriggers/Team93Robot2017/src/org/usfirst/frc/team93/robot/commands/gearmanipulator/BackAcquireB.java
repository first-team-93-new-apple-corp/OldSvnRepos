package org.usfirst.frc.team93.robot.commands.gearmanipulator;

import org.usfirst.frc.team93.robot.subsystems.GearManipulator.GearLocation;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This command group closes the back claw, stops the back intake, and moves the
 * gear manipulator's carriage to the peg setpoint.
 * 
 * @author NAC Controls
 *
 */
public class BackAcquireB extends CommandGroup
{
	/**
	 * This command group closes the back claw, stops the back intake, and moves
	 * the gear manipulator's carriage to the peg setpoint.
	 */
	public BackAcquireB()
	{
		addSequential(new CloseBackClaw());
		addSequential(new SetBottomIntakeMotors(0));
		addSequential(new MoveGear(GearLocation.PEG));
	}
}
