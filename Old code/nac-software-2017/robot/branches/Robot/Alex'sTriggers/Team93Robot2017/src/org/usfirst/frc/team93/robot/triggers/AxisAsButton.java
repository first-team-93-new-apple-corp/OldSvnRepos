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
	private static GenericHID m_joystick;
	private static int m_axis;
	private static double m_threshold;
	
	/**
	 * Create a joystick axis as a button for triggering commands.
	 *
	 * @param joystick The GenericHID object that has the axis (e.g. Joystick,
	 * KinectStick, etc)
	 * @param axis The axis number (see {@link GenericHID#getRawAxis(int) }
	 */
	public AxisAsButton(GenericHID joystick, int axis)
	{
		m_joystick = joystick;
		m_axis = axis;
		m_threshold = 0.8;
	}
	
	/**
	 * Create a joystick axis as a button for triggering commands.
	 *
	 * @param joystick The GenericHID object that has the axis (e.g. Joystick,
	 * KinectStick, etc)
	 * @param axis The axis number (see {@link GenericHID#getRawAxis(int) }
	 * 
	 * @param threshold The threshold at which to activate the trigger
	 */
	public AxisAsButton(GenericHID joystick, int axis, double threshold)
	{
		m_joystick = joystick;
		m_axis = axis;
		m_threshold = threshold;
	}
	
	@Override
	public boolean get()
	{
		return m_joystick.getRawAxis(m_axis) > m_threshold;
	}
	
}
