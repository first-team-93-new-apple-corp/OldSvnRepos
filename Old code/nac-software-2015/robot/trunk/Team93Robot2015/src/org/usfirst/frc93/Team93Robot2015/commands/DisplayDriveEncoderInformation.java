package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc93.Team93Robot2015.RobotMap;

/**
 * This is a class that displays the drive information on the smart dashboard.
 */
public class DisplayDriveEncoderInformation extends Command {

    public DisplayDriveEncoderInformation() {
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
        SmartDashboard.putNumber("Right Drive Encoder Value : ",
                RobotMap.RIGHT_MOTOR_ENCODER.pidGet());
        SmartDashboard.putNumber("Left Drive Encoder Value : ",
                RobotMap.LEFT_MOTOR_ENCODER.pidGet());
        SmartDashboard.putNumber(
                "Drive Left/Right Error : ",
                Math.abs(RobotMap.RIGHT_MOTOR_ENCODER.pidGet()
                        - RobotMap.LEFT_MOTOR_ENCODER.pidGet()));
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
