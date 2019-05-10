package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc93.Team93Robot2015.RobotMap;
import org.usfirst.frc93.Team93Robot2015.subsystems.Grabber;

/**
 * @codereview ColbyMcKinley: In this area you should write a brief description
 *             of the command, within the comments.
 */
public class ObjectGrabber extends Command {

    public ObjectGrabber() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        /**
         * @codereview EvansChen: Replace Grabber.grabbing with a getter.
         */
        if (Grabber.grabbing == true) {
            RobotMap.CLAW_SOLENOID.set(DoubleSolenoid.Value.kForward);
            Grabber.grabbing = false;
        }
        else {
            RobotMap.CLAW_SOLENOID.set(DoubleSolenoid.Value.kReverse);
            Grabber.grabbing = true;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
