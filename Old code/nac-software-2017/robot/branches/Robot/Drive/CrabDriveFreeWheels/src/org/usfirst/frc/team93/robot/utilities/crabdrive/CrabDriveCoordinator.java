package org.usfirst.frc.team93.robot.utilities.crabdrive;

import java.util.ArrayList;
import java.util.HashMap;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;
import org.usfirst.frc.team93.robot.subsystems.SPIEncoders;
import org.usfirst.frc.team93.robot.utilities.PIDOutputExtended;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * CrabDriveCoordinator
 * 
 * @author Evans Chen
 * 
 * This class coordinates the CrabDriveModules. If the joystick is translating
 * and not turning, translate as normal. If the joystick is turning and
 * translating, use only AngleAdjuster to perform a tank turn. If the joystick
 * is turning but stationary, do a crab turn.
 * 
 * For these three actions, the 4 drive motors and the 4 direction motors must
 * be coordinated.
 * 
 * To use this class, use the request(speed, direction, yaw) command. This
 * requests the Coordinator to move the robot with a certain speed, a certain
 * direction, and move to a certain yaw.
 * 
 * The CrabDriveContinuous command writes to this command. This class writes to
 * the 4 drive motors and the 4 direction motors.
 */
public class CrabDriveCoordinator
{
	
	/**
	 * Robot centric: Wheels face a direction relative to the robot
	 * 
	 * Field centric: Wheels face a direction relative to the field, regardless
	 * of robot's angle
	 */
	public enum CrabDriveMode
	{
		RobotCentric, FieldCentric;
	}
	
	/**
	 * Tank Mode: Robot turns left by turning left wheel backward and right
	 * wheel forward.
	 * 
	 * Crab Mode: Robot turns left by rotating all wheels at a slant.
	 * 
	 * Steer Mode: Robot turns left by keeping the back wheels straight, but
	 * turning the front wheels to the left.
	 */
	public enum CrabTurnMode
	{
		Tank, Steer, Crab;
	}
	
	// the crab drive mode to use
	public CrabDriveMode m_mode;
	
	// the turning mode to use
	public CrabTurnMode m_turnMode;
	
	// Yaw PIDController
	PIDController m_yawController;
	
	// Angle Adjuster
	CrabDriveWheelSpeedCoordinator m_angleAdjuster;
	
	// the ports
	public enum Port
	{
		LEFT_FRONT, LEFT_BACK, RIGHT_FRONT, RIGHT_BACK;
	}
	
	// The HashMap containing the wheel direction controllling motors.
	public HashMap<Port, CrabDriveDirectionMotor> m_directions;
	
	// The HashMap containing the Crab Rotate (Stationary) offsets.
	HashMap<Port, Double> m_crabRotateOffsets;
	
	// the list containing the front and back motors for turn modes.
	public ArrayList<CrabDriveDirectionMotor> m_front;
	public ArrayList<CrabDriveDirectionMotor> m_back;
	
	// lock object for synchronization
	public Object lock;
	
	// direction enum
	enum CardinalDirection
	{
		FRONT(0), LEFT(90), BACK(180), RIGHT(270);
		
		private final int m_angle;
		
		CardinalDirection(final int angle)
		{
			m_angle = angle;
		}
		
		public int get()
		{
			return m_angle;
		}
	}
	
	public CrabDriveCoordinatorReceiver speedReceiver;
	public CrabDriveCoordinatorReceiver directionReceiver;
	public CrabDriveCoordinatorReceiver yawReceiver;
	
	// whether or not the CrabDriveCoordinator is enabled
	public boolean m_enabled = false;
	
