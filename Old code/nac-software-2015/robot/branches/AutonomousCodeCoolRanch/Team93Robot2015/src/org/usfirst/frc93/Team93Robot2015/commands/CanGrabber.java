package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc93.Team93Robot2015.RobotMap;

/**
 *
 */
public class CanGrabber extends Command {
    boolean grabbing;
    private double m_waitTime = 0.5;
    private Timer m_timer = new Timer();

    public CanGrabber(boolean state) {
        grabbing = state;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        m_timer.reset();
        m_timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (grabbing == true) {
            RobotMap.CLAW_SOLENOID.set(DoubleSolenoid.Value.kForward);
        }
        else {
            RobotMap.CLAW_SOLENOID.set(DoubleSolenoid.Value.kReverse);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        if (m_timer.get() >= m_waitTime)
            return true;
        else {
            return false;
        }
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
