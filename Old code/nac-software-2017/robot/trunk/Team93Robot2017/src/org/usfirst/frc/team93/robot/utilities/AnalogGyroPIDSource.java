package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * This class is used to provide a PID source using our gyro sensor so that we
 * can change the robot's heading precisely.
 */
public class AnalogGyroPIDSource implements GyroPIDSource
{
	
	double m_offset;
	double m_gain = 1.0;
	double m_global_offset;
	AnalogGyro m_gyro;
	
	/**
	 * Constructor for the GyroPIDSource.
	 * 
	 * @param channel The channel the gyro is plugged into.
	 */
	public AnalogGyroPIDSource(AnalogGyro gyro)
	{
		m_gyro = gyro;
		reset();
		m_offset = 0.0;
		m_gain = 1.0;
		m_global_offset = 0.0;
	}
	
	/**
	 * Gets a reference to the AnalogGyro Gyro.
	 * 
	 * @return The AnalogGyro Gyro.
	 */
	public AnalogGyro getGyro()
	{
		return m_gyro;
	}
	
	/**
	 * Alias of getRawAngle().
	 * 
	 * @return The raw angle.
	 */
	@Override
	public double getAngle()
	{
		return m_gyro.getAngle() * m_gain;
	}
	
	/**
	 * PID Source Type can only be kDisplacement. This method does nothing.
	 */
	@Override
	public void setPIDSourceType(PIDSourceType pidSource)
	{
	}
	
	/**
	 * PID Source Type can only be kDisplacement.
	 */
	@Override
	public PIDSourceType getPIDSourceType()
	{
		return PIDSourceType.kDisplacement;
	}
	
	/**
	 * Returns the heading of the gyro as a PID Source. This includes the
	 * offset.
	 */
	@Override
	public double pidGet()
	{
		return getAngle() + m_offset;
	}
	
	/**
	 * Returns the raw heading, straight from the gyro, without offsets.
	 * 
	 * @return The gyro's raw heading.
	 */
	@Override
	public double getRawAngle()
	{
		return getAngle();
	}
	
	/**
	 * Returns the global heading, unaffected by set and reset.
	 * 
	 * @return The gyro's raw global heading.
	 */
	@Override
	public double getGlobalAngle()
	{
		return m_global_offset + getAngle();
	}
	
	/**
	 * Returns the angle that the robot is facing.
	 * 
	 * @return The heading of the robot.
	 */
	@Override
	public double get()
	{
		return pidGet();
	}
	
	/**
	 * Resets the PIDSource. (sets the current reading to our reference point)
	 */
	@Override
	public void reset()
	{
		set(0.0);
	}
	
	/**
	 * Sets the PIDSource to a set heading.
	 * 
	 * @param angle The angle to set the heading to.
	 */
	@Override
	public void set(double angle)
	{
		m_global_offset = getAngle();
		m_offset = angle;
		m_gyro.reset();
	}
	
	@Override
	public double getGain()
	{
		return m_gain;
	}
	
	@Override
	public void setGain(double gain)
	{
		m_gain = gain;
	}
}