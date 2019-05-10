package org.usfirst.frc.team93.robot.utilities.crabdrive;

import java.util.ArrayList;
import java.util.HashMap;

import org.usfirst.frc.team93.robot.utilities.PIDOutputExtended;
import org.usfirst.frc.team93.robot.utilities.crabdrive.CrabDriveCoordinator.Port;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * This class is used to control how fast we are driving and hold a yaw /
 * heading setpoint
 */
public class CrabDriveWheelSpeedCoordinator
{
	
	CrabDriveWheelSpeedReceiver speedReceiver;
	CrabDriveWheelSpeedReceiver yawReceiver;
	
	public HashMap<Port, PIDOutputExtended> m_motors;
	public ArrayList<PIDOutputExtended> m_left;
	public ArrayList<PIDOutputExtended> m_right;
	
	// lock object for synchronization
	public Object lock;
	
	// direction enum
	enum PrimaryDirection
	{
		FRONT(0), FRONT_LEFT(45), LEFT(90), BACK_LEFT(135), BACK(180), BACK_RIGHT(225), RIGHT(270), FRONT_RIGHT(315);
		
		private final int m_angle;
		
		PrimaryDirection(final int angle)
		{
			m_angle = angle;
		}
		
		public int get()
		{
			return m_angle;
		}
	}
	
	/**
	 * Constructor for the CrabDriveWheelSpeedCoordinator class. All of these
	 * are the pins of the corresponding motor
	 * 
	 * @param leftFront
	 * @param leftBack
	 * @param rightFront
	 * @param rightBack
	 */
	public CrabDriveWheelSpeedCoordinator(PIDOutputExtended leftFront, PIDOutputExtended leftBack,
			PIDOutputExtended rightFront, PIDOutputExtended rightBack)
	{
		// initializes the lock
		lock = new Object();
		
		// initializes the receiver PIDOutputs
		yawReceiver = new CrabDriveWheelSpeedReceiver();
		speedReceiver = new CrabDriveWheelSpeedReceiver();
		
		// initializes the map of port to motor
		m_motors = new HashMap<Port, PIDOutputExtended>();
		
		// populates map of motors
		m_motors.put(Port.LEFT_FRONT, leftFront);
		m_motors.put(Port.LEFT_BACK, leftBack);
		m_motors.put(Port.RIGHT_FRONT, rightFront);
		m_motors.put(Port.RIGHT_BACK, rightBack);
		
		// initializes the left and right lists of motors
		m_left = new ArrayList<PIDOutputExtended>();
		m_right = new ArrayList<PIDOutputExtended>();
	}
	
	/**
	 * @author Evans Chen Put this class as the PIDOutput to a PIDController,
	 * whether yaw for robot gyro or distance for robot speed
	 */
	public class CrabDriveWheelSpeedReceiver implements PIDSource, PIDOutput
	{
		public double value = 0.0;
		
		@Override
		public void pidWrite(double output)
		{
			value = output;
			update();
		}
		
		public void set(double value)
		{
			pidWrite(value);
		}
		
		@Override
		public void setPIDSourceType(PIDSourceType pidSource)
		{
		}
		
		@Override
		public PIDSourceType getPIDSourceType()
		{
			return PIDSourceType.kDisplacement;
		}
		
		@Override
		public double pidGet()
		{
			return value;
		}
		
		public double get()
		{
			return pidGet();
		}
	}
	
	/**
	 * Takes the requested speed and yaw values and writes to the motors
	 */
	private void update()
	{
		for (Port port : Port.values())
		{
			double speed = speedReceiver.get();
			double yaw = yawReceiver.get();
			PIDOutputExtended motor = m_motors.get(port);
			
			// This block accesses the m_left and m_right lists.
			// This cannot run while we are modifying those lists in
			// setDirection(double dir)
			synchronized (lock)
			{
				// if motor is on the left of the robot, subtract power to turn
				if (m_left.contains(motor))
				{
					speed -= yaw;
				}
				// if motor is on the right of the robot, add power to turn
				if (m_right.contains(motor))
				{
					speed += yaw;
				}
			}
			// set motor to the correct speed
			motor.set(speed);
		}
	}
	
