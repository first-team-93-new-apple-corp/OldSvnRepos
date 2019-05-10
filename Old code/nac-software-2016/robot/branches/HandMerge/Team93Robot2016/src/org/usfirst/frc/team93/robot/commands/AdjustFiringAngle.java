package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This class adjustes the angle of the shooter based off of joystick inputs
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
		requires(Robot.shooter);
		m_scalingFactor = scalingFactor;
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
		Shooter.ManualAdjustShootingAngle(m_scalingFactor * OI.getDriverLY(0.05 * m_scalingFactor));
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
