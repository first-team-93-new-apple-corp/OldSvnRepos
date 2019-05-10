package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * SPIEncoderSource
 * 
 * @author Evans Chen This merely serves to hold a periodically update value,
 * the value of the related SPIEncoderPIDSource.
 */
public class DummyPIDSource implements PIDSource, PIDOutput
{
	public double m_value = 0;
	
	public DummyPIDSource()
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
		return m_value;
	}
	
	public double get()
	{
		return pidGet();
	}
	
	@Override
	public void setPIDSourceType(PIDSourceType pidSourceType)
	{
	}
	
	public void set(double value)
	{
		m_value = value;
	}
	
	@Override
	public void pidWrite(double output)
	{
		set(output);
	}
}