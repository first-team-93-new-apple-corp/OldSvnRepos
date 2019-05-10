
package nacteam93.robot2014.commands;

/**
 *
 * @author bradmiller
 */
public class ShooterEmergencyExpel extends CommandBase {

    public ShooterEmergencyExpel() {
        requires(CommandBase.shooter);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        
        //Actuate the firing mechanism
        //Possibly actuates bacquisition expulsion mechanism
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        
        //If sensors say the ball has been shot, return true
        
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
