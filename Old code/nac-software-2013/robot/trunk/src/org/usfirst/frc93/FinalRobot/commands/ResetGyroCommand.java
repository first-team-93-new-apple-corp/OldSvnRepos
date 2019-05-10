/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc93.FinalRobot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc93.FinalRobot.Robot;
import org.usfirst.frc93.FinalRobot.RobotMap;

/**
 *
 * @author Controls
 */
public class ResetGyroCommand extends Command {
    
    public ResetGyroCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    boolean latch = false;
    protected void execute() {
        System.out.println("Resetting");
        Robot.drive.resetGyro();
        latch = true;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return latch;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
