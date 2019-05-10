package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Scaler extends Subsystem
{

	@Override
	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public static void retractArm(double armSpeed)
	{
		RobotMap.ScalarMotor.set(armSpeed);
	}

	public static void extendArm(double armSpeed)
	{
		RobotMap.ScalarMotor.set(armSpeed);
	}

}
