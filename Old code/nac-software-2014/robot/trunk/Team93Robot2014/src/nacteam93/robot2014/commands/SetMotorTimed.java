/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nacteam93.robot2014.commands;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * @author Controls
 * @param motor, the motor to set
 * @param speed, the speed to set the motor to
 * @param millis, the milliseconds to set the motor for
 */
public class SetMotorTimed extends CommandBase {
    
    private final SpeedController speedController;
    private final double speed;
    private final int millisToWait;
    private int millisPassed;
    
    public SetMotorTimed(SpeedController motor, double speed, int millis) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        this.speedController = motor;
        this.speed = speed;
        this.millisToWait = millis;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        speedController.set(speed);
        millisPassed = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        millisPassed += 20;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return millisPassed >= millisToWait;
    }

    // Called once after isFinished returns true
    protected void end() {
        speedController.set(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        this.end();
    }
}