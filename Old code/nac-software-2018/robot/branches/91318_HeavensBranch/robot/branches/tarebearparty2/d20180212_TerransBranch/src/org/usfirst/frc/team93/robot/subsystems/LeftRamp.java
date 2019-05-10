package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.utilities.CANVictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class LeftRamp extends Subsystem
{
	public static CANVictorSPX leftRampMotor;
	
	public LeftRamp()
	{
		leftRampMotor = new CANVictorSPX(RobotMap.LeftRampMotor);
	}
	
	@Override
	protected void initDefaultCommand()
	{
		// TODO Auto-generated method stub
		
	}
	
	public void setMotors(double motorSpeed)
	{
		if (motorSpeed > 1 || motorSpeed < -1)
		{
			System.out.println("motorSpeed was not between -1 and 1");
			if (motorSpeed < -1)
			{
				motorSpeed = -1;
			}
			else if (motorSpeed > 1)
			{
				motorSpeed = 1;
			}
		}
		leftRampMotor.set(motorSpeed);
	}
	
}
