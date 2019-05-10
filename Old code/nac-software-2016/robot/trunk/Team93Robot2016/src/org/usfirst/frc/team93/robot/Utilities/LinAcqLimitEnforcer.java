package org.usfirst.frc.team93.robot.Utilities;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

public class LinAcqLimitEnforcer implements PIDOutput
{

	PIDOutput m_pidOut;
	PIDSource m_analogInput;
	double m_lowLimit;
	double m_highLimit;
	boolean m_reversed;
	double m_secondaryLowLimit;
	double m_secondaryHighLimit;
	double m_secondarySpeedModifier;

	public LinAcqLimitEnforcer(PIDOutput speedController, PIDSource analogInput, double lowerLimit, double upperLimit)
	{
		m_pidOut = speedController;
		m_analogInput = analogInput;
		m_lowLimit = lowerLimit;
		m_highLimit = upperLimit;
		m_reversed = false;

		m_secondaryLowLimit = lowerLimit;
		m_secondaryHighLimit = upperLimit;
		m_secondarySpeedModifier = 1.0;
	}

	public LinAcqLimitEnforcer(PIDOutput speedController, PIDSource analogInput, double lowerLimit, double upperLimit,
			boolean reversed)
	{
		m_pidOut = speedController;
		m_analogInput = analogInput;
		m_lowLimit = lowerLimit;
		m_highLimit = upperLimit;
		m_reversed = reversed;

		m_secondaryLowLimit = lowerLimit;
		m_secondaryHighLimit = upperLimit;
		m_secondarySpeedModifier = 1.0;
	}

	public void setLimits(double newLower, double newUpper)
	{
		m_lowLimit = newLower;
		m_highLimit = newUpper;
	}

	public void setSecondaryLimits(double newLower, double newUpper, double slowDownScaling)
	{
		m_secondaryLowLimit = newLower;
		m_secondaryHighLimit = newUpper;
		m_secondarySpeedModifier = slowDownScaling;
	}

	public double getUpperLimit()
	{
		return m_highLimit;
	}

	public double getLowerLimit()
	{
		return m_lowLimit;
	}

	@Override
	public void pidWrite(double output)
	{
		set(output, false);
	}

	public void set(double output, boolean override)
	{

		double adjustedOutput = adjustOutput(output);

		if (override)
		{
			m_pidOut.pidWrite(adjustedOutput);
		} else
		{
			if (output > 0)
			{
				if (m_analogInput.pidGet() > m_lowLimit)
				{
					if (m_analogInput.pidGet() < m_secondaryLowLimit)
					{
						adjustedOutput *= m_secondarySpeedModifier;
					}
					m_pidOut.pidWrite(adjustedOutput);
				} else
				{
					m_pidOut.pidWrite(0.0);
				}
			} else if (output <= 0)
			{
				if (m_analogInput.pidGet() < m_highLimit)
				{
					if (m_analogInput.pidGet() > m_secondaryHighLimit)
					{
						adjustedOutput *= m_secondarySpeedModifier;
					}
					m_pidOut.pidWrite(adjustedOutput);
				} else
				{
					m_pidOut.pidWrite(0.0);
				}
			}
		}

	}

	private double adjustOutput(double toAdjust)
	{
		toAdjust = (m_reversed) ? -toAdjust : toAdjust;
		// double currentInput = m_analogInput.pidGet();
		// if ((currentInput > m_secondaryHighLimit) || (currentInput <
		// m_secondaryLowLimit))
		// {
		// toAdjust *= m_secondarySpeedModifier;
		// }
		return toAdjust;
	}

	public void set(double output)
	{
		// TODO Auto-generated method stub

	}

}
