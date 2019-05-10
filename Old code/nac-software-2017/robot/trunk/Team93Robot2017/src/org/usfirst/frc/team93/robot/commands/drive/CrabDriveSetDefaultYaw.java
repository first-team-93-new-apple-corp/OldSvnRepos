package org.usfirst.frc.team93.robot.commands.drive;

import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Sets the robot to be UNABLE to change heading.
 */
public class CrabDriveSetDefaultYaw extends Command
{
	double m_yaw;
	
	/**
	 * Set the robot to maintain a desired heading
	 * 
	 * @param yaw the requested robot body heading to maintain
	 */
	public CrabDriveSetDefaultYaw(double yaw)
	{
		this.m_yaw = yaw;
	}
	
	// Called once when the command executes
	@Override
	protected void initialize()
	{
		// enable the "autocorrect" to maintain the requested value
		Drive.setDefaultYaw(m_yaw);
		Drive.setMaintainYaw(true);
		Drive.setMaintainYawRequest(true);
	}
	
	@Override
	protected boolean isFinished()
	{
		// This command functions as an instant command, so we want it to end as
		// soon as possible.
		return true;
	}
	
}
