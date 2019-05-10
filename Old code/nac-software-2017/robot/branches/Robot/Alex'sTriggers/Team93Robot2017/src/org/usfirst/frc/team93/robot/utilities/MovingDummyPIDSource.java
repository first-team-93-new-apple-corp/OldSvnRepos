package org.usfirst.frc.team93.robot.utilities;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * SPIEncoderSource
 * 
 * @author Evans Chen This merely serves to hold a periodically update value,
 * the value of the related SPIEncoderPIDSource.
 */
public class MovingDummyPIDSource implements PIDSource
{
	public volatile ArrayList<Double> m_average;
	
	public Object lock;
	
	public MovingDummyPIDSource()
	{
		m_average = new ArrayList<Double>();
		lock = new Object();
	}
	
	@Override
	public PIDSourceType getPIDSourceType()
	{
		return PIDSourceType.kDisplacement;
	}
	
	@Override
	public double pidGet()
	{
		synchronized (lock)
		{
			double sum = 0;
			double max = Double.MIN_VALUE;
			double min = Double.MAX_VALUE;
			for (Double d : m_average)
			{
				if (d != null)
				{
					sum += d;
				}
				if (d < min)
				{
					min = d;
				}
				if (d > max)
				{
					max = d;
				}
			}
			if (m_average.size() > 0)
			{
				return sum / m_average.size();
			}
			return 0.0;
		}
	}
	
	public double get()
	{
		synchronized (lock)
		{
			return pidGet();
		}
	}
	
	@Override
	public void setPIDSourceType(PIDSourceType pidSourceType)
	{
	}
	
	public void set(double value)
	{
		synchronized (lock)
		{
			m_average.add(value);
			int magic_number = 5;
			if (m_average.size() > magic_number)
			{
				m_average.remove(0);
			}
		}
	}
}