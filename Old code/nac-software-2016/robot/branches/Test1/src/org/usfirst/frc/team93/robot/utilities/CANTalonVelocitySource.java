/**
 * 
 */
package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * @author Admin93
 *
 */
public class CANTalonVelocitySource implements PIDSource
{

	CANTalon m_input = null;

	public CANTalonVelocitySource(CANTalon input)
	{
		m_input = input;
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
		return m_input.getEncVelocity();
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
