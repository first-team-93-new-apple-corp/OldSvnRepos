package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OutputBall extends Command
{

	public OutputBall()
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.shooter);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		RobotMap.LEFT_BACQUASITION.set(-1.0);
		RobotMap.RIGHT_BACQUASITION.set(1.0);
		RobotMap.BallGuideMotor.set(0.5);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		RobotMap.LEFT_BACQUASITION.set(0.0);
		RobotMap.RIGHT_BACQUASITION.set(0.0);
		RobotMap.BallGuideMotor.set(0.0);
		Shooter.setBallPulled(false);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		this.end();
	}
}