	/**
	 * Uses the requested direction to determine which wheels are the right and
	 * left.
	 * 
	 * @param dir The requested yaw direction (in degrees)
	 */
	public void setDirection(double dir)
	{
		// convert direction to cardinal direction, by finding closest
		PrimaryDirection closest = PrimaryDirection.FRONT;
		// nothing can be farther from the target angle than 360
		double minimum = 360.0;
		// finds the minimum angular distance from the currently requested dir
		for (PrimaryDirection cardinalDirection : PrimaryDirection.values())
		{
			// if the new one is closer
			double dist = Math.abs(closestAngle(dir, cardinalDirection.get()));
			if (dist < minimum)
			{
				// this is the new minimum
				closest = cardinalDirection;
				minimum = dist;
			}
		}
		
		// This block writes to the m_left and m_right lists.
		// This cannot run while we are accessing those lists in update()
		synchronized (lock)
		{
			m_left.clear();
			m_right.clear();
			
			// use switch case to determine which wheels are right and left,
			// given a cardinal direction that the robot is facing
			switch (closest)
			{
			// When the robot is moving forward,
			// Right is right and left is left
			case FRONT:
				m_left.add(m_motors.get(Port.LEFT_FRONT));
				m_left.add(m_motors.get(Port.LEFT_BACK));
				m_right.add(m_motors.get(Port.RIGHT_FRONT));
				m_right.add(m_motors.get(Port.RIGHT_BACK));
				break;
			// When the robot is moving forward and left
			// only the left back wheel is left
			// only the right front wheel is right
			case FRONT_LEFT:
				m_left.add(m_motors.get(Port.LEFT_BACK));
				m_right.add(m_motors.get(Port.RIGHT_FRONT));
				break;
			// When the robot if moving left
			// Front is right and back is left
			case LEFT:
				m_left.add(m_motors.get(Port.LEFT_BACK));
				m_left.add(m_motors.get(Port.RIGHT_BACK));
				m_right.add(m_motors.get(Port.LEFT_FRONT));
				m_right.add(m_motors.get(Port.RIGHT_FRONT));
				break;
			// When the robot is moving backwards and left
			// front left wheel is right
			// back right wheel is left
			case BACK_LEFT:
				m_left.add(m_motors.get(Port.RIGHT_BACK));
				m_right.add(m_motors.get(Port.LEFT_FRONT));
				break;
			// When the robot is going backwards
			// left is right and right is left
			case BACK:
				m_left.add(m_motors.get(Port.RIGHT_FRONT));
				m_left.add(m_motors.get(Port.RIGHT_BACK));
				m_right.add(m_motors.get(Port.LEFT_FRONT));
				m_right.add(m_motors.get(Port.LEFT_BACK));
				break;
			// When the robot is going backwards and right
			// Back left is right and front right is left
			case BACK_RIGHT:
				m_left.add(m_motors.get(Port.RIGHT_FRONT));
				m_right.add(m_motors.get(Port.LEFT_BACK));
				break;
			// when the robot is facing right
			// Back is right and front is left
			case RIGHT:
				m_left.add(m_motors.get(Port.LEFT_FRONT));
				m_left.add(m_motors.get(Port.RIGHT_FRONT));
				m_right.add(m_motors.get(Port.LEFT_BACK));
				m_right.add(m_motors.get(Port.RIGHT_BACK));
				break;
			// When the robot is facing front right
			// Back right is right, front left is left
			case FRONT_RIGHT:
				m_left.add(m_motors.get(Port.LEFT_FRONT));
				m_right.add(m_motors.get(Port.RIGHT_BACK));
				break;
			// default to front setting
			default:
				m_left.add(m_motors.get(Port.LEFT_FRONT));
				m_left.add(m_motors.get(Port.LEFT_BACK));
				m_right.add(m_motors.get(Port.RIGHT_FRONT));
				m_right.add(m_motors.get(Port.RIGHT_BACK));
				break;
			}
		}
	}
	
	/**
	 * Get the closest angle between the given angles.
	 * 
	 * @param a The current heading
	 * @param b The desired heading
	 * @return The calculated difference in angles
	 */
	private double closestAngle(double a, double b)
	{
		// get direction
		double dir = modulo(b, 360.0) - modulo(a, 360.0);
		
		// convert from -360 to 360 to -180 to 180
		if (Math.abs(dir) > 180.0)
		{
			dir = -(Math.signum(dir) * 360.0) + dir;
		}
		return dir;
	}
	
	/**
	 * Modulo that works with negative numbers and always returns a positive
	 * number.
	 * 
	 * @param a The current angle
	 * @param b The desired angle
	 * @return A positive value for a certain angle
	 */
	private double modulo(double a, double b)
	{
		return (a < 0 ? b + (a % b) : a % b);
	}
}
