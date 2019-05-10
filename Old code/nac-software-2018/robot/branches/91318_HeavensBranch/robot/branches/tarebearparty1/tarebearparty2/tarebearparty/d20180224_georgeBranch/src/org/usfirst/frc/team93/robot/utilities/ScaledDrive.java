package org.usfirst.frc.team93.robot.utilities;

import org.usfirst.frc.team93.robot.subsystems.Scaler;

import edu.wpi.first.wpilibj.PIDOutput;

public class ScaledDrive implements PIDOutput
{
	PIDOutput m_out;
	
	public ScaledDrive(PIDOutput Out)
	{
		m_out = Out;
	}
	
	@Override
	public void pidWrite(double output)
	{
		m_out.pidWrite(output * (1 - (Math.abs(Scaler.ScalerEncoder.getRaw() / 20000))));
		// System.out.println((1 - (Scaler.ScalerEncoder.getRaw() / 20000)));
		// System.out.println(Scaler.ScalerEncoder.getRaw());
	}
}
