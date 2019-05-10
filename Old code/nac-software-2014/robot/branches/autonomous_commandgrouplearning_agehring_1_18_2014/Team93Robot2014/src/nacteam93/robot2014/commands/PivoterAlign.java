
package nacteam93.robot2014.commands;

import nacteam93.robot2014.RobotMap;

/**
 *
 * @author bradmiller
 */
public class PivoterAlign extends CommandBase {

    public PivoterAlign() {
        requires(CommandBase.pivoter);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        
    
        
        //Sets pivoter all the way to one side
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        
        //When correct position is reached, return true
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
