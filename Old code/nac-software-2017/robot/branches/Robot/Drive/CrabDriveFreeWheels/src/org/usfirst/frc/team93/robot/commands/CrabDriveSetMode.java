package org.usfirst.frc.team93.robot.commands;

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
		Drive.CRAB_DRIVE_COORDINATOR.setMode(m_mode);
	}
	
	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		return true;
	}
	
}
