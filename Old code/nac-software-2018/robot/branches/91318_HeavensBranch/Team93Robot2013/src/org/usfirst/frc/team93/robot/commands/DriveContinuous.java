package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveContinuous extends Command {

    public DriveContinuous() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Math.abs((OI.droperator.getRawAxis(1))) > .1) 
    	{
    		RobotMap.leftBack.set(OI.droperator.getRawAxis(1) * -1);
    		RobotMap.leftFront.set(OI.droperator.getRawAxis(1) * -1);
    	}
    	else
    	{
    		RobotMap.leftBack.set(0);
    		RobotMap.leftFront.set(0);
    		
    	}
    	if(Math.abs(OI.droperator.getRawAxis(3)) > .1) 
    	{
    		RobotMap.rightBack.set(OI.droperator.getRawAxis(3));
    		RobotMap.rightFront.set(OI.droperator.getRawAxis(3));
    	}
    	else
    	{
    		RobotMap.rightBack.set(0);
    		RobotMap.rightFront.set(0);
    	}
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
