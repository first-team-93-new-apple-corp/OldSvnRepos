
package nacteam93.robot2014.commands;

import nacteam93.robot2014.RobotMap;

/**
 *
 * @author Andy
 */
public class AngularTurn extends CommandBase {
    
    private double setpoint;
    /**
     * Construct the turn command
     * @param angle angle, in degrees, to turn to. Reference point is the passing position = 0 degrees
     */
    public AngularTurn(double angle) {
        requires(CommandBase.pivoter);
        setpoint = angle;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        RobotMap.armPID.enable();
        RobotMap.armPID.setSetpoint(setpoint);

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        
        //return true when 90 degrees are turned
        boolean result;
        result = RobotMap.armPID.getError() <= 3;
        return result;
    }

    // Called once after isFinished returns true
    protected void end() {
        RobotMap.armPID.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}