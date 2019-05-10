package org.usfirst.frc.team93.robot.commands.drive;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This command gets input from the joysticks and sends it to the crab drive
 * coordinator.
 */
public class CrabDriveContinuous extends Command
{
	/**
	 * @codereview josh.hawbaker 3.13.17 Changed variables to initialize outside
	 *             of execute
	 */
	private final double yawDeadzone = 0.3;
	private double yawAxis;
	private double sign;
	private double magnitude;
	
	private Timer yawSpeedTimer;
	private double oldYaw;
	
	public CrabDriveContinuous()
	{
		// Reset these sensors at startup, NOT in initialize, since initialize
		// runs multiple times
		// and robot may not always be zeroed when it runs.
		Drive.resetSensors();
		OI.movement.direction().reset();
		
		yawSpeedTimer = new Timer();
		
		// requires drive
		requires(Robot.drive);
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		Drive.enableCrabDrive();
		Drive.setMaintainYaw(true);
		oldYaw = Drive.GYRO.get();
		yawSpeedTimer.start();
		yawSpeedTimer.reset();
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		// sends joystick request to coordinator
		magnitude = OI.movement.magnitude().get();
		
		double yawJoystickAxis = OI.yaw.get();
		// if we accidentally push the joystick a bit while trying to pivot,
		// then ignore
		if ((yawJoystickAxis > 0.5) && (magnitude < 0.4))
		{
			magnitude = 0;
		}
		
		// do math on joystick
		// make sure to NEVER have this multiplier over about 180.
		// otherwise it'll go the opposite way
		double multiplier = 60.0;
		// counterclockwise twist is negative, which isn't right
		yawAxis = yawJoystickAxis;
		sign = Math.signum(yawAxis);
		yawAxis -= Math.signum(yawAxis) * yawDeadzone;
		yawAxis = yawAxis / (1.0 - 0.3);
		yawAxis = Math.abs(Math.pow(yawAxis, 3)) * sign;
		
		// calculate yaw speed
		double yaw = Drive.GYRO.get();
		double yawSpeed = (yaw - oldYaw) / yawSpeedTimer.get();
		yawSpeedTimer.reset();
		oldYaw = yaw;
		// stores request angle
		double requestAngle = Drive.GYRO.get();
		
		// if we are commanded to turn
		if (yawAxis != 0)
		{
			// no longer need to maintain yaw
			Drive.setMaintainYaw(false);
			// calculate yaw that we need
			requestAngle = yaw + (yawAxis * multiplier);
		}
		// if we stopped turning but we're still moving (not maintain)
		else if (!Drive.getMaintainYaw())
		{
			if (yawSpeed > 2.0)
			{
				// if we're still moving from inertia
				// go with the flow and don't interfere
				requestAngle = Drive.GYRO.get();
			}
			else
			{
				// we stopped, so set this as the current default yaw
				Drive.setDefaultYaw(yaw);
				requestAngle = Drive.getDefaultYaw();
				Drive.setMaintainYaw(true);
			}
		}
		else
		{
			// hold default yaw
			requestAngle = Drive.getDefaultYaw();
		}
		if (!Drive.getMaintainYawRequest())
		{
			requestAngle = yaw + (yawAxis * multiplier);
		}
		
		Drive.CRAB_DRIVE_COORDINATOR.request(magnitude, OI.movement.direction().get(), requestAngle);
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