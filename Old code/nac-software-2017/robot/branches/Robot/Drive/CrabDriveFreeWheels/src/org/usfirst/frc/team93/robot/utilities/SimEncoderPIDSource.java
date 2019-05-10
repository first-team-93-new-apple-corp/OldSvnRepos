package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;

public class SimEncoderPIDSource implements WheelAnglePIDSource
{
	
	// the attached instance of the SPI.
	Encoder m_encoder;
	
	// offset
	int m_offset;
	// gain
	double m_gain;
	
	// last value
	Integer m_last;
	
	final int ticksPerRotation = 360;
	
	// tracks number of rotations
	int m_rotations = 0;
	
	// compensation for SPI Encoder placement.
	double m_compensation = 0;
	
	public SimEncoderPIDSource(Encoder encoder)
	{
		m_encoder = encoder;
		m_encoder.setDistancePerPulse(1);
		m_offset = 0;
		m_gain = 1.0;
	}
	
	/**
	 * SPI Encoder only gives displacement. Cannot set PIDSourceType.
	 */
	@Override
	public void setPIDSourceType(PIDSourceType pidSource)
	{
	}
	
	/**
	 * SPI Encoder only gives displacement.
	 */
	@Override
	public PIDSourceType getPIDSourceType()
	{
		return PIDSourceType.kDisplacement;
	}
	
	/**
	 * Gets the position of the SPI Encoder.
	 */
	@Override
	public double pidGet()
	{
		// get value from encoder
		Integer readValue = readInt();
		if ((readValue == null) && (m_last != null))
		{
			readValue = m_last;
		}
		
		// return encoder value
		m_last = readValue;
		return readValue;
	}
	
	/**
	 * Gets the position of the SPI Encoder.
	 * 
	 * @return The SPI Encoder position.
	 */
	@Override
	public double get()
	{
		return pidGet();
	}
	
	/**
	 * Returns the multiplier to convert ticks to rotations.
	 * 
	 * @return The multiplier
	 */
	public double getGain()
	{
		return m_gain;
	}
	
	/**
	 * Sets the multiplier to convert ticks to rotations.
	 * 
	 * @param gain
	 */
	@Override
	public void setGain(double gain)
	{
		m_gain = gain;
	}
	
	/**
	 * Sets the SPI Encoder to a value;
	 * 
	 * @param value
	 */
	@Override
	public void set(double value)
	{
		if (m_gain != 0)
		{
			m_offset = -readInt() + (int) Math.round(value / getGain());
		}
	}
	
	/**
	 * Resets the SPI Encoder to 0.0
	 */
	@Override
	public void reset()
	{
		set(0.0);
	}
	
	/**
	 * Sets the compensation of the SPI Encoder. Suppose the SPI Encoder is at
	 * 200 degrees at neutral position. Then, make compensation 200.
	 * 
	 * @param compensation
	 */
	public void setCompensation(double compensation)
	{
		m_compensation = compensation;
	}
	
	/**
	 * Returns the compensation value subtracted off of the SPI Encoder.
	 * 
	 * @return
	 */
	public double getCompensation()
	{
		return m_compensation;
	}
	
	/**
	 * Reads an integer from the SPI Encoder.
	 * 
	 * @return The SPI Encoder's current location
	 */
	private Integer readInt()
	{
		return (int) m_encoder.getDistance();
	}
	
	@Override
	public void finishReset()
	{
		reset();
	}
	
	@Override
	public int getMisreads()
	{
		return 0;
	}
}
