package org.usfirst.frc.team93.robot.Utilities;

import org.usfirst.frc.team93.robot.RobotMap;

public class DefenseManipulatorPositionProfile
{
	double m_previousSetpoint = 0;
	double m_finalSetpoint = 0;
	double m_error;
	double m_time;
	double m_setpointDifference;
	final double m_updateTime = 20 / 1000;
	boolean m_finished;
	Team93PIDController m_pidController;

	public DefenseManipulatorPositionProfile(Team93PIDController PIDcontroller)
	{
		// time is he amount of time it takes to complete action in seconds
		m_pidController = PIDcontroller;
	}

	public void setTime(double Time)
	{
		m_time = Time;
	}

	public double getTime()
	{
		return m_time;
	}

	public void setSetpoint(double Setpoint)
	{
		m_finalSetpoint = Setpoint;
	}

	public double getSetpoint()
	{
		return m_finalSetpoint;
	}

	public void setError(double Error)
	{
		m_error = Error;
	}

	public double getError()
	{
		return m_error;
	}

	public void start()
	{
		// TODO Auto-generated method stub
		m_previousSetpoint = m_pidController.get();
		m_finished = false;
		m_setpointDifference = m_finalSetpoint - m_previousSetpoint;
		m_pidController.enable();
	}

	public void update()
	{
		// TODO Auto-generated method stub
		if (m_setpointDifference >= 0)
		{
			if (m_previousSetpoint < m_finalSetpoint)
			{
				m_previousSetpoint += m_updateTime * m_setpointDifference / m_time;
			} else
			{
				m_finished = true;
			}
		} else
		{
			if (m_previousSetpoint > m_finalSetpoint)
			{
				m_previousSetpoint += m_updateTime * m_setpointDifference / m_time;
			} else
			{
				m_finished = true;
			}
		}

		m_pidController.setSetpoint(m_previousSetpoint);
	}

	public boolean isFinished()
	{
		// TODO Auto-generated method stub
		double currentError = Math.abs(m_pidController.getError());
		System.out.println(currentError);
		if (currentError <= m_error && m_finished)
		{
			return true;
		}
		return false;
	}

	public void End()
	{
		RobotMap.ManipulatorPositionControl.disable();

	}
}
