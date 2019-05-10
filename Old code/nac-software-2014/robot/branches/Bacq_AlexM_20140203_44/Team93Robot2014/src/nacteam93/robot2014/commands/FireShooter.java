
package nacteam93.robot2014.commands;

import nacteam93.robot2014.*;

/**
 *
 * @author bradmiller
 */
public class FireShooter extends CommandBase {

    public FireShooter() {
        requires(CommandBase.shooter);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        
        //Actuates the solenoid to release the ratchet system
        //Fix this when RobotMap is populated
        boolean canFire = shooter.isRetracted();
        if(canFire){
    //        RobotMap.whateverTheShooterCylinderIs.gogogoforward();
        }
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        
        //If the ball has been released, return true
        
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        this.end();
    }
}
