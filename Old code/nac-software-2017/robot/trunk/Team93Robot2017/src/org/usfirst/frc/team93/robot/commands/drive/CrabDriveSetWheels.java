package org.usfirst.frc.team93.robot.commands.drive;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;
import org.usfirst.frc.team93.robot.utilities.crabdrive.CrabDriveCoordinator;
import org.usfirst.frc.team93.robot.utilities.crabdrive.CrabDriveCoordinator.Port;
import org.usfirst.frc.team93.robot.utilities.crabdrive.CrabDriveDirectionMotor;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This command is used to set the crab wheels direction, not the robot heading.
 * This allows us to drive in a direction that we aren't facing.
 */
public class CrabDriveSetWheels extends Command
{
	/**
	 * @codereview josh.hawbaker 3.14.17 Gave this class a javadoc and replaced
	 * copy-pasted comments from CrabDriveResetWheels
	 */
	public double m_direction;
	private boolean onTarget;
	
	// the timer that keeps track of when to finish
	private Timer finishTimer;
	
	public CrabDriveSetWheels(double direction)
	{
		// Use requires() here to declare subsystem dependencies
		m_direction = direction;
		finishTimer = new Timer();
		requires(Robot.drive);
	}
	
	// Called once when the command executes
	@Override
	protected void initialize()
	{
		// start a PID controller for every wheel to accurately turn
		for (Port p : CrabDriveCoordinator.Port.values())
		{
			CrabDriveDirectionMotor wheel = Drive.CRAB_DRIVE_COORDINATOR.m_directions.get(p);
			wheel.enable();
		}
		finishTimer.start();
		finishTimer.reset();
	}
	
	@Override
	protected void execute()
	{
		// Pass the drive frame a minimal speed, the desired direction, and a
		// straight forward heading. If the .001 were replaced by a 0, then
		// request would try to get our robot to turn tank-fashion.
		Drive.CRAB_DRIVE_COORDINATOR.request(0.001, m_direction, 0);
	}
	
	@Override
	protected boolean isFinished()
	{
		onTarget = true;
		for (Port p : CrabDriveCoordinator.Port.values())
		{
			CrabDriveDirectionMotor wheel = Drive.CRAB_DRIVE_COORDINATOR.m_directions.get(p);
			wheel.setTolerance(6.0);
			// if any wheels are off, we are not done
			if (!wheel.onTarget())
			{
				onTarget = false;
			}
		}
		if (!onTarget)
		{
			finishTimer.reset();
		}
		return finishTimer.get() > 0.5;
	}
	
	@Override
	protected void end()
	{
		// stop all of the PID controllers and motors
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