	/**
	 * Constructor for the CrabDriveCoordinator
	 * 
	 * @param P P constant for the CrabDriveWheelSpeedCoordinator PID
	 * @param I I constant for the CrabDriveWheelSpeedCoordinator PID
	 * @param D D constant for the CrabDriveWheelSpeedCoordinator PID
	 * @param gyro The Gyro PID Source, giving the robot's yaw.
	 * @param leftFrontDrive The left front drive motor.
	 * @param leftBackDrive The left back drive motor.
	 * @param rightFrontDrive The right front drive motor.
	 * @param rightBackDrive The right back drive motor.
	 * @param leftFrontSteering The left front CrabDriveDirectionMotor.
	 * @param leftBackSteering The left back CrabDriveDirectionMotor.
	 * @param rightFrontSteering The right front CrabDriveDirectionMotor.
	 * @param rightBackSteeringThe right back CrabDriveDirectionMotor.
	 */
	public CrabDriveCoordinator(double P, double I, double D, PIDSource gyro, PIDOutputExtended leftFrontDrive,
			PIDOutputExtended leftBackDrive, PIDOutputExtended rightFrontDrive, PIDOutputExtended rightBackDrive,
			CrabDriveDirectionMotor leftFrontSteering, CrabDriveDirectionMotor leftBackSteering,
			CrabDriveDirectionMotor rightFrontSteering, CrabDriveDirectionMotor rightBackSteering)
	{
		// initialize the PIDOutputs that input into the coordinator
		speedReceiver = new CrabDriveCoordinatorReceiver();
		directionReceiver = new CrabDriveCoordinatorReceiver();
		yawReceiver = new CrabDriveCoordinatorReceiver();
		
		// initialize the synchronization lock
		lock = new Object();
		
		// initialize hash maps before they are called
		m_directions = new HashMap<Port, CrabDriveDirectionMotor>();
		m_crabRotateOffsets = new HashMap<Port, Double>();
		
		// map all CrabDriveWheelModules to ports.
		m_directions.put(Port.LEFT_FRONT, leftFrontSteering);
		m_directions.put(Port.LEFT_BACK, leftBackSteering);
		m_directions.put(Port.RIGHT_FRONT, rightFrontSteering);
		m_directions.put(Port.RIGHT_BACK, rightBackSteering);
		
		// define crab rotate offsets for stationary rotation
		m_crabRotateOffsets.put(Port.LEFT_FRONT, -45.0);
		m_crabRotateOffsets.put(Port.LEFT_BACK, 45.0);
		m_crabRotateOffsets.put(Port.RIGHT_FRONT, 45.0);
		m_crabRotateOffsets.put(Port.RIGHT_BACK, -45.0);
		
		// default to robot centric mode
		m_mode = CrabDriveMode.FieldCentric;
		// default to tank turn mode for now
		m_turnMode = CrabTurnMode.Crab;
		// init the angle adjuster
		m_angleAdjuster = new CrabDriveWheelSpeedCoordinator(leftFrontDrive, leftBackDrive, rightFrontDrive, rightBackDrive);
		// output to the angle adjuster from the PIDController
		m_yawController = new PIDController(P, I, D, gyro, m_angleAdjuster.yawReceiver);
		
		// for steering modes, initialize front and back lists
		m_front = new ArrayList<CrabDriveDirectionMotor>();
		m_back = new ArrayList<CrabDriveDirectionMotor>();
	}
	
	/**
	 * This method requests a speed, direction, and yaw from the
	 * CrabDriveCoordinator. DO NOT USE THIS WHILE USING A PIDCONTROLLER! This
	 * method is designed to be a "set once" kind of method.
	 * 
	 * @param speed
	 * @param direction
	 * @param yaw
	 */
	public void request(double speed, double direction, double yaw)
	{
		speedReceiver.set(speed);
		directionReceiver.set(direction);
		yawReceiver.set(yaw);
	}
	
