package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.Utilities.CurrentLimitedSpeedController;

import edu.wpi.first.wpilibj.command.Command;

public class CheckCurrent extends Command{

	public CheckCurrent()
	{
		
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
		if(RobotMap.LimitedLeftMotor.IsCurrentFine())
		{
			RobotMap.LimitedLeftMotor.set(0.0);
		}
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
