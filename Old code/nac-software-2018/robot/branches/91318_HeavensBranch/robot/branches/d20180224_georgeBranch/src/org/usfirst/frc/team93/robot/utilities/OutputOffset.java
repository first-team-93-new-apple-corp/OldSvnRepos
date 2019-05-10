package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.PIDOutput;

public class OutputOffset implements PIDOutput
{
	private PIDOutput m_output;
	private double m_offsetVal;
	
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
	
	@Override
	public void pidWrite(double output)
	{
		m_output.pidWrite(output + m_offsetVal);
	}
	
}
