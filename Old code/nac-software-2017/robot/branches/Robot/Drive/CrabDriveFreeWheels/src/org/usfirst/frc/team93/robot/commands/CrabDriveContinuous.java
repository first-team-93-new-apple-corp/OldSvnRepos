package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command gets input from the joysticks and sends it to the crab drive
 * coordinator.
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
		Drive.enableCrabDrive();
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		// do math on joystick
		double multiplier = 80;
		double yawDeadzone = 0.3;
		double yawAxis = OI.yaw.get();
		double sign = Math.signum(yawAxis);
		yawAxis -= Math.signum(yawAxis) * yawDeadzone;
		yawAxis = yawAxis / (1.0 - 0.3);
		yawAxis = Math.abs(Math.pow(yawAxis, 3)) * sign;
		// more math on joystick (this setpoint sweeping is bad form, I know)
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
		
		// sends joystick request to coordinator
		Drive.CRAB_DRIVE_COORDINATOR.request(OI.movement.magnitude().get(), -OI.movement.direction().get(), requestAngle);
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
		Drive.disableCrabDrive();
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		this.end();
	}
}