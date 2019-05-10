
package nacteam93.robot2014.commands;

/**
 *
 * @author bradmiller
 */
public class BacquisitionEmergencyExpel extends CommandBase {

    public BacquisitionEmergencyExpel() {
        requires(CommandBase.bacquisition);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        
        //Runs Actuators to expel ball, full speed.
        //Possibly fires shooter as well
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        
        //If sensors say the ball is expelled, return true
        
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
