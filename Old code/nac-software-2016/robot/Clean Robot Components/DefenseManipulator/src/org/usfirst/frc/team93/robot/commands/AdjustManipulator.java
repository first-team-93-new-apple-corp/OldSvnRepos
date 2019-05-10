package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Manipulator;

import edu.wpi.first.wpilibj.command.Command;

public class AdjustManipulator extends Command
{

	double m_scalingFactor;

	public AdjustManipulator(double ScalingFactor)
	{
		m_scalingFactor = ScalingFactor;
		requires(Robot.defenseManipulator);
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
		Manipulator.setManipulator(OI.getOperatorLY(0.05 * m_scalingFactor));
		// RobotMap.RIGHT_SHOOTER.set(OI.getOperatorLY(0.05 * m_scalingFactor));
		// RobotMap.LEFT_SHOOTER.set(OI.getOperatorLY(0.05 * m_scalingFactor));
		// double speed = OI.getOperatorLY(0.05 * m_scalingFactor);
		// speed *= (speed >= 0) ? -.75 : -.75;
		// speed *= m_scalingFactor;
		// Shooter.ManualAdjustShootingAngle(speed); }
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
		Manipulator.setManipulator(0.0);
	}

	@Override
	protected void interrupted()
	{
		// TODO Auto-generated method stub
		this.end();
	}

}
