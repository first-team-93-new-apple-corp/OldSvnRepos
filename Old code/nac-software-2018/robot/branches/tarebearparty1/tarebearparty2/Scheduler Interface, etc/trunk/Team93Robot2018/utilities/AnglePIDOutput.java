package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.PIDOutput;

public class AnglePIDOutput implements PIDOutput
{
	private PIDOutput m_PIDOutputOne;
	private PIDOutput m_PIDOutputTwo;
	
	/**
	 * Makes a PIDOutput where one value is set to the opposite of the other
	 * 
	 * @param PIDOutputOne
	 *            The first PIDOutput that is set to what it is set to
	 * @param PIDOutputTwo
	 *            The second PIDOutput that is set to what it is set to times -1
	 */
	public AnglePIDOutput(PIDOutput PIDOutputOne, PIDOutput PIDOutputTwo)
	{
		m_PIDOutputOne = PIDOutputOne;
		m_PIDOutputTwo = PIDOutputTwo;
	}
	
	/**
	 * Writes to the PIDOutput
	 * 
	 * @param output
	 *            The value that PIDOutputOne is set to and what PIDOutputTwo is set
	 *            to times -1
	 */
	@Override
	public void pidWrite(double output)
	{
		m_PIDOutputOne.pidWrite(output);
		m_PIDOutputTwo.pidWrite(output * -1);
	}
}