	/**
	 * request a speed, direction, and yaw. Called by a command. This class will
	 * then calculate and command the proper wheel angles and speeds.
	 * 
	 * @param speed
	 * @param direction
	 * @param yaw
	 */
	private void update(double speed, double direction, double yaw)
	{
		// if we are not enabled, do not do anything here
		if (!m_enabled)
		{
			return;
		}
		
		// If the SPI encoders aren't ready, then don't do anything
		if (Robot.isReal())
		{
			if (!SPIEncoders.ready())
			{
				return;
			}
		}
		
		// update requested angle
		// whether or not to flip speed based on what is facing forward
		speed = speed * Drive.getOrientation();
		
		// get setpoint from joysticks
		// find closest angle from gyro to request
		double robotAngle = closestAngle(Drive.GYRO.get(), yaw);
		
		// the direction the wheels should face relative to the robot
		double wheelDirection = direction;
		// set Translation PID setpoints based on mode
		if (m_mode == CrabDriveMode.RobotCentric)
		{
			/**
			 * Robot Centric: Wheels are set relative to robot, no offset needed
			 */
			wheelDirection = direction;
		}
		else if (m_mode == CrabDriveMode.FieldCentric)
		{
			/**
			 * Field Centric: Wheels are set relative to field by subtracting
			 * robot angle (GYRO is negative, so add)
			 */
			wheelDirection = direction + Drive.GYRO.get();
		}
		// let the angle adjuster know which direction the wheels are pointing
		// left and right
		m_angleAdjuster.setDirection(wheelDirection);
		// update which wheels are front and back
		setDirection(wheelDirection);
		
		// if robot DOES NOT need to rotate
		// OR if robot is trying to rotate while translating
		// use a turn mode.
		boolean translating = (Math.abs(robotAngle) < 5) || (Math.abs(speed) > 0);
		if (translating)
		{
			switch (m_turnMode)
			{
			case Tank:
				requestTank(speed, wheelDirection, robotAngle);
				break;
			case Steer:
				requestSteer(speed, wheelDirection, robotAngle);
				break;
			case Crab:
				requestCrab(speed, wheelDirection, robotAngle);
				break;
			}
		}
		// if robot DOES need to rotate AND it is stationary, use pivot turn.
		else
		{
			// if the yaw controller isn't enabled
			if (!m_yawController.isEnabled())
			{
				// enable it since we use it
				m_yawController.enable();
			}
			
			// do a crab rotate
			// Go the other way if clockwise rotation.
			// set wheels to their crab rotate positions
			for (Port port : Port.values())
			{
				m_directions.get(port).set(m_crabRotateOffsets.get(port));
			}
			// set to normal left and right motors setting
			m_angleAdjuster.setDirection(0);
			// Note that the wheels are set to 0 because m_angleAdjuster moves
			// the wheels.
			m_yawController.setSetpoint(Drive.GYRO.get() + robotAngle);
		}
	}
	
	/**
	 * Updates from the receivers
	 */
	private void update()
	{
		update(speedReceiver.get(), directionReceiver.get(), yawReceiver.get());
	}
	
	/**
	 * @author Evans Chen Put this class as the PIDOutput to a PIDController,
	 * whether yaw for robot gyro or distance for robot speed
	 */
	public class CrabDriveCoordinatorReceiver implements PIDSource, PIDOutput
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
	 * requests speed, relative direction, and yaw deviation while moving using
	 * tank turn mode.
	 * 
	 * @param speed
	 * @param direction Relative, robot-centric target wheel direction
	 * @param yawDeviation The yaw deviation from the target gyro angle.
	 */
	private void requestTank(double speed, double direction, double yawDeviation)
	{
		// if the yaw controller isn't enabled
		if (!m_yawController.isEnabled())
		{
			// enable it since we use it
			m_yawController.enable();
		}
		
		// set the yaw controller setpoint
		m_yawController.setSetpoint(Drive.GYRO.get() + yawDeviation);
		
		// set the wheels to the right direction
		set(speed, direction);
	}
	
