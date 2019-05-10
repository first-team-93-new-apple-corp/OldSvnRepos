package org.usfirst.frc.team93.robot.Utilities;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

public class ManipulatorLimit extends LinAcqLimitEnforcer
{

	public ManipulatorLimit(PIDOutput speedController, PIDSource analogInput, double lowerLimit, double upperLimit)
	{
		super(speedController, analogInput, lowerLimit, upperLimit);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void pidWrite(double output)
	{
		set(output);
	}

	@Override
	public void set(double output)
	{
		// TODO Auto-generated method stub
		if (output > 0)
		{
			if (m_analogInput.pidGet() > m_lowLimit)
			{
				m_pidOut.pidWrite(output);
			} else
			{
				m_pidOut.pidWrite(0.0);
			}
		} else if (output <= 0)
		{
			if (m_analogInput.pidGet() < m_highLimit)
			{
				m_pidOut.pidWrite(output);
			} else
			{
				m_pidOut.pidWrite(0.0);
			}
		}
	}
}
