package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveSub extends Subsystem
{

	public DriveSub()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initDefaultCommand()
	{
		// TODO Auto-generated method stub

	}

	public static void setRightMotors(double speed)
	{
		speed = Math.pow(speed, 3);
		RobotMap.frontRight.set(-speed);
		// RobotMap.middleRight.set(speed);
		RobotMap.backRight.set(-speed);
	}

	public static void setLeftMotors(double speed)
	{
		speed = Math.pow(speed, 3);
		RobotMap.frontLeft.set(speed);
		// RobotMap.middleLeft.set(-1 * speed);
		RobotMap.backLeft.set(speed);
	}

	public static void setAllMotors(double speed)
	{
		setLeftMotors(speed);
		setRightMotors(speed);
	}
}
