/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc93.FinalRobot.commands;
import org.usfirst.frc93.FinalRobot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc93.FinalRobot.RobotMap;
import org.usfirst.frc93.FinalRobot.subsystems.Shooter;

/**
 *
 * @author NAC Controls
 */
public class setShooterTwoPoint extends Command {
    
    public setShooterTwoPoint() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Robot.shooter.setShootingState(Shooter.twoPointGoal);
        RobotMap.shooterSetting = "Two Point Goal";
    }
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
       if(Robot.shooter.getMainSpeedSetting() == Shooter.shooterTwoPointMain
                &&Robot.shooter.getGuideSpeedSetting() == Shooter.shooterTwoPointGuide) 
       {
           return true;
       }
       return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
