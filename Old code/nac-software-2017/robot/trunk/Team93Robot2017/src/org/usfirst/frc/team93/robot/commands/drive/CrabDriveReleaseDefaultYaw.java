package org.usfirst.frc.team93.robot.commands.drive;

import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Sets the robot to be ABLE to change heading.
 */
public class CrabDriveReleaseDefaultYaw extends Command
{
	/**
	 * @codereview josh.hawbaker 4.4.17 Please don't copy/paste javadocs. Would
	 * it be a good idea to combine this and CrabDriveSetDeafaultYaw to one
	 * command that requires a boolean and yaw?
	 */
	
	/**
	 * Allow the robot to change heading
	 */
	public CrabDriveReleaseDefaultYaw()
	{
	}
	
	// Called once when the command executes
	@Override
	protected void initialize()
	{
		// disable the "autocorrect" for our heading
		Drive.setMaintainYawRequest(false);
	}
	
	@Override
	protected boolean isFinished()
	{
		// This command functions as an instant command, so we want it to end as
		// soon as possible.
		return true;
	}
	
}
