package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * PIDSourceExtended
 * 
 * @author Evans Chen This class allows additional functionality to be placed on
 * PIDSources, such as a gain multiplier.
 */
public class PIDSourceExtended implements PIDSourceEnhanced
{
	
	PIDSource m_source;
	double m_gain;
	double m_offset;
	
	/**
	 * Constructor for PIDSource extended. Extends the functionality of a
	 * PIDSource that is passed in.
	 * 
	 * @param source The PIDSource to enhance.
	 */
	public PIDSourceExtended(PIDSource source, double gain)
	{
		m_source = source;
		m_gain = gain;
	}
	
	/**
	 * A default of the constructor for PIDSource extended. Gain defaults to
	 * 1.0.
	 * 
	 * @param source The PIDSource to enhance.
	 */
	public PIDSourceExtended(PIDSource source)
	{
		this(source, 1.0);
	}
	
	@Override
	public void setPIDSourceType(PIDSourceType pidSource)
	{
		m_source.setPIDSourceType(pidSource);
	}
	
	/**
	 * Uses PIDSourceType.kDisplacement as a default.
	 */
	@Override
	public PIDSourceType getPIDSourceType()
	{
		return m_source.getPIDSourceType();
	}
	
	/**
	 * Provides the value to give to the PID from the sensor. This is the
	 * source's value multiplied by the gain.
	 */
	@Override
	public double pidGet()
	{
		return (m_source.pidGet() * m_gain) + m_offset;
	}
	
	/**
	 * Alias of pidGet() for ease of use.
	 * 
	 * @return The pidGet() value.
	 */
	@Override
	public double get()
	{
		return m_source.pidGet();
	}
	
	/**
	 * Gets the gain.
	 * 
	 * @return The gain multiplier
	 */
	@Override
	public double getGain()
	{
		return m_gain;
	}
	
	/**
	 * Sets the gain.
	 * 
	 * @param value The gain multiplier to set the PIDSourceExtended to.
	 */
	@Override
	public void setGain(double value)
	{
		m_gain = value;
	}
	
	/**
	 * Returns the PIDSource that this PIDSourceExtended takes input from.
	 * 
	 * @return The PIDSource
	 */
	@Override
	public PIDSource getPIDSource()
	{
		return m_source;
	}
	
	/**
	 * Sets the PIDSource to a value.
	 * 
	 * @param the value to set it to.
	 */
	public void set(double value)
	{
		m_offset = value - m_source.pidGet();
	}
	
	/**
	 * Resets the sensor to 0.
	 */
	public void reset()
	{
		set(0);
	}
}