/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nacteam93.robot2014.commands;

import nacteam93.robot2014.RobotMap;

/**
 *
 * @author Controls
 */
public class SetShooterDogGear extends CommandBase {
    
    private boolean setting;
    
    public SetShooterDogGear(boolean setting) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(CommandBase.shooter);
        this.setting = setting;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        RobotMap.shooterTrigger.set(setting);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
