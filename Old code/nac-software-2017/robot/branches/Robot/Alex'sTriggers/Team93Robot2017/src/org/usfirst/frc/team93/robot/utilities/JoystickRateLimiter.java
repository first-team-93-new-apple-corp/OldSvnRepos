package org.usfirst.frc.team93.robot.utilities;

public class JoystickRateLimiter
{
	Point2D target;
	Point2D current;
	double deadzone = 0.1;
	
	double maxDeltaMagnitude = 0.05;
	
	private double m_lastDirection;
	
	public JoystickRateLimiter()
	{
		current = new Point2D(0.0, 0.0);
		target = new Point2D(0.0, 0.0);
	}
	
	public void update()
	{
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
	
	public double getDirection()
	{
		double x = get().getX();
		double y = get().getY();
		if (x != 0 || y != 0)
		{
			double angle = Math.toDegrees(Math.atan2(y, x)) + 90.0;
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