	/**
	 * requests speed, relative direction, and yaw deviation while moving using
	 * steer turn mode.
	 * 
	 * @param speed
	 * @param direction Relative, robot-centric target wheel direction
	 * @param yaw The yaw deviation from the target gyro angle.
	 */
	private void requestSteer(double speed, double direction, double yawDeviation)
	{
		// if the yaw controller is enabled
		if (m_yawController.isEnabled())
		{
			// disable it since we don't use it
			m_yawController.disable();
			// no tank yaw control
			m_angleAdjuster.yawReceiver.set(0.0);
		}
		
		// use front and back motors
		// can't happen while we're writing to the front and back lists, so it
		// is synchronized
		// even though a concurrency error can never happen here I want to be
		// safe
		synchronized (lock)
		{
			// front motors are set to steer
			for (CrabDriveDirectionMotor wheel : m_front)
			{
				wheel.set(direction - calculateSteer(speed, yawDeviation));
			}
			// back motors are set straight
			for (CrabDriveDirectionMotor wheel : m_back)
			{
				wheel.set(direction);
			}
		}
		// set speed of wheels
		m_angleAdjuster.speedReceiver.set(speed);
	}
	
	/**
	 * requests speed, relative direction, and yaw deviation while moving using
	 * crab turn mode.
	 * 
	 * @param speed
	 * @param direction Relative, robot-centric target wheel direction
	 * @param yaw The yaw deviation from the target gyro angle.
	 */
	private void requestCrab(double speed, double direction, double yawDeviation)
	{
		// if the yaw controller is enabled
		if (m_yawController.isEnabled())
		{
			// disable it since we don't use it
			m_yawController.disable();
			// no tank yaw control
			m_angleAdjuster.yawReceiver.set(0.0);
		}
		
		// use front and back motors
		// can't happen while we're writing to the front and back lists, so it
		// is synchronized
		// even though a concurrency error can never happen here I want to be
		// safe
		synchronized (lock)
		{
			// front motors are set to steer
			for (CrabDriveDirectionMotor wheel : m_front)
			{
				wheel.set(direction - calculateCrabSteer(speed, yawDeviation));
			}
			// back motors are set straight
			for (CrabDriveDirectionMotor wheel : m_back)
			{
				wheel.set(direction + calculateCrabSteer(speed, yawDeviation));
			}
		}
		// set speed of wheels
		m_angleAdjuster.speedReceiver.set(speed);
	}
	
	/**
	 * Enables the CrabDriveCoordinator and subcomponents.
	 */
	public void enable()
	{
		m_enabled = true;
		m_yawController.enable();
		for (Port port : Port.values())
		{
			m_directions.get(port).enable();
		}
	}
	
	/**
	 * Disables the CrabDriveCoordinator and subcomponents.
	 */
	public void disable()
	{
		m_enabled = false;
		m_yawController.disable();
		set(0, 0);
		for (Port port : Port.values())
		{
			m_directions.get(port).disable();
		}
		speedReceiver.set(0);
		directionReceiver.set(0);
		yawReceiver.set(0);
	}
	
	/**
	 * Sets the CrabDriveCoordinator's subcomponents.
	 * 
	 * @param speed
	 * @param angle
	 */
	private void set(double speed, double angle)
	{
		// sets the speed
		m_angleAdjuster.speedReceiver.set(speed);
		// sets the wheel angles
		for (Port port : Port.values())
		{
			m_directions.get(port).set(angle);
		}
	}
	
	/**
	 * Get the closest angle between the given angles.
	 * 
	 * @param a
	 * @param b
	 * @return
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
	 * @param a
	 * @param b
	 * @return
	 */
	private double modulo(double a, double b)
	{
		return (a < 0 ? b + (a % b) : a % b);
	}
	
	/**
	 * Given a deviation from the desired yaw setpoint from the current yaw, for
	 * CrabTurnMode Crab, calculate how much to turn the wheels.
	 * 
	 * Note that turning them 90 degrees is just the same as a tank turn, and
	 * will stop translation. Also note that turning over 90 degrees will cause
	 * the robot to start moving backwards.
	 * 
	 * Hence, the method avoids those values, and only goes between 0 and 45.
	 * 
	 * @param speed The robot's speed
	 * @param angle The robot's deviation from the desired yaw
	 * @return The angle to turn the wheels outward when turning.
	 */
	private double calculateCrabSteer(double speed, double angle)
	{
		return calculateSteer(speed, angle) * 1.0;
	}
	
