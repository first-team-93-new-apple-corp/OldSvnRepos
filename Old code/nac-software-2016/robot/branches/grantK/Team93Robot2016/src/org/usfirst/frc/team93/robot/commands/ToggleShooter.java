package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.BoulderAcquisition;

import edu.wpi.first.wpilibj.command.Command;

public class ToggleShooter extends Command
{

	public ToggleShooter()
	{
		requires(Robot.bacquisition);
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
		BoulderAcquisition.ExtendArm();

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
		BoulderAcquisition.RetractArm();
	}

	@Override
	protected void interrupted()
	{
		// TODO Auto-generated method stub
		this.end();
	}

}
