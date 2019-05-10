package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class unClimb extends Command
{
	Boolean Finished;

	public unClimb()
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		Finished = false;
		OI.NoSmoke = -1;
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		RobotMap.Climber.set(1);
		if (OI.NoSmoke != -1)
		{
			Finished = true;
		}

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		return Finished;
	}

	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		RobotMap.Climber.set(0);
		OI.NoSmoke = 0;
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		RobotMap.Climber.set(0);
		OI.NoSmoke = 0;
	}
}