	/**
	 * Given a deviation from the desired yaw setpoint from the current yaw, for
	 * CrabTurnMode Steer, calculate how much to turn the front wheels.
	 * 
	 * @param speed
	 * @param angle
	 * @return
	 */
	private double calculateSteer(double speed, double angle)
	{
		// math to get the amount to change the wheel direction.
		double target = linearMap(limit(angle * (0.5 / speed), 90.0), -90.0, 90.0, -45.0, 45.0);
		return target;
	}
	
	/**
	 * Gets whether the yaw controller is on target
	 * 
	 * @return
	 */
	public boolean yawIsOnTarget()
	{
		return m_yawController.onTarget();
	}
	
	/**
	 * Set the crab drive mode to robot centric or field centric.
	 * 
	 * @param mode
	 */
	public void setMode(CrabDriveMode mode)
	{
		m_mode = mode;
	}
	
	/**
	 * Returns the current crab drive mode setting.
	 * 
	 * @return
	 */
	public CrabDriveMode getMode()
	{
		return m_mode;
	}
	
	/**
	 * Limits a number so that its absolute value is under the limit.
	 * 
	 * @param value The number to limit.
	 * @param limit The maximum absolute value limit.
	 * @return The limited value.
	 */
	private double limit(double value, double limit)
	{
		double val = Math.abs(value);
		if (Math.abs(value) > limit)
		{
			val = limit;
		}
		return val * Math.signum(value);
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
	 * Calculates which wheels are in front and back, given a translation
	 * request direction.
	 */
	private void setDirection(double dir)
	{
		// convert direction to cardinal direction, by finding closest
		CardinalDirection closest = CardinalDirection.FRONT;
		// nothing can be farther from the target angle than 360
		double minimum = 360.0;
		// finds the minimum angular distance from the currently requested dir
		for (CardinalDirection cardinalDirection : CardinalDirection.values())
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
		
		// This block writes to the m_front and m_back lists.
		// This cannot run while we are accessing those lists in request()
		// even though a concurrency error can never happen here I want to be
		// safe
		synchronized (lock)
		{
			m_front.clear();
			m_back.clear();
			
			switch (closest)
			{
			// if we are driving forward then the front wheels are in the front
			// back wheels are in the back
			case FRONT:
				m_front.add(m_directions.get(Port.LEFT_FRONT));
				m_front.add(m_directions.get(Port.RIGHT_FRONT));
				m_back.add(m_directions.get(Port.LEFT_BACK));
				m_back.add(m_directions.get(Port.RIGHT_BACK));
				break;
			// if we are driving left then the left wheels are in the front
			// right wheels are in the back
			case LEFT:
				m_front.add(m_directions.get(Port.LEFT_FRONT));
				m_front.add(m_directions.get(Port.LEFT_BACK));
				m_back.add(m_directions.get(Port.RIGHT_FRONT));
				m_back.add(m_directions.get(Port.RIGHT_BACK));
				break;
			// if we are driving back then the back wheels are in the front
			// front wheels are in the back
			case BACK:
				m_front.add(m_directions.get(Port.LEFT_BACK));
				m_front.add(m_directions.get(Port.RIGHT_BACK));
				m_back.add(m_directions.get(Port.LEFT_FRONT));
				m_back.add(m_directions.get(Port.RIGHT_FRONT));
				break;
			// if we are driving right, then the right wheels ar ein front
			// left wheels are in back
			case RIGHT:
				m_front.add(m_directions.get(Port.RIGHT_FRONT));
				m_front.add(m_directions.get(Port.RIGHT_BACK));
				m_back.add(m_directions.get(Port.LEFT_FRONT));
				m_back.add(m_directions.get(Port.LEFT_BACK));
				break;
			default:
				break;
			}
		}
	}
}