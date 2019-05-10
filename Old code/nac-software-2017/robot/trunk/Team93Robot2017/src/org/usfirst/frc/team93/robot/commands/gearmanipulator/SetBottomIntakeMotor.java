package org.usfirst.frc.team93.robot.commands.gearmanipulator;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command sets the bottom intake motor on the gear manipulator.
 */
public class SetBottomIntakeMotor extends Command
{
	/**
	 * @codereview josh.hawbaker added a javadoc. Funny enough, the action was
	 * already in initialize. Refactored removing the last "s" in
	 * "SetBotomIntakeMotors" since there's only one motor.
	 */
	double m_speed;
	
	public SetBottomIntakeMotor(double speed)
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.gearManipulator);
		m_speed = speed;
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		GearManipulator.setBottomIntakeMotors(m_speed);
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
