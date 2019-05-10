package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Moves the high part of the arm.
 */
public class MoveHighArm extends Command
{

	public MoveHighArm()
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		RobotMap.leftHighArm.set(true);
		RobotMap.rightHighArm.set(true);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
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
		if (RobotMap.releaseScaler.get() == DoubleSolenoid.Value.kForward)
		{
			RobotMap.leftHighArm.set(false);
			RobotMap.rightHighArm.set(false);
		}
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
	}
}
