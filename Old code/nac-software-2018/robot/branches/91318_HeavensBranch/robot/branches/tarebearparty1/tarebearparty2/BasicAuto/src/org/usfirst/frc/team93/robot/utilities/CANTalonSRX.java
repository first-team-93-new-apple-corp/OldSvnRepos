package org.usfirst.frc.team93.robot.utilities;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;

public class CANTalonSRX implements SpeedController
{
	private TalonSRX talon;
	private int multiplier;
	VictorSP a;
	
	/**
	 * Sets up the Talon with on the channel that is input
	 * 
	 * @param channel
	 */
	public CANTalonSRX(int channel)
	{
		talon = new TalonSRX(channel);
		multiplier = 1;
	}
	
	/**
	 * Sets the talon to the value that is given
	 * 
	 * @param input
	 */
	@Override
	public void set(double input)
	{
		talon.set(ControlMode.PercentOutput, input);
	}
	
	/**
	 * Sets the talon to the value that is given
	 * 
	 * @param input
	 */
	@Override
	public void pidWrite(double input)
	{
		talon.set(ControlMode.PercentOutput, input * multiplier);
	}
	
	/**
	 * returns the value that was previously set
	 * 
	 * @return
	 */
	@Override
	public double get()
	{
		return talon.getMotorOutputPercent();
	}
	
	@Override
	public void setInverted(boolean isInverted)
	{
		// TODO Auto-generated method stub
		if (isInverted)
		{
			multiplier = -1;
		}
		else
		{
			multiplier = 1;
		}
		
	}
	
	@Override
	public boolean getInverted()
	{
		// TODO Auto-generated method stub
		return (multiplier == -1);
	}
	
	@Override
	public void disable()
	{
		// TODO Auto-generated method stub
		talon.set(ControlMode.PercentOutput, 0);
		
	}
	
	@Override
	public void stopMotor()
	{
		// TODO Auto-generated method stub
		talon.set(ControlMode.PercentOutput, 0);
		
	}
}
