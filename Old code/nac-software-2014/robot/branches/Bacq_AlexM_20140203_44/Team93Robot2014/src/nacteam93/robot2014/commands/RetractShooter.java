
package nacteam93.robot2014.commands;

import nacteam93.robot2014.*;

/**
 *
 * @author bradmiller
 */
public class RetractShooter extends CommandBase {

    public RetractShooter() {
        requires(CommandBase.shooter);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {          
        //Actuates the retraction mechanism
    //    RobotMap.shooterRetractionMotor.gobackwardsNOW();        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        boolean result = shooter.isRetracted();        
        return result;
    }

    // Called once after isFinished returns true
    protected void end() {
    //    RobotMap.shooterRetractionMotor////.STAHP();        
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        this.end();
    }
}
