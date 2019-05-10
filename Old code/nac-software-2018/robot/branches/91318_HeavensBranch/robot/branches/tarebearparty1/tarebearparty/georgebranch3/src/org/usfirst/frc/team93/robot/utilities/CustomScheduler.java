package org.usfirst.frc.team93.robot.utilities;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.subsystems.Claw;
import org.usfirst.frc.team93.robot.subsystems.Scaler;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CustomScheduler
{
	public static void RunTeleopScheduler()
	{
		
		Claw.leftIntake.pidWrite(DriveChooser.deadzone(OI.operator.getRawAxis(1), 0.15) * 0.7);
		Claw.rightIntake.pidWrite(DriveChooser.deadzone(OI.operator.getRawAxis(1), 0.15) * -1);
		
		SmartDashboard.putNumber("Encoder Val", Scaler.ScalerEncoder.getRaw());
		SmartDashboard.putBoolean("Lim switch val", Claw.leftLimit.get());
		SmartDashboard.putBoolean("OtherSideLim", Claw.rightLimit.get());
		if (Scaler.bottomLimit.get())
		{
			Scaler.EncoderPIDOutput.Reset();
		}
		
		// LeftRamp.leftRampMotorTwo.pidWrite(DriveChooser.deadzone(OI.operator.getRawAxis(3),
		// 0.1));
		// System.out.println("Val set to" + Scaler.ScaleController.get());
		// System.out.println("Setpoint" + Scaler.ScaleController.getSetpoint());
		// System.out.println("Encoder Feedback: " + Scaler.ScalerEncoder.getRaw());
		// System.out.println(Scaler.ScaleController.get)
		// System.out.println(Scaler.ScaleController.onTarget());
		
		// Claw.leftIntake.set(1);
	}
	
	public static void RunAutoScheduler()
	{
		
	}
}
