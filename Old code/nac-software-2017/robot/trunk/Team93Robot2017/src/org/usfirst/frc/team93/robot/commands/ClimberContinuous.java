package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Climber;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command has the climber run when the operator moves the joystick during
 * teleop. It runs continuously for all of teleop.
 */
public class ClimberContinuous extends Command
{
	/**
	 * @codereview josh.hawbaker 3.10.17 initialized climber double up here,
	 *             added a javadoc, and added a few comments to explain things a
	 *             bit.
	 */
	double climberPower;
	
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
		climberPower = OI.climber.get();
		// the value the climber motor is set to should only be positive -
		// there's no reason to ever climb down
		if (climberPower < 0)
		{
			climberPower = 0;
		}
		Climber.CLIMBER_MOTOR_A.set(climberPower);
		Climber.CLIMBER_MOTOR_B.set(climberPower);
		Climber.CLIMBER_MOTOR_C.set(climberPower);
	}
	
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		// this command should never finish, it runs until the robot is disabled
		return false;
	}
	
	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		// we don't need anything here because this command should never end
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		// we don't need anything here because this command should never be
		// interrupted
	}
}
