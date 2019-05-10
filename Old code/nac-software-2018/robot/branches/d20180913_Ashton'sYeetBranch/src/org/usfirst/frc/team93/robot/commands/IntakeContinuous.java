package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Claw;
import org.usfirst.frc.team93.robot.utilities.DriveChooser;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeContinuous extends Command
{
	
	public IntakeContinuous()
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.claw);
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
		if (!Claw.leftLimit.get())
		{
			Claw.leftIntake.set(DriveChooser.deadzone(OI.operator.getRawAxis(1), 0.15) * 0.75);
		}
		else
		{
			Claw.leftIntake.set(0);
		}
		if (!Claw.rightLimit.get())
		{
			Claw.rightIntake.set(DriveChooser.deadzone(OI.operator.getRawAxis(1), 0.15) * -1);
		}
		else
		{
			Claw.rightIntake.set(0);
		}
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
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
	}
}
