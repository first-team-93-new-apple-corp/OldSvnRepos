package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class JoystickMagnitude implements PIDSource
{
	public JoystickAxis m_x;
	public JoystickAxis m_y;
	
	public JoystickMagnitude(JoystickAxis x, JoystickAxis y)
	{
		m_x = x;
		m_y = y;
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
	 * Gets the magnitude of the joystick position.
	 */
	@Override
	public double pidGet() {
		return Math.sqrt(Math.pow(m_x.get(), 2) + Math.pow(m_y.get(), 2));
	}
	
	/**
	 * Alias of pidGet() for ease of use.
	 * @return The magnitude of the joystick position.
	 */
	public double get()
	{
		return pidGet();
	}
}
