package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Timer;

/**
 * PIDOutputLimitedLoad
 * 
 * @author EvansChen
 * 
 * This class describes a PIDOutput, such as a motor, which should only take a
 * limited amount of load.
 * 
 * This helps reduce brownouts.
 */
public class PIDOutputLimitedLoad implements PIDOutputEnhanced
{
	public double m_maxLoad = 1.0;
	public Timer m_timer;
	
	public PIDOutput m_output;
	
	public double m_value;
	
	public double m_gain;
	
	/**
	 * The constructor for PIDOutputLimitedLoad.
	 * 
	 * @param output The PIDOutput to write to.
	 * @param maxLoad The maximum amount of change in output per second.
	 */
	public PIDOutputLimitedLoad(PIDOutput output, double maxLoad)
	{
		// create and start the timer
		m_timer = new Timer();
		m_timer.start();
		m_timer.reset();
		
		// store output
		m_output = output;
		
		// store max load
		m_maxLoad = maxLoad;
		
		// default value and gain
		m_value = 0.0;
		m_gain = 1.0;
	}
	
	@Override
	public void pidWrite(double output)
	{
		set(output);
	}
	
	@Override
	public void set(double value)
	{
		// multiply value by gain
		value = m_gain * value;
		
		double maxChange = m_maxLoad * m_timer.get();
		// if request exceeds max load
		if (Math.abs(value - m_value) > maxChange)
		{
			// make the output closer to the target value
			m_value = m_value + (Math.signum(value - m_value) * maxChange);
		}
		// if request is within max load
		else
		{
			// set the output to the requested value
			m_value = value;
		}
		// write to the output
		m_output.pidWrite(m_value);
		// reset the timer
		m_timer.reset();
	}
	
	@Override
	public double get()
	{
		return m_value;
	}
	
	@Override
	public double getGain()
	{
		return m_gain;
	}
	
	@Override
	public void setGain(double value)
	{
		m_gain = value;
	}
	
	@Override
	public PIDOutput getPIDOutput()
	{
		return m_output;
	}
}