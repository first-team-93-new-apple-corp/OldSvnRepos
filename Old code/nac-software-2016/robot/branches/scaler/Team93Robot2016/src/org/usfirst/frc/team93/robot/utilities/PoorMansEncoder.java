package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Talon;

public class PoorMansEncoder implements PIDSource
{

	Talon m_motor;
	DigitalInput m_lightSensor;
	boolean sameRotation = false;
	int rotations = 0;
	PIDSourceType m_pidSource;

	public PoorMansEncoder(DigitalInput lightSensor, Talon motor)
	{
		m_lightSensor = lightSensor;
		m_motor = motor;
		m_pidSource = PIDSourceType.kDisplacement;
	}

	public void update()
	{
		// TODO Auto-generated constructor stub
		if (m_lightSensor.get() != sameRotation)
		{
			if (Math.abs(m_motor.get()) != 0)
			{
				rotations += m_motor.get() / -Math.abs(m_motor.get());
			} else
			{
				rotations += 0;
			}
			sameRotation = m_lightSensor.get();
		}
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource)
	{
		m_pidSource = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType()
	{
		return m_pidSource;
	}

	@Override
	public double pidGet()
	{
		update();
		return rotations;
	}

	public void reset()
	{
		rotations = 0;
	}

}