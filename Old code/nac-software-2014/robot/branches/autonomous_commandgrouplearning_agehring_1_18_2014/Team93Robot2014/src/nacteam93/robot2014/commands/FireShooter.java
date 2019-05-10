
package nacteam93.robot2014.commands;
import nacteam93.robot2014.RobotMap;

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
    int iterationNum = 0;
    // Called just before this Command runs the first time
    protected void initialize() {
        iterationNum = 0;
        RobotMap.driveFrontLeft.set(0);
        RobotMap.driveBackLeft.set(0);
        RobotMap.driveFrontRight.set(0);
        RobotMap.driveBackRight.set(0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        
        //Actuates the solenoid to release the ratchet system
        //Fix this when RobotMap is populated
        boolean canFire = shooter.isRetracted();
        if(canFire){
            RobotMap.driveFrontLeft.set(0.5);
            RobotMap.driveBackLeft.set(0);
            RobotMap.driveFrontRight.set(0);
            RobotMap.driveBackRight.set(0);
        }
        iterationNum += 1;
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        // checks for 20 executes
        if (iterationNum == 20){
            return true;
        }
        else{
            return false;
        }
        //If the ball has been released, return true
        
    }

    // Called once after isFinished returns true
    protected void end() {
        RobotMap.driveFrontLeft.set(0);
        RobotMap.driveBackLeft.set(0);
        RobotMap.driveFrontRight.set(0);
        RobotMap.driveBackRight.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        this.end();
    }
}
