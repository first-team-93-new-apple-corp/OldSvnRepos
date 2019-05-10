package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.subsystems.Drive;
import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class WheelSpinner extends Command {

    public WheelSpinner() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Drive.CRAB_DRIVE_DIRECTION_LEFT_FRONT.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Drive.CRAB_DRIVE_DIRECTION_LEFT_FRONT.set(-OI.movement.direction().get());
    	System.out.println("SOURCE: " + Drive.CRAB_DRIVE_DIRECTION_LEFT_FRONT.m_source.pidGet());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
