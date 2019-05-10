package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * Many PIDSource subclasses lie below.
 */
public class JoystickAxis implements PIDSource
{
	public Joystick m_joystick;
	public int m_axis;
	
	private double m_deadzone;
	
	/**
	 * Constructor for JoystickAxis
	 * @param joystick The joystick to get from
	 * @param axis The axis
	 */
	public JoystickAxis(Joystick joystick, int axis)
	{
		m_joystick = joystick;
		m_axis = axis;
		
		m_deadzone = 0.1;
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
	 * Gets the value of the axis.
	 */
	@Override
	public double pidGet() {
		return deadzone(m_joystick.getRawAxis(m_axis), m_deadzone);
	}
	
	/**
	 * Alias of pidGet() for ease of use.
	 * @return The value of the axis.
	 */
	public double get()
	{
		return pidGet();
	}
	
	/**
	 * Set the deadzone size of the axis.
	 * @param deadzone
	 */
	public void setDeadzone(double deadzone)
	{
		m_deadzone = deadzone;
	}
	
	/**
	 * Deadzoning method used for creating a deadzone.
	 * @param value The value to deadzone
	 * @param deadzone The deadzone to apply
	 * @return The deadzoned value
	 */
	private double deadzone(double value, double deadzone)
	{
		if (Math.abs(value) < deadzone)
		{
			return 0;
		}
		return value;
	}
}