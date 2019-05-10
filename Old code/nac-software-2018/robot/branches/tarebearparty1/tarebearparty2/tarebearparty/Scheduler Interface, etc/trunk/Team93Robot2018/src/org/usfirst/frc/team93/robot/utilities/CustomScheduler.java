package org.usfirst.frc.team93.robot.utilities;

import java.util.ArrayList;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.subsystems.Claw;
import org.usfirst.frc.team93.robot.subsystems.Scaler;

public class CustomScheduler
{
	
	static ArrayList<CustomScheduledObject> ScheduledObjects;
	
	public static void RunTeleopScheduler()
	{
		if (DriveChooser.deadzone(OI.operator.getRawAxis(1), 0.15) >= 0)
		{
			Claw.leftIntake.pidWrite(DriveChooser.deadzone(OI.operator.getRawAxis(1), 0.15) * 0.7);
			Claw.rightIntake.pidWrite(DriveChooser.deadzone(OI.operator.getRawAxis(1), 0.15) * -1);
		}
		else
		{
			Claw.leftIntake.pidWrite(DriveChooser.deadzone(OI.operator.getRawAxis(1), 0.15) * 1);
			Claw.rightIntake.pidWrite(DriveChooser.deadzone(OI.operator.getRawAxis(1), 0.15) * -1);
		}
		if (Scaler.bottomLimit.get())
		{
			Scaler.EncoderPIDOutput.Reset();
		}
		System.out.println("Encoder Feedback: " + Scaler.ScalerEncoder.getRaw());
		
		for (int i = 0; i <= ScheduledObjects.size(); i++)
		{
			ScheduledObjects.get(i).schedule();
		}
	}
	
	public static void RunAutoScheduler()
	{
		for (int i = 0; i <= ScheduledObjects.size(); i++)
		{
			ScheduledObjects.get(i).schedule();
		}
	}
	
	public static void addScheduledItemToList(CustomScheduledObject o)
	{
		ScheduledObjects.add(o);
	}
	
	public static void removeScheduledItemFromList(CustomScheduledObject o)
	{
		ScheduledObjects.remove(o);
	}
}
