package org.usfirst.frc.team93.robot.commands.drive;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;
import org.usfirst.frc.team93.robot.utilities.crabdrive.CrabDriveCoordinator;
import org.usfirst.frc.team93.robot.utilities.crabdrive.CrabDriveCoordinator.Port;
import org.usfirst.frc.team93.robot.utilities.crabdrive.CrabDriveDirectionMotor;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command sets the crab wheels to go back to their starting position
 * (straight forward, gears out).
 */
public class CrabDriveResetWheels extends Command
{
	/**
	 * @codereview josh.hawbaker 3.13.17 Added javadoc and a few comments. I
	 * don't think any of execute is necessary since it does the same this as
	 * initialize. This command requires drive, so it's not as if another
	 * command could change the wheel direction in the mean time.
	 */
	public CrabDriveResetWheels()
	{
		// Use requires() here to declare subsystem dependencies
		requires(Robot.drive);
	}
	
	// Called once when the command executes
	@Override
	protected void initialize()
	{
		// go through and do this for every wheel
		for (Port p : CrabDriveCoordinator.Port.values())
		{
			// enable a PID controller for the wheel and set its setpoint to
			// neutral position
			CrabDriveDirectionMotor wheel = Drive.CRAB_DRIVE_COORDINATOR.m_directions.get(p);
			wheel.enable();
			wheel.setAbsolute(0);
		}
	}
	
	@Override
	protected void execute()
	{
		// set all wheels to neutral position
		// this is here again for super security.
		for (Port p : CrabDriveCoordinator.Port.values())
		{
			CrabDriveDirectionMotor wheel = Drive.CRAB_DRIVE_COORDINATOR.m_directions.get(p);
			wheel.setAbsolute(0);
		}
	}
	
	@Override
	protected boolean isFinished()
	{
		// have the command end when the driver hits right bumper
		return OI.driver.getRawButton(6);
	}
	
	/**
	 * @codereview josh.hawbaker 3.13.17 Although we are enabling multiple PID
	 * controllers here and not disabling them when we're done, the same PIDs
	 * are supposedly used during continuous drive with a new setpoint. Evans
	 * assured me that they are disabled eventually.
	 */
}
