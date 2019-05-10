/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nacteam93.robot2014;

import edu.wpi.first.wpilibj.command.CommandGroup;
import nacteam93.robot2014.commands.*;
import nacteam93.robot2014.subsystems.*;
/**
 *
 * @author NAC Controls
 */
public class PeriodicallyCheckHot extends CommandBase {
    
    public PeriodicallyCheckHot() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    boolean isHot = false;
    protected void execute() {
        if (true == CommandBase.hotDetection.isHot)
        {
            isHot = true;
        }       
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isHot;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
