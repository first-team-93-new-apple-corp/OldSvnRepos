package org.usfirst.frc.team93.robot.commands.gearmanipulator;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * This command has both the front and back claw go to the position that they
 * are not currently at
 */
public class ToggleBothClaws extends InstantCommand
{
	
	public ToggleBothClaws()
	{
		super();
		requires(Robot.gearManipulator);
	}
	
	// Called once when the command executes
	@Override
	protected void initialize()
	{
		if (GearManipulator.frontClawOpen != GearManipulator.backClawOpen)
		{
			GearManipulator.closeClaws();
		}
		GearManipulator.toggleGrabber();
	}
	
}
