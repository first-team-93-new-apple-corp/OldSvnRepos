package org.usfirst.frc.team93.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.RobotMap;

/**
 *
 */
public class DriveForward extends Command {
	public DriveForward() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.exampleSubsystem);
		
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		
		RobotMap.timer.start();
		RobotMap.rightFront.set(.2);
		RobotMap.rightBack.set(.2);
		RobotMap.frontLeft.set(.2);
		RobotMap.backLeft.set(.2);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		if(RobotMap.timer.get() == 2000)
		{
			return true;
		}
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		RobotMap.rightFront.set(0);
		RobotMap.rightBack.set(0);
		RobotMap.frontLeft.set(0);
		RobotMap.backLeft.set(0);
		RobotMap.timer.stop();
		RobotMap.timer.reset();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
