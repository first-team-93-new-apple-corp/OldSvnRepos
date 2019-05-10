package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;
import org.usfirst.frc.team93.robot.utilities.crabdrive.CrabDriveCoordinator;
import org.usfirst.frc.team93.robot.utilities.crabdrive.CrabDriveCoordinator.Port;
import org.usfirst.frc.team93.robot.utilities.crabdrive.CrabDriveDirectionMotor;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CrabDriveResetWheels extends Command
{
	
	public CrabDriveResetWheels()
	{
		// Use requires() here to declare subsystem dependencies
		requires(Robot.drive);
	}
	
	// Called once when the command executes
	@Override
	protected void initialize()
	{
		// set all wheels to neutral position
		for (Port p : CrabDriveCoordinator.Port.values())
		{
			CrabDriveDirectionMotor wheel = Drive.CRAB_DRIVE_COORDINATOR.m_directions.get(p);
			wheel.enable();
			wheel.setAbsolute(0);
		}
	}
	
	@Override
	protected void execute()
	{
		// set all wheels to neutral position
		for (Port p : CrabDriveCoordinator.Port.values())
		{
			CrabDriveDirectionMotor wheel = Drive.CRAB_DRIVE_COORDINATOR.m_directions.get(p);
			wheel.setAbsolute(0);
		}
	}
	
	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		return OI.driver.getRawButton(6);
	}
	
}
