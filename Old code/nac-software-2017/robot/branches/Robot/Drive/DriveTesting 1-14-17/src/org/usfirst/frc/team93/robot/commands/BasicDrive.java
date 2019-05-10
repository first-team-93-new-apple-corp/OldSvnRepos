package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.subsystems.DriveSub;

import edu.wpi.first.wpilibj.command.Command;

public class BasicDrive extends Command
{

	public BasicDrive()
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
		DriveSub.setLeftMotors(OI.getLeftValue());
		DriveSub.setRightMotors(OI.getRightValue());
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
