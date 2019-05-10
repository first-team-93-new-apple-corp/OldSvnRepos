package org.usfirst.frc.team93.robot.utilities.crabdrive;

import java.util.HashMap;

import org.usfirst.frc.team93.robot.subsystems.Drive;
import org.usfirst.frc.team93.robot.utilities.PIDOutputExtended;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;

/**
 * CrabDriveCoordinator
 * @author Evans Chen
 * 
 * This class coordinates the CrabDriveModules.
 * If the joystick is translating and not turning, translate as normal.
 * If the joystick is turning and translating, use only AngleAdjuster to perform a tank turn.
 * If the joystick is turning but stationary, do a crab turn.
 * 
 * For these three actions, the 4 drive motors and the 4 direction motors must be coordinated.
 * 
 * To use this class, use the request(speed, direction, yaw) command.
 * This requests the Coordinator to move the robot with a certain speed, a certain direction, and move to a certain yaw.
 * 
 * The CrabDriveContinuous command writes to this command.
 * This class writes to the 4 drive motors and the 4 direction motors.
 */
public class CrabDriveCoordinator {
	
	public enum CrabDriveMode
	{
		RobotCentric, FieldCentric;
	}
	
	/**
	 * TODO: CrabTurnMode
	 * Tank Mode: Robot turns left by turning left wheel backward and right wheel forward.
	 * Crab Mode: Robot turns left by rotating all wheels outward 10 degrees (while translating) and trying to turn.
	 * Steer Mode: Robot turns left by keeping the back wheels straight, but turning the front wheels to the left.
	 * Car Mode: Similar to Steer Mode, except the faster the robot is going, the less the robot turns the front wheels, like on a car.
	 */
	public enum CrabTurnMode
	{
		Tank, Crab, Steer, Car;
	}
	
	//the crab drive mode to use
	public CrabDriveMode m_mode = CrabDriveMode.RobotCentric;
	
	//Yaw PIDController
	PIDController m_yawController;
	
	//Angle Adjuster
	CrabDriveWheelSpeedCoordinator m_angleAdjuster;
	
	//the ports
	public enum Port
	{
		LEFT_FRONT, LEFT_BACK, RIGHT_FRONT, RIGHT_BACK;
	}
	
	//The HashMap containing the wheel direction controllling motors.
	public HashMap<Port, CrabDriveDirectionMotor> m_directions;
	
	//The HashMap containing the Crab Rotate (Stationary) offsets.
	HashMap<Port, Double> m_crabRotateOffsets;
	
