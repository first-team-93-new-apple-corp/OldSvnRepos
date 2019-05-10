package org.usfirst.frc.team93.robot.commands.gearmanipulator;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * This command has the front claw open if it is closed and close if it is open
 */
// I was going to name this ToggleFrontClaw, but I didn't want a duplicate name
public class ToggleTopClaw extends InstantCommand
{
	/**
	 * This command opens the top claw if it is closed and closes it if it is
	 * open
	 */
	public ToggleTopClaw()
	{
		super();
		requires(Robot.gearManipulator);
	}
	
	// Called once when the command executes
	@Override
	protected void initialize()
	{
		GearManipulator.toggleFrontClaw();
	}
	
}
