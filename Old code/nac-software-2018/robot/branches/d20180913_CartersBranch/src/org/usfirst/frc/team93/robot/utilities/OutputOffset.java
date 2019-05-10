package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.PIDOutput;

public class OutputOffset implements PIDOutput
{
	private PIDOutput m_output;
	private double m_offsetVal;
	private boolean M_isInverted = false;
	
	/**
	 * author ben.fager
	 * 
	 * @param output
	 *            the PIDOutput that will be written to with the offset
	 * @param offsetVal
	 *            The amount of offset that is given to the PIDOutput
	 */
	public OutputOffset(PIDOutput output, double offsetVal)
	{
		m_output = output;
		m_offsetVal = offsetVal;
	}
	
	public OutputOffset(PIDOutput output, double offSetVal, boolean isInverted)
	{
		m_output = output;
		m_offsetVal = offSetVal;
		M_isInverted = isInverted;
	}
	
	@Override
	public void pidWrite(double output)
	{
		if (M_isInverted)
		{
			m_output.pidWrite((output + m_offsetVal) * -1);
		}
		else
		{
			m_output.pidWrite((output + m_offsetVal));
		}
	}
	
	public void getPercent()
	{
		// return m_output.
	}
	
}
