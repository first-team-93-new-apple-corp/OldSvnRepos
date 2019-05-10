
package nacteam93.robot2014.commands;

import nacteam93.robot2014.RobotMap;

/**
 *
 * @author bradmiller
 */
public class OutputBall extends CommandBase {

    public OutputBall() {
        requires(CommandBase.bacquisition);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        
        //Runs actuators to expel ball
            //motor1 = driveFrontLeft
            //motor2 = driveBackLeft
        RobotMap.driveFrontLeft.set(0.5);
        RobotMap.driveBackLeft.set(0.5);
        RobotMap.driveFrontRight.set(0);
        RobotMap.driveBackRight.set(0);
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        
        //If sensors say ball is expelled, return true
        
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
