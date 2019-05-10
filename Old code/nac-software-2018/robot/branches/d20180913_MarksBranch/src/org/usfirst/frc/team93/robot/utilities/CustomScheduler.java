package org.usfirst.frc.team93.robot.utilities;

import org.usfirst.frc.team93.robot.subsystems.Scaler;

public class CustomScheduler
{
	public static void RunTeleopScheduler()
	{
		if (Scaler.bottomLimit.get())
		{
			Scaler.ScalerEncoder.Reset();
		}
		// System.out.println(Drive.gyro.get());
		// System.out.println((Drive.LeftDriveEncoder.getRaw()) + "LEFT");
		// System.out.println((Drive.RightDriveEncoder.getRaw()) + "RIGHT");
		System.out.println(Scaler.scalerMotor.get());
	}
	
	public static void RunAutoScheduler()
	{
		
	}
}