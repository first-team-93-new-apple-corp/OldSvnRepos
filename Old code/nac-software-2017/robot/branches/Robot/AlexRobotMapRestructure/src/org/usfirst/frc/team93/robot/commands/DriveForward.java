package org.usfirst.frc.team93.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;

/**
 *
 */
public class DriveForward extends Command {
	public DriveForward() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.exampleSubsystem);
		
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		
		Drive.timer.start();
		Drive.DRIVE_RIGHT.set(.2);
		Drive.DRIVE_LEFT.set(.2);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		if(Drive.timer.get() == 2000)
		{
			return true;
		}
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Drive.DRIVE_ALL.set(0);
		Drive.timer.stop();
		Drive.timer.reset();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
