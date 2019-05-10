package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc93.Team93Robot2015.OI;
import org.usfirst.frc93.Team93Robot2015.subsystems.Elevator;

/**
 * This command detects when the elevator is slipping and sends the data to
 * SmartDashboard.
 */
public class DetectElevatorSlip extends Command {

    public DetectElevatorSlip() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    double currentEncoderValue = 0.0;
    double lastEncoderValue = 0.0;

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

        // Has the operator commanded the elevator to rise or hold?
        boolean elevatorHoldCommanded = (OI.getOperatorLY(0.05) >= 0.0);

        // Gets the current encoder value.
        currentEncoderValue = Elevator.getEncoder();

        // Is the elevator descending?
        boolean elevatorDescending = (currentEncoderValue < lastEncoderValue);

        // Gets the encoder value to be used next cycle.
        lastEncoderValue = Elevator.getEncoder();

        // If the operator has told the elevator to hold or rise, but the
        // elevator is descending, the elevator is slipping.
        boolean elevatorSlipping = ((elevatorHoldCommanded) && (elevatorDescending));

        // Print out the elevatorSlipping boolean to SmartDashboard.
        SmartDashboard.putBoolean("Elevator Slipping : ", elevatorSlipping);

        // SmartDashboard.putNumber("Camera PID Input",
        // RobotMap.visionProcessorPIDSource.value);
        // SmartDashboard.putNumber("Center Of White",
        // RobotMap.visionProcessorPIDSource.centerOfWhite);
        // SmartDashboard.putNumber("Target Row",
        // RobotMap.visionProcessorPIDSource.imageHeight / 2.0);
        // SmartDashboard.putNumber("Average Pxl Intensity",
        // RobotMap.visionProcessorPIDSource.getAveragePxlIntensity(10));
        // SmartDashboard.putNumber("Maximum Pxl Intensity",
        // RobotMap.visionProcessorPIDSource.getMaxPxlIntensity(10));
        // SmartDashboard.putNumber("Minimum Pxl Intensity",
        // RobotMap.visionProcessorPIDSource.getMinPxlIntensity(10));
        // SmartDashboard.putNumber("White Center",
        // RobotMap.visionProcessorPIDSource.getWhiteCenter());

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