/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nacteam93.robot2014.commands;

import nacteam93.robot2014.OI;
import nacteam93.robot2014.RobotMap;

/**
 *
 * @author Controls
 */
public class timedRetraction extends CommandBase {
    
    private int waitMillis;
    
    public timedRetraction(int waitMillis) {
        requires(CommandBase.shooter);
        this.waitMillis = waitMillis;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    private int millisPassed;
    // Called just before this Command runs the first time
    protected void initialize() {
        millisPassed = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        millisPassed += 20;
        RobotMap.shooterWinch.set(RobotMap.retractionDirection);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(OI.manualInterrupt.get()) {
            return true;
        }
        return (millisPassed >= waitMillis);
    }

    // Called once after isFinished returns true
    protected void end() {
        RobotMap.shooterWinch.set(0.0);
        System.out.println("timedRetraction finished");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        this.end();
    }
}
