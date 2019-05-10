package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.utilities.CANVictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class RightRamp extends Subsystem
{
	public static CANVictorSPX rightRampMotorOne;
	public static CANVictorSPX rightRampMotorTwo;
	
	public RightRamp()
	{
		rightRampMotorOne = new CANVictorSPX(RobotMap.RightRampMotorOne);
		rightRampMotorTwo = new CANVictorSPX(RobotMap.RightRampMotorTwo);
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
		rightRampMotorOne.set(motorSpeed);
		rightRampMotorTwo.set(motorSpeed * -1.0);
	}
	
}
