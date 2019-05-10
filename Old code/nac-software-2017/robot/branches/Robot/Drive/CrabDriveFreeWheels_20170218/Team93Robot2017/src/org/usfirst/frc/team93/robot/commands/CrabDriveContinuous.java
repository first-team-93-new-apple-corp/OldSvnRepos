package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;
import org.usfirst.frc.team93.robot.subsystems.SPIEncoders;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command gets
 */
public class CrabDriveContinuous extends Command
{

	// the angle requested to the PID Controller.
	double requestAngle;

	public CrabDriveContinuous()
	{

		// Reset these sensors at startup, NOT in initialize, since initialize
		// runs multiple times
		// and robot may not always be zeroed when it runs.
		Drive.resetSensors();
		OI.movement.direction().reset();

		// requires drive
		requires(Robot.drive);

		requestAngle = 0.0;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		requestAngle = 0.0;
		Drive.CRAB_DRIVE_COORDINATOR.enable(); // comment me out for spaz
												// control
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		// update requested angle
		double multiplier = 100;
		double yawDeadzone = 0.3;
		double yawAxis = OI.yaw.get();
		yawAxis -= Math.signum(yawAxis) * yawDeadzone;
		yawAxis = yawAxis / (1.0 - 0.0);

		if (OI.yaw.get() != 0)
		{
			requestAngle = Drive.GYRO.get() + (yawAxis * multiplier);
		}
		else
		{
			requestAngle = Drive.GYRO.get();
		}

		// reset yaw on button press
		if (OI.driver.getRawButton(OI.resetButton))
		{
			requestAngle = 0.0;
		}

		if (SPIEncoders.ready())
		{
			// sends joystick request to coordinator
			Drive.CRAB_DRIVE_COORDINATOR.request(OI.movement.magnitude().get(), -OI.movement.direction().get(), requestAngle);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		// Is never finished unless interrupted
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end()
	{

		// disables PIDs
		Drive.CRAB_DRIVE_COORDINATOR.disable();
		// sets drive motors to 0.0 to stop
		Drive.setAllMotors(0.0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		this.end();
	}
}