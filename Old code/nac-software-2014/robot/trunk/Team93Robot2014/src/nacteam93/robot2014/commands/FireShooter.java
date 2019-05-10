
package nacteam93.robot2014.commands;

import nacteam93.robot2014.*;
import nacteam93.robot2014.subsystems.Bacquisition;

/**
 *
 * @author Conor
 */
public class FireShooter extends CommandBase {

    private int millisPassed;
    
    public FireShooter() {
        requires(CommandBase.shooter);
        requires(CommandBase.bacquisition);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
        shooter.setRetracted(false);
        millisPassed = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected synchronized void execute() {
            //Actuates the solenoid to release the ratchet system
            RobotMap.shooterTrigger.set(true);
            Bacquisition.setTopMotor(-1.0);
            Bacquisition.setBottomMotor(-1.0);
            millisPassed += 20;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(OI.manualInterrupt.get()) {
            return true;
        }
        //Delays 500 milliseconds, to ensure the top motor does not
        //stop running before the ball is fully expelled.
        if(millisPassed >= 500) {
            return true;
        }
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        Bacquisition.setBallExpected(false);
        RobotMap.shooterTrigger.set(false);
        Bacquisition.setTopMotor(0.0f);
        Bacquisition.setBottomMotor(0.0f);
        millisPassed = 0;
        System.out.println("FireShooter Finished");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        this.end();
    }
}