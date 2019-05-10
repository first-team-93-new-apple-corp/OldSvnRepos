package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class JoystickDirection implements PIDSource
{
	public JoystickAxis m_x;
	public JoystickAxis m_y;
	
	private double m_last;
	
	private double m_offset;
	
	public JoystickDirection(JoystickAxis x, JoystickAxis y)
	{
		m_x = x;
		m_y = y;
		
		m_last = 0.0;
		
		m_offset = 0;
	}
	
	/**
	 * Cannot set PIDSourceType.
	 */
	@Override
	public void setPIDSourceType(PIDSourceType pidSource)
	{
	}
	
	/**
	 * Only displacement can exist here.
	 */
	@Override
	public PIDSourceType getPIDSourceType()
	{
		return PIDSourceType.kDisplacement;
	}
	
	/**
	 * Gets the direction of the joystick position, in degrees.
	 */
	@Override
	public double pidGet()
	{
		if (m_x.get() != 0 || m_y.get() != 0)
		{
			double angle = Math.toDegrees(Math.atan2(m_y.get(), m_x.get())) - 90.0;
			m_last = angle;
			return angle;
		}
		return m_last;
	}
	
	/**
	 * Alias of pidGet() for ease of use.
	 * 
	 * @return The direction of the joystick position, in degrees.
	 */
	public double get()
	{
		return pidGet();
	}
	
	/**
	 * Resets default value to 0.0.
	 */
	public void reset()
	{
		m_last = 0.0;
	}
	
	/**
	 * Gets the offset angle added to each read.
	 * 
	 * @return
	 */
	public double getOffset()
	{
		return m_offset;
	}
	
	/**
	 * Sets the offset angle to add to each read.
	 * 
	 * @param offset
	 */
	public void setOffset(double offset)
	{
		m_offset = offset;
	}
}
