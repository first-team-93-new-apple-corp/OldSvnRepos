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
	final int MOVINGAVERAGEAMOUNT = 23;
	
	double[] arrayAvg;
	int arrayLocation;
	
	public TeleopScaler()
	{
		arrayAvg = new double[MOVINGAVERAGEAMOUNT];
		for (int i = 0; i < arrayAvg.length; i++)
		{
			arrayAvg[i] = 0;
		}
		arrayLocation = 0;
	}
	
	@Override
	public void pidWrite(double output)
	{
		arrayAvg[arrayLocation] = output;
		arrayLocation++;
		arrayLocation %= arrayAvg.length;
		double average = 0;
		for (int i = 0; i < arrayAvg.length; i++)
		{
			average += arrayAvg[i];
		}
		average /= arrayAvg.length;
		setMotor(average);
	}
	
	void setMotor(double output)
	{
		if (Scaler.ScalerEncoder.getRaw() <= 4000 && output > 0)
		{
			Scaler.offsetMotorOutput.pidWrite(output * 0.25);
		}
		else if (Scaler.ScalerEncoder.getRaw() >= 16000 && output < 0)
		{
			Scaler.offsetMotorOutput.pidWrite(output * 0.5);
		}
		else if (Scaler.ScalerEncoder.getRaw() >= 14000 && output < 0)
		{
			Scaler.offsetMotorOutput.pidWrite(output * 0.75);
		}
		
		else
		{
			Scaler.offsetMotorOutput.pidWrite(output);
		}
	}
}
