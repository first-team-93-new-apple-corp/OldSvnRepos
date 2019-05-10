package org.usfirst.frc.team93.robot.utilities.crabdrive;

import org.usfirst.frc.team93.robot.utilities.PIDOutputExtended;
import org.usfirst.frc.team93.robot.utilities.Team93PIDController;

import edu.wpi.first.wpilibj.PIDSource;

/**
 * CrabDriveDirectionMotor
 * 
 * @author Evans Chen
 * 
 *         This class merges a SPIEncoderSource, a PIDController, and a
 *         SpeedController. Using this class, one can merge these components to
 *         form one simple component, to which other classes can request a given
 *         angle.
 * 
 *         To set the direction of a wheel to a given angle, use:
 *         CrabDriveDirectionMotor.set(double value)
 * 
 *         NOTE: This class does NOT calculate the closest rotational path. That
 *         is calculated in CrabDriveWheelModule.
 */
public class CrabDriveDirectionMotor
{
	public Team93PIDController m_controller;
	public PIDOutputExtended m_output;
	public PIDSource m_source;

	/**
	 * Constructor for CrabDriveDirectionMotor.
	 * 
	 * @param P
	 *            P constant for the CrabDriveDirectionMotor's PID
	 * @param I
	 *            I constant for the CrabDriveDirectionMotor's PID
	 * @param D
	 *            D constant for the CrabDriveDirectionMotor's PID
	 * @param ILimit
	 *            I contribution limit for the CrabDriveDirectionMotor's PID
	 * @param source
	 *            PIDSource, usually a SPI Encoder Source
	 * @param output
	 *            A motor that controls the wheel's rotation
	 */
	public CrabDriveDirectionMotor(double P, double I, double D, double ILimit, PIDSource source, PIDOutputExtended output)
	{
		m_controller = new Team93PIDController(P, I, D, ILimit, source, output);
		m_output = output;
		m_source = source;
	}

	/**
	 * Sets the wheel to a certain angle.
	 * 
	 * @param value
	 */
	public void set(double value)
	{
		double currentWheelAngle = get();
		double desiredWheelAngle = value;
		double deltaAngle = closestAngle(currentWheelAngle, desiredWheelAngle);
		m_controller.setSetpoint(currentWheelAngle + deltaAngle);
	}

	/**
	 * Resets the CrabDriveDirectionMotor
	 */
	public void reset()
	{
		m_controller.reset();
	}

	/**
	 * Gets the angle of the wheel.
	 * 
	 * @return
	 */
	public double get()
	{
		return m_source.pidGet();
	}

	/**
	 * Enables the CrabDriveDirectionMotor by enabling its PID.
	 */
	public void enable()
	{
		reset();
		m_controller.enable();
	}

	/**
	 * Disables the CrabDriveDirectionMotor by disabling its PID.
	 */
	public void disable()
	{
		m_controller.disable();
		m_output.pidWrite(0);
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
}