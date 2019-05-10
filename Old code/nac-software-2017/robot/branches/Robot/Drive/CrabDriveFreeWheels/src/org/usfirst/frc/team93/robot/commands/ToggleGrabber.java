package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command toggles both the top and bottom claws of the gear manipulator
 * simply by toggling both of them. It will reverse both of the claws (if top is
 * closed and bottom is open, top will open and bottom will close).
 */
public class ToggleGrabber extends Command
{
	
	public ToggleGrabber()
	{
		super();
		requires(Robot.gearManipulator);
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}
	
	// Called once when the command executes
	@Override
	protected void initialize()
	{
		Robot.gearManipulator.toggleGrabber();
	}
	
	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		return true;
	}
}