package org.usfirst.frc.team93.robot.other;

import edu.wpi.first.wpilibj.AnalogInput;

public class SwerveMotorModule {
	
	//THE LIMITS OF THE INPUT
	double MaxVolts;
	double MinVolts;
	double DeltaVolts;
	
	double VoltDegRatio;
	
	AnalogInput m_encoder;
	
	public SwerveMotorModule(double maxVolt, double minVolt, AnalogInput encoder)
	{
		MaxVolts = maxVolt;
		MinVolts = minVolt;
		DeltaVolts = (maxVolt - minVolt);
		VoltDegRatio = (360/DeltaVolts);
		m_encoder = encoder;
	}
	
	public double getAngle()
	{
		return (m_encoder.getVoltage()-MinVolts) * VoltDegRatio;
	}
}
