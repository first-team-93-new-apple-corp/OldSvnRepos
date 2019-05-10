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
import org.usfirst.frc93.Team93Robot2015.subsystems.Elevator;

/**
 * This raises or lowers the elevator based off of the joystick values.
 * 
 * @author Admin93
 *
 */
public class ElevatorControlContinuous extends Command {

    public ElevatorControlContinuous() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.elevator);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    /**
     * This function raises or lowers the elevator based of the Joystick values
     */
    protected void execute() {

        Elevator.setElevatorMotors(OI.getOperatorLY(0.05));

        /**
         * @codreview ColbyMcKinley: These comments should be unneeded since
         *            they should be in DisplayElevatorPosition.java using
         *            SmartDashboard.putNumber function.
         */
        // System.out.println("Elevator Encoder: "
        // + RobotMap.ELEVATOR_ENCODER.get());
        // System.out.println("Bot Switch: "
        // + RobotMap.ELEVATOR_LIMIT_SWITCH_BOTTOM.get());
        // System.out.println("Top Switch: "
        // + RobotMap.ELEVATOR_LIMIT_SWITCH_TOP.get());

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Elevator.stopElevatorMotors();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        this.end();
    }
}