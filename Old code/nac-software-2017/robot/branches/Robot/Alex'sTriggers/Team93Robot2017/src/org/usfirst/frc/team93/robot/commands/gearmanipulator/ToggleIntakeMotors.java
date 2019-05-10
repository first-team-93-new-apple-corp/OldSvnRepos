package org.usfirst.frc.team93.robot.commands.gearmanipulator;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ToggleIntakeMotors extends InstantCommand
{
	
	public ToggleIntakeMotors()
	{
		super();
		requires(Robot.gearManipulator);
	}
	
	// Called once when the command executes
	@Override
	protected void initialize()
	{
		GearManipulator.toggleIntakeMotors();
	}
	
}