	/**
	 * Constructor for the CrabDriveCoordinator
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
	public CrabDriveCoordinator(double P, double I, double D, PIDSource gyro, PIDOutputExtended leftFrontDrive, PIDOutputExtended leftBackDrive, PIDOutputExtended rightFrontDrive, PIDOutputExtended rightBackDrive, 
			CrabDriveDirectionMotor leftFrontSteering, CrabDriveDirectionMotor leftBackSteering, CrabDriveDirectionMotor rightFrontSteering, CrabDriveDirectionMotor rightBackSteering)
	{
		//initialize hash maps before they are called
		m_directions = new HashMap<Port, CrabDriveDirectionMotor>();
		m_crabRotateOffsets = new HashMap<Port, Double>();
		
		//map all CrabDriveWheelModules to ports.
		m_directions.put(Port.LEFT_FRONT, leftFrontSteering);
		m_directions.put(Port.LEFT_BACK, leftBackSteering);
		m_directions.put(Port.RIGHT_FRONT, rightFrontSteering);
		m_directions.put(Port.RIGHT_BACK, rightBackSteering);
		
		//define crab rotate offsets for stationary rotation
		m_crabRotateOffsets.put(Port.LEFT_FRONT, -45.0);
		m_crabRotateOffsets.put(Port.LEFT_BACK, 45.0);
		m_crabRotateOffsets.put(Port.RIGHT_FRONT, 45.0);
		m_crabRotateOffsets.put(Port.RIGHT_BACK, -45.0);
		
		//default to robot centric mode
		m_mode = CrabDriveMode.RobotCentric;
		//init the angle adjuster
		m_angleAdjuster = new CrabDriveWheelSpeedCoordinator(leftFrontDrive, leftBackDrive, rightFrontDrive, rightBackDrive);
		//output to the angle adjuster from the PIDController
		m_yawController = new PIDController(P, I, D, gyro, m_angleAdjuster.yawReceiver);
	}
	
	/**
	 * request a speed, direction, and yaw. Called by a command. This class will then calculate and command the proper wheel angles and speeds.
	 * @param speed
	 * @param direction
	 * @param yaw
	 */
	public void request(double speed, double direction, double yaw)
	{
		//get setpoint from joysticks
    	//find closest angle from gyro to request
    	double robotAngle = closestAngle(Drive.GYRO.get(), yaw);
    	
		//if robot DOES NOT need to rotate
		//OR if robot is trying to rotate while translating
		if ((Math.abs(robotAngle) < 5) || (Math.abs(speed) > 0))
		{
			//rotate using only yawController -> CrabDriveWheelSpeedCoordinator
	    	
	    	//set Translation PID setpoints based on mode
	    	if (m_mode == CrabDriveMode.RobotCentric)
	    	{
		    	/**
		    	 * Robot Centric:
		    	 * Wheels are set relative to robot, no offset needed
		    	 */
		    	set(speed, direction);
		    	//let the angle adjuster know which wheels are left and right
		    	m_angleAdjuster.setDirection(direction);
	    	}
	    	else if (m_mode == CrabDriveMode.FieldCentric)
	    	{
		    	/**
		    	 * Field Centric:
		    	 * Wheels are set relative to field by subtracting robot angle
		    	 */
	    		double fieldDirection = direction - Drive.GYRO.get();
		    	set(speed, fieldDirection);
		    	//let the angle adjuster know which wheels are left and right
		    	m_angleAdjuster.setDirection(fieldDirection);
	    	}
		}
		//if robot DOES need to rotate AND it is stationary
		else
		{
			//do a crab rotate
			//Go the other way if clockwise rotation.
			//set wheels to their crab rotate positions
			for (Port port : Port.values())
			{
				m_directions.get(port).set(m_crabRotateOffsets.get(port));
			}
			//set to normal left and right motors setting
			m_angleAdjuster.setDirection(0);
			//Note that the wheels are set to 0 because m_angleAdjuster moves the wheels.
		}
		m_yawController.setSetpoint(Drive.GYRO.get() + robotAngle);
	}
	
	/**
	 * Enables the CrabDriveCoordinator and subcomponents.
	 */
	public void enable()
	{
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
		m_yawController.disable();
		set(0, 0);
		for (Port port : Port.values())
		{
			m_directions.get(port).disable();
		}
	}
	
	/**
	 * Sets the CrabDriveCoordinator's subcomponents.
	 * @param speed
	 * @param angle
	 */
	private void set(double speed, double angle)
	{
		//sets the speed
		m_angleAdjuster.speedReceiver.set(speed);
		//sets the wheel angles
		for (Port port : Port.values())
		{
			m_directions.get(port).set(angle);
		}
	}
	
    /**
     * Get the closest angle between the given angles.
     * @param a
     * @param b
     * @return
     */
    private double closestAngle(double a, double b)
    {
    	//get direction
    	double dir = modulo(b, 360.0) - modulo(a, 360.0);
    	
    	//convert from -360 to 360 to -180 to 180
    	if (Math.abs(dir) > 180.0)
    	{
    		dir = -(Math.signum(dir) * 360.0) + dir;
    	}
    	return dir;
    }
    
    /**
     * Modulo that works with negative numbers and always returns a positive number.
     * @param a
     * @param b
     * @return
     */
	private double modulo(double a, double b)
	{
		return (a < 0 ? b + (a % b) : a % b);
	}
	
	/**
	 * Setpoint to CrabTurn angle is not an easily definable relation.
	 * Hence, a PID can't really be used here.
	 * This method calculates the CrabTurn angle, given a speed and an angle to turn.
	 * @param setpoint
	 * @return The angle to turn the wheels outward when turning.
	 */
	private double calculateCrabTurn(double speed, double angle)
	{
		//cap speed at 1.0
		if (Math.abs(speed) > 1.0)
		{
			speed = Math.signum(speed);
		}
		
		return (angle / 45) * (1.5 - speed) * 0.2;
	}
	
	/**
	 * Gets whether the yaw controller is on target
	 * @return
	 */
	public boolean yawIsOnTarget()
	{
		return m_yawController.onTarget();
	}
}