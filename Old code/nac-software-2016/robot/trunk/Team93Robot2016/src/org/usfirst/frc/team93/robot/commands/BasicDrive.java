package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This class takes values from the joystick and directly writes this value to
 * the motors, does not factor any scaling.
 * 
 * @author NAC Controls
 *
 */
public class BasicDrive extends Command
{

	public BasicDrive()
	{
		// TODO Auto-generated constructor stub
		requires(Robot.drive);
	}

	@Override
	protected void initialize()
	{
		// TODO Auto-generated method stub
	}

	@Override
	protected void execute()
	{
		// Drive.setLeftMotors(OI.getLeftValue(OI.DEFAULT_DEADZONE));
		// Drive.setRightMotors(OI.getRightValue(OI.DEFAULT_DEADZONE));
	}

	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end()
	{
		// TODO Auto-generated method stub
	}

	@Override
	protected void interrupted()
	{
		// TODO Auto-generated method stub
	}
}
