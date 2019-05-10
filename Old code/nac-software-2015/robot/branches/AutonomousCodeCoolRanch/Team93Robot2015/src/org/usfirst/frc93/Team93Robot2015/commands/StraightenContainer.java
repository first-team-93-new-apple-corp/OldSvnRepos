package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc93.Team93Robot2015.RobotMap;
import org.usfirst.frc93.Team93Robot2015.subsystems.Grabber;

/**
 * @codereview ColbyMcKinley: In this area you should write a brief description
 *             of the command, within the comments.
 */
public class StraightenContainer extends Command {

    /**
     * @codereview ColbyMcKinley are these doubles constants? I believe that
     *             they are variables and should not be capitalized. If they are
     *             variables, follow the pattern of m_variableName
     */
    double MOTOR_BRAKE = 0;
    double ZERO_DEGREES = 0;
    double NINETY_DEGREES_BACK = 3.75;
    double TURN_SPEED = .1;
    double TOP_THRESHOLD = .5;
    double TWO_SEVENTY_THRESHOLD = 3.7;

    public StraightenContainer() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    /**
     * @codereview ColbyMcKinley: you should use local variables to store data.  
     * Such as creating a variable named m_rightHandPot = RobotMap.Right_HAND_POT.getVoltage().  
     * Then in the if statement do if(m_rightHandPot >= NINETY_DEGREES_BACK){
     * //execute here
     * }
     */
    protected void execute() {
        // Right Side.
        // If between 270 - 360 degrees, and not grabbing, turn back.
        if ((RobotMap.RIGHT_HAND_POT.getVoltage() >= NINETY_DEGREES_BACK || RobotMap.RIGHT_HAND_POT
                .getVoltage() <= TOP_THRESHOLD) && Grabber.grabbing == false) {
            RobotMap.RIGHT_HAND_MOTOR.set(-TURN_SPEED);
        }
        // If between 270 - 360, and grabbing, turn forward.
        else if ((RobotMap.RIGHT_HAND_POT.getVoltage() >= TWO_SEVENTY_THRESHOLD)
                && (Grabber.grabbing == true)) {
            RobotMap.RIGHT_HAND_MOTOR.set(TURN_SPEED);
        }
        // Otherwise, do nothing.
        else {
            RobotMap.RIGHT_HAND_MOTOR.set(MOTOR_BRAKE);
        }
        // Left Side.
        // If between 270 - 360 degrees, and not grabbing, turn back.
        if ((RobotMap.LEFT_HAND_POT.getVoltage() >= NINETY_DEGREES_BACK || RobotMap.LEFT_HAND_POT
                .getVoltage() <= TOP_THRESHOLD) && Grabber.grabbing == false) {
            RobotMap.LEFT_HAND_MOTOR.set(-TURN_SPEED);
        }
        // If between 270 - 360, and grabbing, turn forward.
        else if ((RobotMap.LEFT_HAND_POT.getVoltage() >= TWO_SEVENTY_THRESHOLD)
                && (Grabber.grabbing == true)) {
            RobotMap.LEFT_HAND_MOTOR.set(TURN_SPEED);
        }
        // Otherwise, do nothing.
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
