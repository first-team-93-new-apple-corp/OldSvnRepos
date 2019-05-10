
package nacteam93.robot2014.commands;

/**
 *
 * @author bradmiller
 */
public class PivoterControl extends CommandBase {

    public PivoterControl() {
        requires(CommandBase.pivoter);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        
        //Rotates the pivoter
        //use joystick axis as input?
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        
        //always return false
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
