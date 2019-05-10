package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.subsystems.Manipulator;

import edu.wpi.first.wpilibj.command.Command;

public class SetManipulator extends Command
{
	double m_setpoint;
	double m_error;
	double m_time;

	public SetManipulator(double Setpoint, double Error)
	{
		requires(Robot.defenseManipulator);
	}

	@Override
	protected void initialize()
	{
		// TODO Auto-generated method stub
		RobotMap.ManipulatorPositionControl.enable();
		m_time = 0;
	}

	@Override
	protected void execute()
	{
		// TODO Auto-generated method stub
		RobotMap.ManipulatorPositionControl.setSetpoint(m_setpoint);
		System.out.println("Manipulator Error = " + RobotMap.ManipulatorPositionControl.getError());
	}

	@Override
	protected boolean isFinished()
	{
		double currentError = Math.abs(RobotMap.ManipulatorPositionControl.getError());
		// System.out.println("manipulator error " + currentError);
		if (currentError <= m_error)
		{
			m_time += 20;
			if (m_time >= 500)
			{
				return true;
			}
		} else
		{
			m_time = 0;
		}

		return false;
	}

	@Override
	protected void end()
	{
		// TODO Auto-generated method stub
		Manipulator.setManipulator(0.0);
		RobotMap.ManipulatorPositionControl.disable();
	}

	@Override
	protected void interrupted()
	{
		// TODO Auto-generated method stub
		this.end();
	}

}
