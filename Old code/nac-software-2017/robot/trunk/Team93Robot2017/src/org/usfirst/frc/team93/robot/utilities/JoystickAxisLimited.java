package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;

/**
 * This class is used to ensure that we don't pass the motors too many values in
 * a short period of time. (It prevents brown-out issues)
 */
public class JoystickAxisLimited implements PIDSource
{
	public Joystick m_joystick;
	public int m_axis;
	
	private double m_deadzone;
	
	private double m_current;
	
	public double m_dampening = 3.0;
	private Timer timer;
	
	private double m_gain;
	
	/**
	 * Constructor for JoystickAxis
	 * 
	 * @param joystick
	 *            The joystick to get from
	 * @param axis
	 *            The axis
	 */
	public JoystickAxisLimited(Joystick joystick, int axis)
	{
		m_joystick = joystick;
		m_axis = axis;
		
		m_deadzone = 0.1;
		m_current = 0.0;
		
		timer = new Timer();
		timer.reset();
		timer.start();
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
		double axis = m_joystick.getRawAxis(m_axis);
		// if the change is not large, then use the current joystick value.
		if (Math.abs(axis - m_current) < m_dampening * timer.get())
		{
			m_current = axis;
		}
		// if the change is too fast, then
		else
		{
			m_current += Math.signum(axis - m_current) * m_dampening * timer.get();
		}
		timer.reset();
		timer.start();
		return deadzone(m_current, m_deadzone);
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
	 * @codereview josh.hawbaker 3.21.17 Copy-pasted all the deadzone defining
	 *             from JoystickAxis
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
	 * Gets the gain multiplier
	 * 
	 * @return
	 */
	public double getGain()
	{
		return m_gain;
	}
}
