package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.commands.UpdateSmartDashboard;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This class contains a command that prints and writes to SmartDashboard.
 */
public class SmartDashboardWriter extends Subsystem
{
	
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	@Override
	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		Command update = new UpdateSmartDashboard();
		update.setRunWhenDisabled(true);
		setDefaultCommand(update);
	}
}
