/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nacteam93.robot2014.commands;

import nacteam93.robot2014.RobotMap;
import nacteam93.robot2014.subsystems.Drive;

/**
 *
 * @author Controls
 */
public class DriveForwards extends CommandBase {
    
    double maxError;
    
    //This not only sets the number of rotations for all of the motors, in encoder ticks, but the number is set
    //when the command is called on
    /**
     * @param setpoint An estimate of the number of encoder ticks that is required for a 45 degree turn
     * @param maxError The maximum number of ticks allowed to be off from the estimate. Should be relatively low with some exceptions.
     */
    public DriveForwards(int setpoint, double maxError) {
        requires(CommandBase.drive);
        RobotMap.driveMasterPID.setSetpoint(setpoint);
        
        this.maxError = maxError;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        //Enables PIDController
        System.out.println("DriveForwards Enabling");
        RobotMap.driveMasterPID.enable();
        RobotMap.driveLeftEncoder.reset();
        RobotMap.driveRightEncoder.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        //RobotMap.PIDOne.notify();
    }
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        double currentError = RobotMap.driveMasterPID.getError();
        //Checks if the PIDController is where it needs to be
        return (RobotMap.driveMasterPID.getError() <= maxError);
    }

    // Called once after isFinished returns true
    protected void end() {
        //Disables PIDController
        RobotMap.driveMasterPID.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        this.end();
    }
}
