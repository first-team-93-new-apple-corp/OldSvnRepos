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
	/**
	 * If both claws aren't closed, close them. Otherwise, toggle both claws
	 */
	public ToggleBothClaws()
	{
		super();
		requires(Robot.gearManipulator);
	}
	
	// Called once when the command executes
	@Override
	protected void initialize()
	{
		// if both claws aren't closed
		if (!GearManipulator.checkAllClawsClosed())
		{
			/// close both claws
			GearManipulator.closeClaws();
		}
		else
		{
			// if both are closed, toggle both claws (this could in theory be
			// replaced by OpenClaws)
			GearManipulator.toggleGrabber();
		}
	}
	
}
