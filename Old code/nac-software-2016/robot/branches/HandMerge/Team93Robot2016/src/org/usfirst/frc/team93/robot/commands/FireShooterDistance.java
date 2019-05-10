package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FireShooterDistance extends Command
{

	double m_distance;
	double m_speed;
	double m_angle;

	public FireShooterDistance(double distance)
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		m_distance = distance;
		requires(Robot.shooter);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		m_speed = Shooter.findFireSpeed(m_distance);
		m_angle = Shooter.findFireAngleTicks(m_distance);
		RobotMap.LEFT_SHOOTER.set(m_speed);
		RobotMap.RIGHT_SHOOTER.set(m_speed);
		Shooter.setAngle(m_angle);
		if (Shooter.TestSpeed(m_speed))
		{
			RobotMap.LEFT_BACQUASITION.set(1.0);
			RobotMap.RIGHT_BACQUASITION.set(1.0);
		}
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
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
	}
}
