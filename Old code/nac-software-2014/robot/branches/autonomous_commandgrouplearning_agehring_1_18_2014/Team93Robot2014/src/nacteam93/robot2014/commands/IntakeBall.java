
package nacteam93.robot2014.commands;
import nacteam93.robot2014.RobotMap;
/**
 *
 * @author bradmiller
 */
public class IntakeBall extends CommandBase {

    public IntakeBall() {
        requires(CommandBase.bacquisition);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }
int a = 0;
    // Called just before this Command runs the first time
    protected void initialize() {
        a = 0;
        RobotMap.driveFrontLeft.set(0);
        RobotMap.driveBackLeft.set(0);
        RobotMap.driveFrontRight.set(0);
        RobotMap.driveBackRight.set(0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        a += 1;
        //Runs Actuators to intake ball
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        
        //If sensors say the ball is in, return true
        if (a == 20){
            return true;
        }
        else{
            return false;
        }
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
    }
}
