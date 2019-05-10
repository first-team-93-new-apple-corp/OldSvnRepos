package org.usfirst.frc.team93.robot.utilities;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.subsystems.Claw;
import org.usfirst.frc.team93.robot.subsystems.Scaler;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CustomScheduler
{
	public static void RunTeleopScheduler()
	{
		
		Scaler.offsetMotorOutput.pidWrite(DriveChooser.deadzone(OI.operator.getRawAxis(1), 0.15));
		
		SmartDashboard.putNumber("Encoder Val", Scaler.ScalerEncoder.getRaw());
		SmartDashboard.putBoolean("Lim switch val", Claw.leftLimit.get());
		SmartDashboard.putBoolean("OtherSideLim", Claw.rightLimit.get());
		if (Scaler.bottomLimit.get())
		{
			Scaler.EncoderPIDOutput.Reset();
		}
		
		Scaler.offsetMotorOutput.pidWrite(DriveChooser.deadzone(OI.operator.getRawAxis(3), 0.05));
		System.out.println(Scaler.ScaleController.get());
		// Claw.leftIntake.set(1);
	}
	
	public static void RunAutoScheduler()
	{
		
	}
}
