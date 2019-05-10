package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc93.Team93Robot2015.RobotMap;
import org.usfirst.frc93.Team93Robot2015.subsystems.Grabber;

/**
 * @codereview ColbyMcKinley: In this area you should write a brief description
 *             of the command, within the comments.
 */
public class FlipContainer extends Command {

    /**
     * @codereview ColbyMcKinley are these doubles constants? I believe that
     *             they are variables and should not be capitalized. If they are
     *             variables, follow the pattern of m_variableName
     */
    double MOTOR_BRAKE = 0;
    double ZERO_DEGREES = 0;
    double NINETY_DEGREES = 1.25;
    double ONE_EIGHTY_DEGREES = 2.5;
    double TURN_SPEED = .1;
    double TOP_THRESHOLD = 4.9;
    double TWO_SEVENTY_THRESHOLD = 3.7;

    public FlipContainer() {
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
        // Right Side
        // If we are between 0 - 180 degrees, and we are not grabbing, rotate.
        if ((RobotMap.RIGHT_HAND_POT.getVoltage() <= ONE_EIGHTY_DEGREES
                || RobotMap.RIGHT_HAND_POT.getVoltage() > ZERO_DEGREES || RobotMap.RIGHT_HAND_POT
                .getVoltage() >= TOP_THRESHOLD) && Grabber.grabbing == false) {
            RobotMap.RIGHT_HAND_MOTOR.set(-TURN_SPEED);
        }
        // If we are more than 270 degrees and holding can, turn back to 0 (aka
        // 360).
        else if ((RobotMap.RIGHT_HAND_POT.getVoltage() >= TWO_SEVENTY_THRESHOLD)
                && (Grabber.grabbing == true)) {
            RobotMap.RIGHT_HAND_MOTOR.set(TURN_SPEED);
        }
        // If none of the above conditions are met, do nothing.
        else {
            RobotMap.RIGHT_HAND_MOTOR.set(MOTOR_BRAKE);
        }

        // Left Side.
        // If we are between 0 - 180 degrees, and we are not grabbing, rotate.
        if ((RobotMap.LEFT_HAND_POT.getVoltage() <= ONE_EIGHTY_DEGREES
                || RobotMap.LEFT_HAND_POT.getVoltage() > ZERO_DEGREES || RobotMap.LEFT_HAND_POT
                .getVoltage() >= TOP_THRESHOLD) && Grabber.grabbing == false) {
            RobotMap.LEFT_HAND_MOTOR.set(-TURN_SPEED);
        }
        // If we are more than 270 degrees and holding can, turn back to 0 (aka
        // 360).
        else if ((RobotMap.LEFT_HAND_POT.getVoltage() >= TWO_SEVENTY_THRESHOLD)
                && (Grabber.grabbing == true)) {
            RobotMap.LEFT_HAND_MOTOR.set(TURN_SPEED);
        }
        // If none of the above conditions are met, do nothing.
        else {
            RobotMap.LEFT_HAND_MOTOR.set(MOTOR_BRAKE);
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
