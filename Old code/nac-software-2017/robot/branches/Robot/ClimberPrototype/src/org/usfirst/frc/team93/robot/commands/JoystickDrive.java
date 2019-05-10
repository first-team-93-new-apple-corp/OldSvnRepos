package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.subsystems.DriveSub;

import edu.wpi.first.wpilibj.command.Command;

public class JoystickDrive extends Command
{

	public JoystickDrive()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initialize()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void execute()
	{
		// TODO Auto-generated method stub
		double noYDeadZone = -1 * (OI.driverJoystick.getRawAxis(1));
		if (Math.abs(noYDeadZone) < .1)
		{
			noYDeadZone = 0;
		}

		double noXDeadZone = OI.driverJoystick.getRawAxis(0);
		if (Math.abs(noXDeadZone) < .25)
		{
			noXDeadZone = 0;
		}

		double noZDeadZone = OI.driverJoystick.getRawAxis(2);
		if (Math.abs(noZDeadZone) < .4)
		{
			noZDeadZone = 0;
		}

		if (noXDeadZone == 0) // if the x axis value is 0
		{
			// we're setting all the motors to the same value so we drive
			// straight forward/backward
			DriveSub.setAllMotors(noYDeadZone);
		}

		if (noYDeadZone == 0 && noXDeadZone == 0) // if the x and y axis values
													// are 0
		{
			// we're setting the motors to the rotating axis
			DriveSub.setRightMotors(.5 * -1 * noZDeadZone);
			DriveSub.setLeftMotors(.5 * noZDeadZone);
		}

		if (noXDeadZone >= .25) // If the joystick is pointed right or left
		{
			// we're setting right motors to the y value (up = positive) - the x
			// value (right = positive) so that if we are pointing the joystick
			// to the right we will subtract and set the right motors to a
			// smaller value.
			DriveSub.setRightMotors(noYDeadZone - noXDeadZone);
			DriveSub.setLeftMotors(noYDeadZone + noXDeadZone);
		}

		System.out.println(RobotMap.frontRight.get());
		System.out.println(RobotMap.frontLeft.get());
	}

	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted()
	{
		// TODO Auto-generated method stub

	}

}
