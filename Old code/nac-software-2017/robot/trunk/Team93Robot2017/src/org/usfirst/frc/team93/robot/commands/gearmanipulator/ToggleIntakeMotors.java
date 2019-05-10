package org.usfirst.frc.team93.robot.commands.gearmanipulator;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * This command starts the intake motors if they are off and turns them off if
 * they are on. Used in OI
 */
public class ToggleIntakeMotors extends InstantCommand
{
	/**
	 * @codereview josh.hawbaker 3.20.17 Silly josh, you forgot to add a javadoc
	 * for this one. Also, this one is an instant command, but it still works? I
	 * thought we were having trouble with them.
	 */
	
	/**
	 * Start the intake motors if they're off, stop them if they're on.
	 */
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
