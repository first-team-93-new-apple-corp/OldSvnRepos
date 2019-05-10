package org.usfirst.frc.team93.robot.commands;

import org.usficrst.frc.team93.robot.subsystems.Cameras;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CameraOneSetButton extends Command
{

	boolean isFinished;

	public CameraOneSetButton()
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize()
	{
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute()
	{
		CameraServer.getInstance().startAutomaticCapture(Cameras.cam1);
		isFinished = true;
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished()
	{
		if (isFinished == true)
		// if execute sets variable isFinished to true command is finished
		{
			return true;
		}
		return false;
	}

	// Called once after isFinished returns true
	protected void end()
	{
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted()
	{
	}
}
