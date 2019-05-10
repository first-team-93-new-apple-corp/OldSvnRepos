package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CanGrabber extends Command {
    boolean grabbing;

    //
    public CanGrabber(boolean state) {
        // grabbing = state;
        // // Use requires() here to declare subsystem dependencies
        // // eg. requires(chassis);
    }

    //
    // // Called just before this Command runs the first time
    // @Override
    @Override
    protected void initialize() {

    }

    //
    // // Called repeatedly when this Command is scheduled to run
    // @Override
    @Override
    protected void execute() {
        // if (grabbing == true) {
        // RobotMap.stupidSolenoid.set(DoubleSolenoid.Value.kForward);
        // }
        // else {
        // RobotMap.stupidSolenoid.set(DoubleSolenoid.Value.kReverse);
        // }
    }

    //
    // // Make this return true when this Command no longer needs to run
    // execute()
    // @Override
    @Override
    protected boolean isFinished() {
        return false;
    }

    //
    // // Called once after isFinished returns true
    // @Override
    @Override
    protected void end() {
    }

    //
    // // Called when another command which requires one or more of the same
    // // subsystems is scheduled to run
    // @Override
    @Override
    protected void interrupted() {
    }
}
