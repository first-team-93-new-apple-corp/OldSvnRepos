package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command toggles the bottom claw of the gear manipulator using the method
 * defined in GearManipulatorSub
 */
public class ToggleBottomClaw extends Command
{
	public ToggleBottomClaw()
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
		Robot.gearManipulator.toggleBottomClaw();
	}
	
	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		return true;
	}
}