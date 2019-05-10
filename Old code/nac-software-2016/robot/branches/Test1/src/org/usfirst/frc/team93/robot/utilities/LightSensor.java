package org.usfirst.frc.team93.robot.utilities;

import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class LightSensor implements PIDSource
{

	static boolean sameRotation = RobotMap.lightSensor.get();
	static int halfRotations = 0;
	static PIDSourceType m_pidSource;
	
	public LightSensor()
	{
		m_pidSource = PIDSourceType.kDisplacement;
	}
	
	public static void update() 
	{
		// TODO Auto-generated constructor stub
			if (RobotMap.lightSensor.get() != sameRotation)
			{
				if (Math.abs(RobotMap.PIDMotor.get()) != 0)
				{
					halfRotations += RobotMap.PIDMotor.get()/-Math.abs(RobotMap.PIDMotor.get());
				}
				else
				{
					halfRotations += 0;
				}
				sameRotation = RobotMap.lightSensor.get();
			}
	}
	
	public void setPIDSourceType(PIDSourceType pidSource) 
	{
	    m_pidSource = pidSource;
	}

	public PIDSourceType getPIDSourceType() 
	{
	    return m_pidSource;
	}
	
	@Override
	public double pidGet()
	{
		update();
		return halfRotations;
	}
	
	public void reset() 
	{
		halfRotations = 0;
	}
	
}