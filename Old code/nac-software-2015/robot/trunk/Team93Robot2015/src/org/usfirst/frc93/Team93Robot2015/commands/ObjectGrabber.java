package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc93.Team93Robot2015.Robot;

/**
 * This command sets the claw to the opposite state that it is currently in.
 * 
 * @author Admin93
 */
public class ObjectGrabber extends Command {

    public ObjectGrabber() {
        // Use requires() here to declare subsystem dependencies;
        requires(Robot.grabber);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (Robot.grabber.isGrabbing()) {
            Robot.grabber.openClaw();
        }
        else {
            Robot.grabber.closeClaw();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        this.end();
    }
}
