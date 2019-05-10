package org.usfirst.frc.team93.robot.utilities;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.SpeedController;

public class CANVictorSPX implements SpeedController
{
	private VictorSPX victor;
	private double vicky;
	private boolean m_isInverted;
	
	public CANVictorSPX(int port) {
		victor=new VictorSPX(port);
		vicky=0;
	}

	@Override
	public void pidWrite(double output) {
		if(m_isInverted=true) {
			victor.set(ControlMode.PercentOutput, output*-1);
			vicky=output*-1;
		}
		else {
		victor.set(ControlMode.PercentOutput, output);
		vicky=output;}
	}

	@Override
	public void set(double speed) {
		if(m_isInverted=true) {
			victor.set(ControlMode.PercentOutput, speed*-1);
			vicky=speed*-1;
		}
		else {
		victor.set(ControlMode.PercentOutput, speed);
		vicky=speed;}
	}

	@Override
	public double get() {
		
		return vicky;
	}

	@Override
	public void setInverted(boolean isInverted) {
		 m_isInverted=isInverted;
	}

	@Override
	public boolean getInverted() {
		
		return m_isInverted;
	}

	@Override
	public void disable() {
		set(0);
	}

	@Override
	public void stopMotor() {
		set(0);		
	}

}
