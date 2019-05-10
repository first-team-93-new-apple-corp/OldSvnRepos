package org.usfirst.frc.team93.robot.utilities;

import org.usfirst.frc.team93.robot.subsystems.Scaler;

import edu.wpi.first.wpilibj.PIDOutput;

/**
 * 
 * @author ben.fager
 *         Essentially what this is is a PID output for teleoperated control of
 *         the scale without breaking the hard stops
 */
public class TeleopScaler implements PIDOutput
{
	@Override
	public void pidWrite(double output)
	{
		if (Scaler.bottomLimit.get() && output <= 0)
		{
			Scaler.offsetMotorOutput.pidWrite(0);
		}
		else if (Scaler.ScalerEncoder.getRaw() <= 1000 && output <= 0)
		{
			Scaler.offsetMotorOutput.pidWrite(output * 0.25);
		}
		else if (Scaler.ScalerEncoder.getRaw() <= 2000 && output <= 0)
		{
			Scaler.offsetMotorOutput.pidWrite(output * 0.5);
		}
		else if (Scaler.ScalerEncoder.getRaw() >= 1700 && output >= 0)
		{
			Scaler.offsetMotorOutput.pidWrite(output * 0.25);
		}
		else if (Scaler.ScalerEncoder.getRaw() >= 1600 && output >= 0)
		{
			Scaler.offsetMotorOutput.pidWrite(output * 0.5);
		}
		else
		{
			Scaler.offsetMotorOutput.pidWrite(output);
		}
		
	}
}
