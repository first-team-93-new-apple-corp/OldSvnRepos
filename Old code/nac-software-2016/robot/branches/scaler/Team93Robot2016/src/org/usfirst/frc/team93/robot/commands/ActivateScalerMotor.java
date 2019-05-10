package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Activates the scaler.
 */
public class ActivateScalerMotor extends Command
{

	double m_ticks;
	double m_maxError;
	boolean end;

	public ActivateScalerMotor(double ticks, int maxError)
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		end = false;
		m_ticks = ticks;
		m_maxError = maxError;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		RobotMap.scalerControl.reset();
		RobotMap.scalerEncoder.reset();
		RobotMap.scalerControl.enable();
		RobotMap.scalerControl.setSetpoint(m_ticks);
		RobotMap.scalerControl.setOutputRange(-0.1, 0.1);
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
		if (end)
		{
			return true;
		}
		double currentError = Math.abs(RobotMap.scalerControl.getError());
		System.out.println(currentError);
		if (currentError <= m_maxError)
		{
			return true;
		}
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		RobotMap.scalerControl.disable();
		RobotMap.RETRACTER.set(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{

	}
}