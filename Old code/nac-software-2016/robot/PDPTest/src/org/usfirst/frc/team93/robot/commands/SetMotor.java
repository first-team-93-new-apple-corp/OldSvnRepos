package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetMotor extends Command {
	double m_value;
    public SetMotor(double value) {
        // Use requires() here to declare subsystem dependencies
    	m_value = value;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	RobotMap.talon.set(m_value);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	RobotMap.talon.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
