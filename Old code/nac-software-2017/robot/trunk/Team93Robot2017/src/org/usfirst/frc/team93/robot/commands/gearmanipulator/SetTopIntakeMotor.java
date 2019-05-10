package org.usfirst.frc.team93.robot.commands.gearmanipulator;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command sets the top intake motor on the gear manipulator
 */
public class SetTopIntakeMotor extends Command
{
	/**
	 * @codereview josh.hawbaker 3.20.17 same as the other set intake motor
	 * functions. Refactored to remove the last "s" in "SetTopIntakeMotors"
	 */
	double m_speed;
	
	/*
	 * set the top intake motor to a desired speed
	 */
	public SetTopIntakeMotor(double speed)
	{
		requires(Robot.gearManipulator);
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		m_speed = speed;
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		GearManipulator.setTopIntakeMotors(m_speed);
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
		return true;
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
