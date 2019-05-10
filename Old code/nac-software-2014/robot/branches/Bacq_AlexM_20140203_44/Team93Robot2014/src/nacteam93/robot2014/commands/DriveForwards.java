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
    
    public DriveForwards(int setpoint) {
        RobotMap.PIDOne.setSetpoint(setpoint);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        RobotMap.PIDOne.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        //RobotMap.PIDOne.notify();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return RobotMap.PIDOne.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
        RobotMap.PIDOne.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
