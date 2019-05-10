package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.subsystems.DefenseManipulator;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Returns values for the manipulator.
 */
public class ToggleManipulator extends Command
{

	double m_speed;

	public ToggleManipulator(double speed)
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		m_speed = speed;

		requires(Robot.defense);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		DefenseManipulator.toggleSign();
		m_speed = 0.3 * DefenseManipulator.getToggle();
		System.out.println("the mmotor is going " + DefenseManipulator.getToggle() + " capacity");
		System.out.flush();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		RobotMap.lowerArm.set(m_speed);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		if (m_speed > 0 && RobotMap.frontBarLimit.get())
		{
			return true;
		}
		if (m_speed < 0 && RobotMap.backBarLimit.get())
		{
			return true;
		}
		if (m_speed == 0)
		{
			return true;
		}
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		RobotMap.lowerArm.set(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		this.end();
	}
}