/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nacteam93.robot2014.commands;

import nacteam93.robot2014.OI;
import nacteam93.robot2014.RobotMap;

/**
 *
 * @author NEW Apple Corps Controls
 */
public class RetractToSwitch extends CommandBase {
    
    private final static int Gate = 1;
    private final static int Switch = 2;
    private final static int Finished = 3;
    
    private static int state;
    
    public RetractToSwitch() {
        requires(CommandBase.shooter);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        state = 1;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        switch(state){
            case Gate:
                RobotMap.shooterWinch.set(RobotMap.retractionDirection);
                if (false == RobotMap.shooterRetractionIndex.get()) {
                    state = Switch;
                }
                else if (true == RobotMap.shooterRetrationSwitch.get()) {
                    state = Finished;
                }
                break;
            case Switch:
                RobotMap.shooterWinch.set(0.5f * RobotMap.retractionDirection);
                if (true == RobotMap.shooterRetrationSwitch.get()) {
                    state = Finished;
                }
                break;
            case Finished:
                RobotMap.shooterWinch.set(0.0);
                break;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(OI.manualInterrupt.get()) {
            return true;
        }
        return (state == Finished);
    }

    // Called once after isFinished returns true
    protected void end() {
        RobotMap.shooterWinch.set(0.0);
        shooter.setRetracted(true);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        this.end();
    }
}