package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AwfulAuto extends Command
{

	int time;
	int maxTime;

	public AwfulAuto(int driveTime)
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.drive);
		maxTime = driveTime;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		time = 0;
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		time += 20;
		Drive.setAllMotors(-0.5);
		System.out.println(time);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		return (time >= maxTime);
	}

	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		Drive.setAllMotors(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		this.end();
	}
}
