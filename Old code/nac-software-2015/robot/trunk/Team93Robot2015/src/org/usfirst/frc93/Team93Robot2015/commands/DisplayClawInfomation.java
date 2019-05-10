package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc93.Team93Robot2015.RobotMap;

/**
 * This class is for decoding/tuning the claw. It displays information in the
 * smart dashboard.
 */
public class DisplayClawInfomation extends Command {

    public DisplayClawInfomation() {
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
        SmartDashboard.putNumber("Right Hand Angle = ",
                RobotMap.RIGHT_HAND_ENCODER.getAngle());
        SmartDashboard.putNumber("Right Hand Error = ",
                RobotMap.rightGrabberControl.getError());
        SmartDashboard.putNumber("Left Hand Angle = ",
                RobotMap.LEFT_HAND_ENCODER.getAngle());
        SmartDashboard.putNumber("Left Hand Error = ",
                RobotMap.leftGrabberControl.getError());
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
