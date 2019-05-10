// RobotBuilder Version: 1.5
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc93.Team93Robot2015.OI;
import org.usfirst.frc93.Team93Robot2015.Robot;

/**
 * This command sets the motors of the grabber based off the joystick values
 * 
 * @author Admin93
 */
public class HandControlContinuous extends Command {

    double m_scalingFactor;

    /**
     *
     * @param scalingFactor
     *            scalingFactor is how fast we wish to speed up or slow down
     */
    public HandControlContinuous(double scalingFactor) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.grabber);

        m_scalingFactor = scalingFactor;
    }

    public HandControlContinuous() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.grabber);

        m_scalingFactor = 1.0;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    /**
     * This function set the motors of the grabber to a speed based off of the joystick values
     */
    protected void execute() {
        Robot.grabber.setHands(m_scalingFactor * OI.getOperatorRY(0.05));
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
        this.end();
    }
}
