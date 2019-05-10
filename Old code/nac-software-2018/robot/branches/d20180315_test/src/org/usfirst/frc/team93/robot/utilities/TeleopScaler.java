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
		// if (Scaler.bottomLimit.get() && output <= 0)
		// {
		// Scaler.offsetMotorOutput.pidWrite(0);
		// }
		if (Scaler.ScalerEncoder.getRaw() <= 4000 && output > 0)
		{
			Scaler.offsetMotorOutput.pidWrite(output * 0.25);
		}
		else if (Scaler.ScalerEncoder.getRaw() >= 14000 && output < 0)
		{
			Scaler.offsetMotorOutput.pidWrite(output * 0.5);
		}
		else
		{
			Scaler.offsetMotorOutput.pidWrite(output);
		}
		
	}
}
