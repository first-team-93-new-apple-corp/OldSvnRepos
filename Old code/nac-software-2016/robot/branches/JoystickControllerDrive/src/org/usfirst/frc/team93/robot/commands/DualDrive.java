package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.subsystems.DriveSub;

import edu.wpi.first.wpilibj.command.Command;

public class DualDrive extends Command
{

	public DualDrive()
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
		double leftYValue = -1 * OI.doubleDriveLeft.getRawAxis(1);
		if (Math.abs(leftYValue) < .1)
		{
			leftYValue = 0;
		}
		double rightYValue = -1 * OI.doubleDriveRight.getRawAxis(1);
		if (Math.abs(rightYValue) < .1)
		{
			rightYValue = 0;
		}
		DriveSub.setLeftMotors(leftYValue);
		DriveSub.setRightMotors(rightYValue);
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