package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;


/**
 *
 */
public class drivecontinuous extends Command {

    public drivecontinuous() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.m_Drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double frontLeftSpeed = OI.deadzone(OI.driver.getRawAxis(1));
    	double backLeftSpeed = OI.deadzone(OI.driver.getRawAxis(1));
    	double frontRightSpeed = OI.deadzone(OI.driver.getRawAxis(3)*-1);
    	double backRightSpeed = OI.deadzone(OI.driver.getRawAxis(3)*-1);
    	
    	Drive.frontLeft.set(frontLeftSpeed);
    	Drive.backLeft.set(backLeftSpeed);
    	Drive.frontRight.set(frontRightSpeed);
    	Drive.backRight.set(backRightSpeed);
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
