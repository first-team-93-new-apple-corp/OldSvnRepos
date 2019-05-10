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
import org.usfirst.frc93.Team93Robot2015.subsystems.Drive;

/**
 * This command has the robot drive continuously based off of the joystick
 * values
 * 
 * @author Admin93
 */
public class DriveContinuousSingleCycle extends Command {

    double m_scalingFactor;

    /**
     * @codereview ColbyMcKinley: Explain what iteration is and does here.
     * @value iteration;
     */
    int iteration = 0;

    /**
     * @param scalingFactor
     *            scalingFactor is how fast we wish to speed up or slow down
     */
    public DriveContinuousSingleCycle(double scalingFactor) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drive);
        m_scalingFactor = scalingFactor;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    /**
     * This function reads the joystick values to determine how fast we should drive
     */
    protected void execute() {
        Drive.setLeftMotors(m_scalingFactor
                * OI.getDriverLY(0.05 * m_scalingFactor));
        Drive.setRightMotors(-m_scalingFactor
                * OI.getDriverRY(0.05 * m_scalingFactor));

        iteration++;

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return iteration >= 2;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        // This intentionally does nothing, to prevent jerkiness during
        // transition.
        iteration = 0;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Drive.setAllMotors(0.0);
        iteration = 0;
    }
}
