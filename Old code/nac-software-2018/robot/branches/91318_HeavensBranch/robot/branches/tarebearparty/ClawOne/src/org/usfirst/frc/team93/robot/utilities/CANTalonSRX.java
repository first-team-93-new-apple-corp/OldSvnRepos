package org.usfirst.frc.team93.robot.utilities;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.PIDOutput;

public class CANTalonSRX implements PIDOutput{
	private TalonSRX talon;
	
	/**
	 * Sets up the Talon with on the channel that is input
	 * @param channel
	 */
	public CANTalonSRX(int channel) 
	{
		talon = new TalonSRX(channel);
	}
	/**
	 * Sets the talon to the value that is given
	 * @param input
	 */
	public void set(double input) 
	{
		talon.set(ControlMode.PercentOutput, input);
	}
	/**
	 * Sets the talon to the value that is given
	 * @param input
	 */
	@Override
	public void pidWrite(double input) 
	{
		talon.set(ControlMode.PercentOutput, input);
	}
	/**
	 * returns the value that was previously set
	 * @return
	 */
	public double get() 
	{
		return talon.getMotorOutputPercent();
	}
}
