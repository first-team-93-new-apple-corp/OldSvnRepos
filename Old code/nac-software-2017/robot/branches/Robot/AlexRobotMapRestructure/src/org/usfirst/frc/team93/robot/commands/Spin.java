package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Spin extends Command {

    public Spin() {
        // Use requires() here to declare subsystem dependencies
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Drive.timer.start();
		Drive.DRIVE_RIGHT.set(-.2);
		Drive.DRIVE_LEFT.set(.2);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(Drive.timer.get() == 2000)
		{
			return true;
		}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Drive.DRIVE_ALL.set(0);
		Drive.timer.stop();
		Drive.timer.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
