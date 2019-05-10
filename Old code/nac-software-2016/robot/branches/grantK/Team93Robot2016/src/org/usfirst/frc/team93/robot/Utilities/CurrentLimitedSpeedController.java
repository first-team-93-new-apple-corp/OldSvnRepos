package org.usfirst.frc.team93.robot.Utilities;

import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class CurrentLimitedSpeedController implements SpeedController
{
	
	private SpeedController m_slave;
	private double m_maxCurrent;
	int m_channel;

	public CurrentLimitedSpeedController(SpeedController OurSpeedController, double MaxCurrent, int channel)
	{
		m_slave = OurSpeedController;
		m_maxCurrent = MaxCurrent;
		m_channel = channel;
	}
	
	public boolean IsCurrentFine()
	{
		return RobotMap.powerDistribution.getCurrent(m_channel) < m_maxCurrent;
	}
	
	@Override
	public void set(double speed) 
	{
		if(IsCurrentFine())
		{
			m_slave.set(speed);
		}
		else 
		{
			m_slave.set(0.0);
			System.out.println("Too Much Current. Current: " + RobotMap.powerDistribution.getCurrent(m_channel));
		}
	}

	@Override
	public void pidWrite(double arg0) 
	{
		set(arg0);
	}

	@Override
	public void disable()
	{
		// TODO Auto-generated method stub
		m_slave.disable();
	}

	@Override
	public double get() 
	{
		// TODO Auto-generated method stub
		return m_slave.get();
		
	}

	@Override
	public boolean getInverted() 
	{
		// TODO Auto-generated method stub
		return m_slave.getInverted();
	}

	@Override
	public void set(double arg0, byte arg1) 
	{
		// TODO Auto-generated method stub
		System.out.println("CurrentLimitedSpeedController::set(double,byte): Use Other Set Function");
	}

	@Override
	public void setInverted(boolean arg0)
	{
		// TODO Auto-generated method stub
		m_slave.setInverted(arg0);
	}
}