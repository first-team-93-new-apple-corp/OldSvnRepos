package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Climber;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClimberContinuous extends Command
{
	
	public ClimberContinuous()
	{
		// Use requires() here to declare subsystem dependencies
		requires(Robot.climber);
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
		double climber = -OI.climber.get();
		if (climber < 0)
		{
			climber = 0;
		}
		Climber.CLIMBER_MOTOR_A.set(climber);
		Climber.CLIMBER_MOTOR_B.set(climber);
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
