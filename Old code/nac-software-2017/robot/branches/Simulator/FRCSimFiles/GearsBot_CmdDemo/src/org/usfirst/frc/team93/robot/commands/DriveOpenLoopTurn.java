package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveOpenLoopTurn extends Command {
	
	double m_speed = 1.0;
	long m_duration_msecs = 0;
	long m_start_time = 0;

    public DriveOpenLoopTurn(double speed, long duration_msecs) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);\
    	requires(Robot.drivetrain);
    	m_duration_msecs = duration_msecs;
    	m_speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_start_time = System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.drive(m_speed, -1*m_speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	 if (m_duration_msecs > 0 )
         {
    		//this.end();
         	return (System.currentTimeMillis() > m_start_time + m_duration_msecs);
         	
         }
         else
         {
         	return false;
         }
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
