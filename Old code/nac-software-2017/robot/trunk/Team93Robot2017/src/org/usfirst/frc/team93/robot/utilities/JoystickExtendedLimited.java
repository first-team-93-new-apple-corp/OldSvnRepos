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
 *         Basically, it returns the hypotenuse of the x and y axis values so
 *         that we can set our drive wheels properly
 */
public class JoystickExtendedLimited
{
	Joystick m_joystick;
	int m_axisX;
	int m_axisY;
	
	public JoystickAxis m_x;
	public JoystickAxis m_y;
	
	public JoystickLimitedMagnitude m_magnitude;
	public JoystickLimitedDirection m_direction;
	
	public double m_deadzone;
	
	public JoystickRateLimiter limiter;
	
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
	public JoystickExtendedLimited(Joystick joystick, int x, int y)
	{
		m_joystick = joystick;
		m_axisX = x;
		m_axisY = y;
		
		m_x = new JoystickAxis(m_joystick, m_axisX);
		m_y = new JoystickAxis(m_joystick, m_axisY);
		
		m_magnitude = new JoystickLimitedMagnitude();
		m_direction = new JoystickLimitedDirection();
		
		// deadzone is set to 0.2 by default
		m_deadzone = 0.1;
		m_x.setDeadzone(m_deadzone);
		m_y.setDeadzone(m_deadzone);
		limiter = new JoystickRateLimiter();
		limiter.reset();
	}
	
	/**
	 * Set the deadzone size of the joystick.
	 * 
	 * @param deadzone
	 *            the smallest value the joystick can be at before we round it
	 *            to zero. (prevents doing unnecessary work)
	 */
	public void setDeadzone(double deadzone)
	{
		m_deadzone = deadzone;
		limiter.deadzone = deadzone;
	}
	
	/**
	 * The PIDSource for the x of the joystick position.
	 */
	public JoystickAxis x()
	{
		return m_x;
	}
	
	/**
	 * The PIDSource for the y of the joystick position.
	 */
	public JoystickAxis y()
	{
		return m_y;
	}
	
	/**
	 * The PIDSource for the magnitude of the joystick position.
	 */
	public JoystickLimitedMagnitude magnitude()
	{
		return m_magnitude;
	}
	
	/**
	 * @codereview josh.hawbaker 3.21.17 I'm not a fan of these getters that go
	 *             on to call other getters.
	 */
	public double getMagnitude()
	{
		update();
		return limiter.getMagnitude();
	}
	
	public double getDirection()
	{
		update();
		return limiter.getDirection();
	}
	
	public void update()
	{
		limiter.set(x().get(), y().get());
		limiter.update();
	}
	
	/**
	 * The PIDSource for the joystick direction in degrees.
	 */
	public JoystickLimitedDirection direction()
	{
		return m_direction;
	}
	
	/**
	 * Gets the hypotenuse of the x and y axis. This is used to set our drive
	 * speed
	 */
	public class JoystickLimitedMagnitude
	{
		public double get()
		{
			return getMagnitude();
		}
	}
	
	/**
	 * Gets the direction the joystick is facing. This is used to make sure our
	 * crab wheels are facing the right direction.
	 */
	public class JoystickLimitedDirection
	{
		public double get()
		{
			return getDirection();
		}
		
		public void reset()
		{
			limiter.reset();
		}
	}
}
