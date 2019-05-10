package org.usfirst.frc.team93.robot.commands.gearmanipulator;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * This command will close the bottom claw if it is open and open the bottom
 * claw if it is closed
 */
public class ToggleBottomClaw extends InstantCommand
{
	/**
	 * Set the bottom claw to close if it is opened and open if it is closed
	 */
	public ToggleBottomClaw()
	{
		super();
		requires(Robot.gearManipulator);
	}
	
	// Called once when the command executes
	@Override
	protected void initialize()
	{
		GearManipulator.toggleBackClaw();
	}
	
}
