/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc93.FinalRobot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc93.FinalRobot.Robot;

/**
 *
 * @author NAC Controls
 */
public class PullDownSliderCommand extends Command {
    int setpoint;
    public PullDownSliderCommand(int setpoint) {
        this.setpoint = setpoint;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }
    boolean finished = false;
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        finished = Robot.climber.pullDownSlider(setpoint);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
        System.out.println("Pull Down Slider Complete!");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
