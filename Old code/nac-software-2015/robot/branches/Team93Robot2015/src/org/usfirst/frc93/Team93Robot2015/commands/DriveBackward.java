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
import org.usfirst.frc93.Team93Robot2015.RobotMap;
import org.usfirst.frc93.Team93Robot2015.subsystems.Drive;

/**
 *
 */
public class DriveBackward extends Command {

    int m_driveDistance;

    private boolean finished = false;

    public void DriveBackward(int driveDistance) {
        // Use requires() here to declare subsystem dependencies
        m_driveDistance = driveDistance;
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.drive);

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        RobotMap.leftMotorEncoder.reset();
        RobotMap.rightMotorEncoder.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

        if (RobotMap.leftMotorEncoder.get() <= m_driveDistance) {
            Drive.setAllMotors(-1.0);
        }
        else {
            Drive.setAllMotors(0.0);
            finished = true;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return (finished);
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Drive.setAllMotors(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        this.end();
    }
}