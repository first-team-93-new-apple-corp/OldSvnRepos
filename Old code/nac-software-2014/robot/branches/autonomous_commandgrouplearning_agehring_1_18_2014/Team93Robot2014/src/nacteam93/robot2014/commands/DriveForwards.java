/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nacteam93.robot2014.commands;

import nacteam93.robot2014.RobotMap;

/**
 *
 * @author Controls
 */
public class DriveForwards extends CommandBase {
    
    public DriveForwards(int setpoint) {         //This not only sets the number of rotations for all of the motors, in encoder ticks, but the number is set 
        RobotMap.PIDOne.setSetpoint(setpoint);   //when he command is called on
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        RobotMap.PIDOne.enable();  //Enables PIDController
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        //RobotMap.PIDOne.notify();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return RobotMap.PIDOne.onTarget(); //Checks if the PIDController is where it needs to be
    }

    // Called once after isFinished returns true
    protected void end() {
        RobotMap.PIDOne.disable();         //Disables PIDController
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
