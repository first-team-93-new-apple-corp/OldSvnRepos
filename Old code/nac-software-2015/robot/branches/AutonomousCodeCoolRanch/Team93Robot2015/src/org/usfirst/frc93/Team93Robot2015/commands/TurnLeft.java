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

import org.usfirst.frc93.Team93Robot2015.Robot;
import org.usfirst.frc93.Team93Robot2015.subsystems.Drive;

/**
 * @codereview ColbyMcKinley: In this area you should write a brief description
 *             of the command, within the comments.
 */
public class TurnLeft extends Command {

    private double m_driveDistance;
    private double m_maxError;
    private int m_errorTimer;

    public TurnLeft(double driveDistance, double maxError) {
        // Use requires() here to declare subsystem dependencies
        m_driveDistance = driveDistance;
        m_maxError = maxError;
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.drive);

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Drive.resetSensors();
        Drive.driveControl.reset();
        Drive.driveControl.enable();
        Drive.driveControl.setSetpoint(m_driveDistance);
        Drive.driveGroup.setGains(-1.0, 1.0);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        // PID Controller moves the motors
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        // returns true when error is low enough for 0.5s
        double currentError = Drive.driveControl.getError();
        if (currentError <= m_maxError) {
            m_errorTimer += 20;
            if (m_errorTimer >= 500) {
                return true;
            }
        }
        else {
            m_errorTimer = 0;
        }
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Drive.setAllMotors(0.0);
        Drive.driveControl.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        this.end();
    }
}