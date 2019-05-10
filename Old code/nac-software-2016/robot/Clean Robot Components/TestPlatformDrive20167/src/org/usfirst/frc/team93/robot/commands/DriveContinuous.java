
package org.usfirst.frc.team93.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.RobotMap;

/**
 *
 */
public class DriveContinuous extends Command {

    public DriveContinuous() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//sets left motors to right joystick value
    	RobotMap.LEFT_MOTOR_ONE.set(OI.joystick.getRawAxis(1));
    	RobotMap.LEFT_MOTOR_TWO.set(OI.joystick.getRawAxis(1));
    	//sets right motors to right joystick value
    	RobotMap.RIGHT_MOTOR_ONE.set(OI.joystick.getRawAxis(3));
    	RobotMap.RIGHT_MOTOR_TWO.set(OI.joystick.getRawAxis(3));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	//stops all motors
    	//stops left motors
    	RobotMap.LEFT_MOTOR_ONE.set(0);
    	RobotMap.LEFT_MOTOR_TWO.set(0);
    	//stops right motors
    	RobotMap.RIGHT_MOTOR_ONE.set(0);
    	RobotMap.RIGHT_MOTOR_TWO.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
