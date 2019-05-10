
package org.usfirst.frc.team93.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.RobotMap;

/**
 *
 */
public class PrintPDPCurrent extends Command {
	int m_channel;
    public PrintPDPCurrent(int channel) {
    	m_channel = channel;
        // Use requires() here to declare subsystem dependencies
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println(RobotMap.pdp.getCurrent(m_channel));
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
    }
}
