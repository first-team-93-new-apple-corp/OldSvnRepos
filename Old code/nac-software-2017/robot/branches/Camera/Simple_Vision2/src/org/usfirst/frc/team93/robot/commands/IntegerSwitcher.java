package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.subsystems.Camera;

import edu.wpi.first.wpilibj.buttons.Button;

//import org.usfirst.frc.team93.robot.subsystems.Cameras;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntegerSwitcher extends Command
{

	boolean isFinished = false;
	//public static int button = -1;
	

	public IntegerSwitcher()
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		
	}

	// Called just before this Command runs the first time
	protected void initialize()
	{
		isFinished = false;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute()
	{

		isFinished = true;
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished()
	{
		return isFinished;
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
