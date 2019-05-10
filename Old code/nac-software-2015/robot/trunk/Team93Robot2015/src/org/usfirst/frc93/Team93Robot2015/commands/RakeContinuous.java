package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc93.Team93Robot2015.Robot;
import org.usfirst.frc93.Team93Robot2015.RobotMap;

/**
 * This command sets the rake motors to a given speed
 */
public class RakeContinuous extends Command {

    double m_speed;

    /**
     * 
     * @param speed
     *            How fast we want to go
     */
    public RakeContinuous(double speed) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.rake);
        m_speed = speed;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        RobotMap.RAKE_MOTOR.set(m_speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        RobotMap.RAKE_MOTOR.set(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        this.end();
    }
}
