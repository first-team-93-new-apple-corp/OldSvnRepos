/**
 * 
 */
package org.usfirst.frc.team93.robot.Utilities;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * @author Admin93
 *
 */
public class CANTalonVelocitySource implements PIDSource
{

	CANTalon m_input = null;
	double m_gain;

	public CANTalonVelocitySource(CANTalon input)
	{
		m_input = input;
		m_gain = 1.0;
	}

	public CANTalonVelocitySource(CANTalon input, double gain)
	{
		m_input = input;
		m_gain = gain;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.wpi.first.wpilibj.PIDSource#getPIDSourceType()
	 */
	@Override
	public PIDSourceType getPIDSourceType()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.wpi.first.wpilibj.PIDSource#pidGet()
	 */
	@Override
	public double pidGet()
	{
		// TODO Auto-generated method stub
		return m_input.getEncVelocity() * m_gain;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.wpi.first.wpilibj.PIDSource#setPIDSourceType(edu.wpi.first.wpilibj.
	 * PIDSourceType)
	 */
	@Override
	public void setPIDSourceType(PIDSourceType arg0)
	{
		// TODO Auto-generated method stub

	}

}
