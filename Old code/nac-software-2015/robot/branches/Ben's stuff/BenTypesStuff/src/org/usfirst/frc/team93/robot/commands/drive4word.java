package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class drive4word extends Command {

	double m_setpoint;

    public drive4word(double setpoint) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    	m_setpoint = setpoint;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        RobotMap.driveAllMotorsEncoders.reset();
        RobotMap.pid1.enable();
        RobotMap.pid1.setSetpoint(m_setpoint);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (RobotMap.pid1.getError() > 10)
    	{
    		return true;
    	}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        RobotMap.driveAllMotorsEncoders.reset();
        RobotMap.pid1.disable();
        RobotMap.moveAllMotors.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
