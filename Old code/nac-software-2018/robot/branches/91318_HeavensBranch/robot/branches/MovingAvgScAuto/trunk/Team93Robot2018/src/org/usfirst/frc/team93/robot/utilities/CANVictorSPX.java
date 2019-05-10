/**
 * 
 */
package org.usfirst.frc.team93.robot.utilities;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * @author ben.fager
 *         The Wrapper that is used for VictorSPX using the CAN ports
 */
public class CANVictorSPX implements SpeedController
{
	private VictorSPX victor;
	
	public CANVictorSPX(int channel)
	{
		victor = new VictorSPX(channel);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.wpi.first.wpilibj.PIDOutput#pidWrite(double)
	 */
	@Override
	public void pidWrite(double output)
	{
		// TODO Auto-generated method stub
		victor.set(ControlMode.PercentOutput, output);
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.wpi.first.wpilibj.SpeedController#set(double)
	 */
	@Override
	public void set(double speed)
	{
		// TODO Auto-generated method stub
		victor.set(ControlMode.PercentOutput, speed);
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.wpi.first.wpilibj.SpeedController#get()
	 */
	@Override
	public double get()
	{
		// TODO Auto-generated method stub
		return victor.getMotorOutputPercent();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.wpi.first.wpilibj.SpeedController#setInverted(boolean)
	 */
	@Override
	public void setInverted(boolean isInverted)
	{
		victor.setInverted(isInverted);
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.wpi.first.wpilibj.SpeedController#getInverted()
	 */
	@Override
	public boolean getInverted()
	{
		// TODO Auto-generated method stub
		return victor.getInverted();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.wpi.first.wpilibj.SpeedController#disable()
	 */
	@Override
	public void disable()
	{
		victor.set(ControlMode.PercentOutput, 0);
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.wpi.first.wpilibj.SpeedController#stopMotor()
	 */
	@Override
	public void stopMotor()
	{
		// TODO Auto-generated method stub
		victor.set(ControlMode.PercentOutput, 0);
		
	}
}
