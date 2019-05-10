package org.usfirst.frc.team93.robot.triggers;

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
	private final GenericHID m_iJoystick;
	private final int m_iAxis;
	private final double m_dThreshold;
	private final boolean m_bIsInverted;
	
	/**
	 * Create a joystick axis as a button for triggering commands using the
	 * default threshold of 0.8.
	 *
	 * @param joystick
	 *            The GenericHID object that has the axis (e.g. Joystick,
	 *            KinectStick, etc)
	 * @param iAxis
	 *            The axis number (see {@link GenericHID#getRawAxis(int) }
	 * 
	 * @param bInverted
	 *            Whether or not to invert the reading
	 */
	public AxisAsButton(GenericHID joystick, int iAxis, boolean bInverted)
	{
		m_iJoystick = joystick;
		m_iAxis = iAxis;
		m_bIsInverted = bInverted;
		if (bInverted)
			m_dThreshold = 0.8;
		else
			m_dThreshold = 0.2;
	}
	
	/**
	 * Create a joystick axis as a button for triggering commands using a custom
	 * threshold.
	 *
	 * @param joystick
	 *            The GenericHID object that has the axis (e.g. Joystick,
	 *            KinectStick, etc)
	 * @param iAxis
	 *            The axis number (see {@link GenericHID#getRawAxis(int) }
	 * 
	 * @param iThreshold
	 *            The threshold at which to activate the trigger
	 * 
	 * @param bInverted
	 *            Whether or not to invert the reading
	 */
	public AxisAsButton(GenericHID joystick, int iAxis, double iThreshold, boolean bInverted)
	{
		m_iJoystick = joystick;
		m_iAxis = iAxis;
		m_bIsInverted = bInverted;
		if (bInverted)
			iThreshold = 1 - iThreshold;
		m_dThreshold = iThreshold;
	}
	
	@Override
	public boolean get()
	{
		if (m_bIsInverted)
			return m_iJoystick.getRawAxis(m_iAxis) < m_dThreshold;
		else
			return m_iJoystick.getRawAxis(m_iAxis) > m_dThreshold;
	}
	
}
