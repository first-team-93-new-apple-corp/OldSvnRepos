package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.Joystick;

/**
 * JoystickExtended
 * 
 * @author Evans Chen
 * 
 *         This class takes input from a joystick and converts it into 2
 *         PIDSources: A direction PIDSource, which measures what angle the
 *         joystick is at A magnitude PIDSource, which measures how far the
 *         joystick is from its startpoint x and y PIDSources are provided as
 *         well.
 * 
 *         Note: This only takes a SINGLE joystick, and its x and y axis, NOT an
 *         entire controller, as Joystick usually means.
 * 
 *         You can use them as normal PIDSources, or use them using get.
 *         Example: x.get() or x.pidGet()
 */
public class JoystickExtended
{
	Joystick m_joystick;
	int m_axisX;
	int m_axisY;
	
	public JoystickAxis m_x;
	public JoystickAxis m_y;
	
	public JoystickMagnitude m_magnitude;
	public JoystickDirection m_direction;
	
	public double m_deadzone;
	
	/**
	 * Constructor for the JoystickExtended.
	 * 
	 * @param joystick
	 *            The joystick.
	 * @param x
	 *            The x axis.
	 * @param y
	 *            The y axis.
	 */
	public JoystickExtended(Joystick joystick, int x, int y)
	{
		m_joystick = joystick;
		m_axisX = x;
		m_axisY = y;
		
		m_x = new JoystickAxis(m_joystick, m_axisX);
		m_y = new JoystickAxis(m_joystick, m_axisY);
		
		m_magnitude = new JoystickMagnitude(m_x, m_y);
		m_direction = new JoystickDirection(m_x, m_y);
		
		// deadzone is set to 0.2 by default
		m_deadzone = 0.1;
		m_x.setDeadzone(m_deadzone);
		m_y.setDeadzone(m_deadzone);
	}
	
	/**
	 * Set the deadzone size of the joystick.
	 * 
	 * @param deadzone
	 */
	public void setDeadzone(double deadzone)
	{
		m_deadzone = deadzone;
	}
	
	/**
	 * The PIDSource for the x of the joystick position.
	 * 
	 * @return
	 */
	public JoystickAxis x()
	{
		return m_x;
	}
	
	/**
	 * The PIDSource for the y of the joystick position.
	 * 
	 * @return
	 */
	public JoystickAxis y()
	{
		return m_y;
	}
	
	/**
	 * The PIDSource for the magnitude of the joystick position.
	 * 
	 * @return
	 */
	public JoystickMagnitude magnitude()
	{
		return m_magnitude;
	}
	
	/**
	 * The PIDSource for the joystick direction in degrees.
	 * 
	 * @return
	 */
	public JoystickDirection direction()
	{
		return m_direction;
	}
}
