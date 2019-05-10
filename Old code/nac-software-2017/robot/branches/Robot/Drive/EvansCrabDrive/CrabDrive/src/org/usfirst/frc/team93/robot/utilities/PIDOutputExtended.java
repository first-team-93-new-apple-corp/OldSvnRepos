package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.PIDOutput;

/**
 * PIDOutputExtended
 * @author Evans Chen
 * PIDOutput with a gain multiplier.
 */
public class PIDOutputExtended implements PIDOutput{
	
	//the gain multiplier of the PIDOutputExtended
	double m_gain;
	
	//The PIDOutput that the PIDOutputExtended writes to.
	PIDOutput m_pidOutput;
	
	//The last set value.
	double m_value;
	
	/**
	 * Normal constructor for PIDOutputExtended.
	 * @param pidOutput The PIDOutput to write to.
	 * @param gain The gain multiplier.
	 */
	public PIDOutputExtended(PIDOutput pidOutput, double gain)
	{
		m_gain = gain;
		m_pidOutput = pidOutput;
		m_value = 0;
	}
	
	/**
	 * Default for the constructor of PIDOutputExtended.
	 * @param pidOutput The PIDOutput to write to.
	 */
	public PIDOutputExtended(PIDOutput pidOutput)
	{
		this(pidOutput, 1.0);
	}
	
	/**
	 * Writes the value of the PIDOutputExtended to the PIDOutput.
	 */
	@Override
	public void pidWrite(double output) {
		m_pidOutput.pidWrite(output * m_gain);
		m_value = output * m_gain;
	}
	
	/**
	 * Sets the value of the PIDOutput using pidWrite().
	 * @param value The value to set the PIDOutput to.
	 */
	public void set(double value)
	{
		pidWrite(value);
	}
	
	/**
	 * Gets the last set value.
	 * @return The last set avlue of the PIDOutputExtended
	 */
	public double get()
	{
		return m_value;
	}
	
	/**
	 * Gets the gain.
	 * @return The gain multiplier
	 */
	public double getGain()
	{
		return m_gain;
	}
	
	/**
	 * Sets the gain.
	 * @param value The gain multiplier to set the PIDOutputExtended to.
	 */
	public void setGain(double value)
	{
		m_gain = value;
	}
	
	/**
	 * Returns the PIDOutput that this PIDOutputExtended writes to.
	 * @return The PIDOutput
	 */
	public PIDOutput getPIDOutput()
	{
		return m_pidOutput;
	}
}
