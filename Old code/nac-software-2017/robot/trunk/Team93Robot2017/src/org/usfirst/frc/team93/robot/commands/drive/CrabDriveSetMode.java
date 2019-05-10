package org.usfirst.frc.team93.robot.commands.drive;

import org.usfirst.frc.team93.robot.subsystems.Drive;
import org.usfirst.frc.team93.robot.utilities.crabdrive.CrabDriveCoordinator.CrabDriveMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Sets the crab drive mode, RobotCentric or Field Centric.
 */
public class CrabDriveSetMode extends Command
{
	CrabDriveMode m_mode;
	
	public CrabDriveSetMode(CrabDriveMode mode)
	{
		super();
		m_mode = mode;
	}
	
	// Called once when the command executes
	@Override
	protected void initialize()
	{
		// set the mode to the requested mode (one button requests
		// field-centric and another button requests robot-centric)
		Drive.CRAB_DRIVE_COORDINATOR.setMode(m_mode);
	}
	
	@Override
	protected boolean isFinished()
	{
		// This command functions as an instant command, so we want it to end as
		// soon as possible.
		return true;
	}
	
}
