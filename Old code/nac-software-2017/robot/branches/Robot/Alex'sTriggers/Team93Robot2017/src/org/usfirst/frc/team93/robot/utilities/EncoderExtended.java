package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * EncoderExtended
 * 
 * @author EvansChen
 * 
 * Extends on the functionality by offering 2 PID Sources, a rate source and a
 * distance source, simultaneously.
 */
public class EncoderExtended
{
	public Encoder m_encoder;
	public DistancePIDSource distance;
	public RatePIDSource rate;
	
	/**
	 * Constructor for an EncoderExtended.
	 * 
	 * @param encoder
	 */
	public EncoderExtended(Encoder encoder)
	{
		m_encoder = encoder;
		distance = new DistancePIDSource();
		rate = new RatePIDSource();
	}
	
	/**
	 * A PID Source serving as the distance value of the encoder.
	 */
	public class DistancePIDSource extends SlavePIDSource
	{
		@Override
		public double get()
		{
			return m_encoder.getDistance();
		}
	}
	
	/**
	 * A PID Source serving as the rate value of the encoder.
	 */
	public class RatePIDSource extends SlavePIDSource
	{
		@Override
		public double get()
		{
			// filters NaN values as 0.
			Double rate = m_encoder.getRate();
			if (Double.isFinite(rate))
			{
				return rate * m_gain;
			}
			return 0;
		}
	}
	
	/**
	 * A template for slaves of another PID Source (e.g. the distance and rate
	 * PIDSources from an Encoder)
	 */
	public class SlavePIDSource implements PIDSourceEnhanced
	{
		double m_gain = 1.0;
		
		@Override
		public void setPIDSourceType(PIDSourceType pidSource)
		{
		}
		
		@Override
		public PIDSourceType getPIDSourceType()
		{
			return PIDSourceType.kDisplacement;
		}
		
		@Override
		public double pidGet()
		{
			return get();
		}
		
		@Override
		public double get()
		{
			return 0;
		}
		
		@Override
		public double getGain()
		{
			return m_gain;
		}
		
		@Override
		public void setGain(double value)
		{
			m_gain = value;
		}
		
		@Override
		public PIDSource getPIDSource()
		{
			return m_encoder;
		}
	}
}
