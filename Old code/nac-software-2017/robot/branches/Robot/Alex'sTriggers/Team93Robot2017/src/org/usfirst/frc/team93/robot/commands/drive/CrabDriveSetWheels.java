package org.usfirst.frc.team93.robot.commands.drive;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;
import org.usfirst.frc.team93.robot.utilities.crabdrive.CrabDriveCoordinator;
import org.usfirst.frc.team93.robot.utilities.crabdrive.CrabDriveCoordinator.Port;
import org.usfirst.frc.team93.robot.utilities.crabdrive.CrabDriveDirectionMotor;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CrabDriveSetWheels extends Command
{
	public double m_direction;
	
	public CrabDriveSetWheels(double direction)
	{
		// Use requires() here to declare subsystem dependencies
		m_direction = direction;
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
		}
	}
	
	@Override
	protected void execute()
	{
		Drive.CRAB_DRIVE_COORDINATOR.request(0.001, m_direction, 0);
	}
	
	@Override
	protected boolean isFinished()
	{
		boolean finished = true;
		for (Port p : CrabDriveCoordinator.Port.values())
		{
			CrabDriveDirectionMotor wheel = Drive.CRAB_DRIVE_COORDINATOR.m_directions.get(p);
			// if any wheels are off, we are not done
			if (!wheel.onTarget())
			{
				finished = false;
			}
		}
		return finished;
	}
	
	@Override
	protected void end()
	{
		// set all wheels to neutral position
		for (Port p : CrabDriveCoordinator.Port.values())
		{
			CrabDriveDirectionMotor wheel = Drive.CRAB_DRIVE_COORDINATOR.m_directions.get(p);
			wheel.disable();
		}
	}
	
	@Override
	protected void interrupted()
	{
		this.end();
	}
}