package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.Timer;

/**
 * This class is used to ensure that we only pass the drive frame a new value
 * every so often so we don't set our motors sporatically. (It prevents
 * brown-outs from overexcited drivers)
 */
public class JoystickRateLimiter
{
	Point2D target;
	Point2D current;
	double deadzone = 0.1;
	
	public double m_dampening = 1.7;
	private Timer timer;
	
	private double m_lastDirection;
	
	public JoystickRateLimiter()
	{
		current = new Point2D(0.0, 0.0);
		target = new Point2D(0.0, 0.0);
		timer = new Timer();
		timer.reset();
		timer.start();
	}
	
	public void update()
	{
		double maxDeltaMagnitude = m_dampening * timer.get();
		// if magnitude is too large
		double magnitude = distance(current.getX(), current.getY(), target.getX(), target.getY());
		if (magnitude > maxDeltaMagnitude)
		{
			double currentX = linearMap(maxDeltaMagnitude, 0, magnitude, current.getX(), target.getX());
			double currentY = linearMap(maxDeltaMagnitude, 0, magnitude, current.getY(), target.getY());
			current = new Point2D(currentX, currentY);
		}
		// otherwise it's the same
		else
		{
			current = new Point2D(target.getX(), target.getY());
		}
		timer.reset();
	}
	
	public void set(double x, double y)
	{
		target = new Point2D(x, y);
	}
	
	public Point2D get()
	{
		double currentX = current.getX();
		double currentY = current.getY();
		if (Math.abs(currentX) < deadzone)
		{
			currentX = 0;
		}
		if (Math.abs(currentY) < deadzone)
		{
			currentY = 0;
		}
		return new Point2D(currentX, currentY);
	}
	
	public void reset()
	{
		current = new Point2D(0.0, 0.0);
		target = new Point2D(0.0, 0.0);
	}
	
	/**
	 * Maps a value in range A to its value in range B
	 * 
	 * For example, mapping 4 in range 3 to 5 to the range 6 to 10 yields 8,
	 * since both are right in the middle.
	 * 
	 * @param value
	 * @param rangeAMinimum
	 * @param rangeAMaximum
	 * @param rangeBMinimum
	 * @param rangeBMaximum
	 * @return The value's equivalent in range B
	 */
	private double linearMap(double value, double rangeAMinimum, double rangeAMaximum, double rangeBMinimum, double rangeBMaximum)
	{
		return (((value - rangeAMinimum) * ((rangeBMaximum - rangeBMinimum) / (rangeAMaximum - rangeAMinimum))) + rangeBMinimum);
	}
	
	/**
	 * Calculates the hypotenuse of the triangle created by the x and y axis on
	 * the controller using super secret trigometry techniques.
	 * 
	 * @return The hypotenuse of the joystick
	 */
	public double getMagnitude()
	{
		double x = get().getX();
		double y = get().getY();
		return distance(0, 0, x, y);
	}
	
	public double distance(double x1, double y1, double x2, double y2)
	{
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}
	
	/**
	 * Calculates the angle of the joystick using two axis and trigonometry
	 * 
	 * @return The angle the joystick is facing
	 */
	public double getDirection()
	{
		double x = get().getX();
		double y = get().getY();
		if (x != 0 || y != 0)
		{
			double angle = -(Math.toDegrees(Math.atan2(y, x)) + 90.0);
			m_lastDirection = angle;
			return angle;
		}
		return m_lastDirection;
	}
	
	public class Point2D
	{
		double x;
		double y;
		
		public Point2D(double x, double y)
		{
			this.x = x;
			this.y = y;
		}
		
		public double getX()
		{
			return x;
		}
		
		public double getY()
		{
			return y;
		}
	}
}
