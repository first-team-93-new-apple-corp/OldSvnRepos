package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc93.Team93Robot2015.RobotMap;
import org.usfirst.frc93.Team93Robot2015.subsystems.Grabber;

/**
 *
 */
public class UpsideDownTurn extends Command {

    public UpsideDownTurn() {
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
        if ((RobotMap.rightHandPOT.getVoltage() <= 2.5
                || RobotMap.rightHandPOT.getVoltage() > 0 || RobotMap.rightHandPOT
                .getVoltage() >= 4.9) && Grabber.grabbing == false) {
            RobotMap.rightHandMotor.set(-.1);
        }
        else if ((RobotMap.rightHandPOT.getVoltage() >= 3.7)
                && (Grabber.grabbing == true)) {
            RobotMap.rightHandMotor.set(.1);
        }
        else {
            RobotMap.rightHandMotor.set(0);
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
