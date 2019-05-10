package org.usfirst.frc.team93.robot.commands.drive;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;
import org.usfirst.frc.team93.robot.utilities.crabdrive.CrabDriveCoordinator.CrabDriveMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Align mode: All wheels at 45 degree angles.
 */
public class CrabDriveAlign extends Command
{
	CrabDriveMode m_oldMode;
	final double yawDeadzone = 0.3;
	double multiplier = 20;
	double yawJoystickAxis;
	double yawAxis;
	double sign;
	
	double yaw;
	double requestAngle;
	
	public CrabDriveAlign()
	{
		// Use requires() here to declare subsystem dependencies
		requires(Robot.drive);
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		Drive.CRAB_DRIVE_COORDINATOR.enable();
		m_oldMode = Drive.CRAB_DRIVE_COORDINATOR.getMode();
		Drive.CRAB_DRIVE_COORDINATOR.setMode(CrabDriveMode.Alignment);
		Drive.CRAB_DRIVE_COORDINATOR.m_altMode = m_oldMode;
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		// joystick input to align mode.
		multiplier = linearMap(OI.slowSpeedSlider.get(), -1.0, 1.0, 15, 45);
		yawJoystickAxis = OI.yawUnlimited.get();
		yawAxis = yawJoystickAxis;
		sign = Math.signum(yawAxis);
		yawAxis -= Math.signum(yawAxis) * yawDeadzone;
		yawAxis = yawAxis / (1.0 - 0.3);
		yawAxis = Math.abs(Math.pow(yawAxis, 3)) * sign;
		
		yaw = Drive.GYRO.get();
		requestAngle = yaw + (yawAxis * multiplier);
		
		if (yaw != 0)
		{
			Drive.setMaintainYaw(false);
			Drive.setMaintainYawRequest(false);
		}
		
		Drive.CRAB_DRIVE_COORDINATOR.request(OI.movementUnlimited.magnitude().get() * linearMap(OI.slowSpeedSlider.get(), -1.0, 1.0, 0.2, 0.6), OI.movementUnlimited.direction().get(), requestAngle);
	}
	
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		// stop alignment mode when button is released
		return !OI.driver.getRawButton(OI.trigger);
	}
	
	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		Drive.CRAB_DRIVE_COORDINATOR.setMode(m_oldMode);
		Drive.CRAB_DRIVE_COORDINATOR.disable();
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		this.end();
	}
	
	/**
	 * Maps a value in range A to its value in range B
	 * 
	 * For example, mapping 4 in range 3 to 5 to the range 6 to 10 yields 8,
	 * since both are right in the middle.
	 * 
	 * @param value
	 * @param rangeAMinimum
	 * @param rangeAMaximum
	 * @param rangeBMinimum
	 * @param rangeBMaximum
	 * @return The value's equivalent in range B
	 */
	private double linearMap(double value, double rangeAMinimum, double rangeAMaximum, double rangeBMinimum, double rangeBMaximum)
	{
		
		return (((value - rangeAMinimum) * ((rangeBMaximum - rangeBMinimum) / (rangeAMaximum - rangeAMinimum))) + rangeBMinimum);
	}
}
