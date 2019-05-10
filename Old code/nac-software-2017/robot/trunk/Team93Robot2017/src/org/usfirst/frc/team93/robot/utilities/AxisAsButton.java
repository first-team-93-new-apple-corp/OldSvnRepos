package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * A {@link Button} that gets its state from a {@link GenericHID}.
 *
 * @author alex.stevenson
 *
 */
public class AxisAsButton extends Button
{
	private final GenericHID m_joystick;
	private final int m_axis;
	private final double m_threshold;
	private final boolean m_isInverted;
	
	/**
	 * Create a joystick axis as a button for triggering commands using the
	 * default threshold of 0.8.
	 *
	 * @param joystick The GenericHID object that has the axis (e.g. Joystick,
	 * KinectStick, etc)
	 * @param axis The axis number (see {@link GenericHID#getRawAxis(int) }
	 * 
	 * @param inverted Whether or not to invert the reading
	 */
	public AxisAsButton(GenericHID joystick, int axis, boolean inverted)
	{
		m_joystick = joystick;
		m_axis = axis;
		m_isInverted = inverted;
		if (inverted)
			m_threshold = 0.8;
		else
			m_threshold = 0.2;
	}
	
	/**
	 * Create a joystick axis as a button for triggering commands using a custom
	 * threshold.
	 *
	 * @param joystick The GenericHID object that has the axis (e.g. Joystick,
	 * KinectStick, etc)
	 * @param axis The axis number (see {@link GenericHID#getRawAxis(int) }
	 * 
	 * @param threshold The threshold at which to activate the trigger
	 * 
	 * @param inverted Whether or not to invert the reading
	 */
	public AxisAsButton(GenericHID joystick, int axis, double threshold, boolean inverted)
	{
		m_joystick = joystick;
		m_axis = axis;
		m_isInverted = inverted;
		if (inverted)
			threshold = 1 - threshold;
		m_threshold = threshold;
	}
	
	@Override
	public boolean get()
	{
		if (m_isInverted)
			return m_joystick.getRawAxis(m_axis) < m_threshold;
		else
			return m_joystick.getRawAxis(m_axis) > m_threshold;
	}
	
}
