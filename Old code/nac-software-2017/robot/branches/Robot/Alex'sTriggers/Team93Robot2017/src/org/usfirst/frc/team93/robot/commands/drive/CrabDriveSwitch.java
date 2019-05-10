package org.usfirst.frc.team93.robot.commands.drive;

import org.usfirst.frc.team93.robot.subsystems.Drive;
import org.usfirst.frc.team93.robot.utilities.crabdrive.CrabDriveCoordinator;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CrabDriveSwitch extends Command
{
	
	public CrabDriveSwitch()
	{
		super();
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
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
		// TODO Auto-generated method stub
		return true;
	}
	
}
