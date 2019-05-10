// RobotBuilder Version: 0.0.2
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in th future.
package org.usfirst.frc93.FinalRobot.commands;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc93.FinalRobot.Robot;
/**
 *
 */
public class  MechanicalLockCommand extends Command {
    public MechanicalLockCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
	
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }
    // Called just before this Command runs the first time
    protected void initialize() {
    }
    // Called repeatedly when this Command is scheduled to run
    
    boolean mechanicalLockFinished = false;
    
    protected void execute() {
    }
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (Robot.climber.mechanicalLock() == true){
            System.out.println("Mecanical Lock Complete!");
            return true;
        }
        return false;
    }
    // Called once after isFinished returns true
    protected void end() {
        mechanicalLockFinished = true;
    }
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
