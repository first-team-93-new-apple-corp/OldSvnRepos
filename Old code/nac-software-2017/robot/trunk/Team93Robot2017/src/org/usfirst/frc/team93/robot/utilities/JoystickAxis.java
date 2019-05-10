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
	
	private double m_gain;
	
	/**
	 * Constructor for JoystickAxis
	 * 
	 * @param joystick
	 *            The joystick to get from
	 * @param axis
	 *            The axis
	 */
	public JoystickAxis(Joystick joystick, int axis)
	{
		m_joystick = joystick;
		m_axis = axis;
		m_gain = 1.0;
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
	public double pidGet()
	{
		return deadzone(m_joystick.getRawAxis(m_axis) * m_gain, m_deadzone);
	}
	
	/**
	 * Alias of pidGet() for ease of use.
	 * 
	 * @return The value of the axis.
	 */
	public double get()
	{
		return pidGet();
	}
	
	/**
	 * @codereview josh.hawbaker 3.21.17 Tried to clarify what a 'deadzone'
	 *             meant since it wasn't really explained.
	 */
	
	/**
	 * Set the deadzone size of the axis.
	 * 
	 * @param deadzone
	 *            The smallest joystick reading before we round it to zero.
	 *            (This ensures we don't constantly set the motors to something
	 *            like .05)
	 */
	public void setDeadzone(double deadzone)
	{
		m_deadzone = deadzone;
	}
	
	/**
	 * Deadzoning method used to prevent setting motors to small values
	 * unintentionally.
	 * 
	 * @param value
	 *            The value to deadzone (joystick reading)
	 * @param deadzone
	 *            The deadzone to apply (the smallest value before we set the
	 *            reading to zero)
	 * @return The deadzoned value (will be greater than the deadzone or zero)
	 */
	private double deadzone(double value, double deadzone)
	{
		/**
		 * @codereview josh.hawbaker 3.21.17 I don't think I've done something
		 *             quite like this before. Does return end the double? Would
		 *             it be better formatting to set value to zero if deadzone
		 *             conditions are met instead of a return?
		 */
		if (Math.abs(value) < deadzone)
		{
			return 0;
		}
		return value;
	}
	
	/**
	 * Sets the gain multiplier
	 * 
	 * @param gain
	 */
	public void setGain(double gain)
	{
		m_gain = gain;
	}
	
	/**
	 * Returns the gain multiplier
	 * 
	 * @return
	 */
	public double getGain()
	{
		return m_gain;
	}
}