package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.commands.UpdateSmartDashboard;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This class contains a command that prints and writes to SmartDashboard.
 */
public class SmartDashboardWriter extends Subsystem
{
	/**
	 * @codereview josh.hawbaker 3.21.17 I understand that a subsystem functions
	 * as a thread so that we can constantly run a command, but would it be
	 * better to just put UpdateSmartDashboard in TeleopPeriodic?
	 */
	
	/*
	 * This subsystem is used to run the UpdateSmartDashboard command repeatedly
	 * throughout teleop.
	 */
	
	@Override
	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		Command update = new UpdateSmartDashboard();
		update.setRunWhenDisabled(true);
		setDefaultCommand(update);
	}
}
