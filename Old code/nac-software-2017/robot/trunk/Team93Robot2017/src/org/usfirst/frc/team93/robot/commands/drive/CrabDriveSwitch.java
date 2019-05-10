package org.usfirst.frc.team93.robot.commands.drive;

import org.usfirst.frc.team93.robot.subsystems.Drive;
import org.usfirst.frc.team93.robot.utilities.crabdrive.CrabDriveCoordinator;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This class is used to change which end of the robot is considered the front,
 * which allows us to change direction instantly.
 */
public class CrabDriveSwitch extends Command
{
	
	public CrabDriveSwitch()
	{
		super();
		// We don't want this class to require anything because if it required
		// drive, we wouldn't be able to instantly reverse.
	}
	
	// Called once when the command executes
	@Override
	protected void initialize()
	{
		// switch the robot from forward facing to backward facing or vice versa
		if (Drive.CRAB_DRIVE_COORDINATOR.getMode() == CrabDriveCoordinator.CrabDriveMode.RobotCentric)
		{
			Drive.toggleOrientation();
		}
	}
	
	@Override
	protected boolean isFinished()
	{
		// This command functions as an instant command, so it ends ASAP.
		return true;
	}
	
}
