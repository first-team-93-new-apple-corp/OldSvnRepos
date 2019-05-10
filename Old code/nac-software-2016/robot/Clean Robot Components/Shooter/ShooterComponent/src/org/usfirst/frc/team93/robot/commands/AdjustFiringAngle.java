package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.LinearActuator;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This class adjusts the angle of the shooter based off of joystick inputs
 * 
 * @author NAC Controls
 *
 */
public class AdjustFiringAngle extends Command
{
	/*
	 * This adjusts the shooter to fire at various angles.
	 */
	double m_scalingFactor;

	/**
	 * @param scalingFactor
	 *            This is the value that it is used as a multiplier to apply
	 *            Against the value of the position of the joystick
	 */
	public AdjustFiringAngle(double scalingFactor)
	{
		// Use requires() here to declare subsystem dependencies
		m_scalingFactor = scalingFactor;
		requires(Robot.linearActuator);

	}

	@Override
	protected void initialize()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void execute()
	{
		// TODO Auto-generated method stub
		double speed = OI.getOperatorRY(0.05 * m_scalingFactor);
		speed *= -.75;
		speed *= m_scalingFactor;
		speed *= OI.operator.getRawButton(6) ? 0.45 : 1.0;
		LinearActuator.ManualAdjustShootingAngle(speed);
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
