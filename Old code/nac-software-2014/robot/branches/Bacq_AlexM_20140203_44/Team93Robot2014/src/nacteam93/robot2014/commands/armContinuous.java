/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nacteam93.robot2014.commands;

import nacteam93.robot2014.OI;
import nacteam93.robot2014.RobotMap;

/**
 *
 * @author NAC Controls
 */
public class armContinuous extends CommandBase {
    
    public armContinuous() {
        requires(pivoter);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        
        double speed = OI.operator.getRawAxis(1);
        if(withinTolerance(speed)){
            RobotMap.armMotors.set(speed);
        }
        else{
            RobotMap.armMotors.set(0);
        }       
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
    
    private boolean withinTolerance(double speed){
        
        boolean result = true;
        double angle = RobotMap.armPotentiometer.getAngle();
        
        if(speed>1 && angle>=pivoter.angle_max){
            result = false;
        }
        else if(speed<1 && angle<=pivoter.angle_min){
            result = false;
        }
        return result;
    }
}