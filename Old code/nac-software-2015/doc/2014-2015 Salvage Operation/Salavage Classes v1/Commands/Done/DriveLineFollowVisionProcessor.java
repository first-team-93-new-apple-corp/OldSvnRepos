package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc93.Team93Robot2015.Robot;
import org.usfirst.frc93.Team93Robot2015.RobotMap;

/**
 * @author grant.keane
 */
public class DriveLineFollowVisionProcessor extends Command {

    double m_cameraLineDistanceSetpoint;
    double m_cameraLineDistanceError;

    public DriveLineFollowVisionProcessor(double cameraLineDistanceSetpoint,
            double cameraLineDistanceError) {
        m_cameraLineDistanceSetpoint = cameraLineDistanceSetpoint;
        m_cameraLineDistanceError = cameraLineDistanceError;
        requires(Robot.drive);
        RobotMap.visionProcessorLineFollowSteering.reset();
        RobotMap.visionProcessorLineFollowSteering.enable();
        RobotMap.visionProcessorLineFollowSteering
                .setSetpoint(m_cameraLineDistanceSetpoint);
        RobotMap.steerAndSpeed.setSteeringGain(0.5);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        double currentError = Math.abs(RobotMap.driveSpeedControl.getError());
        if (currentError <= m_cameraLineDistanceError) {
            return true;
        }

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
