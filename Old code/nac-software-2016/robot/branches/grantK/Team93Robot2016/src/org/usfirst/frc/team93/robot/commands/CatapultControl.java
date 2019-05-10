package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;

public class CatapultControl extends Command{

	@Override
	protected void initialize() 
	{
		// TODO Auto-generated method stub
		//Shooter.RetractCatapult();
	}

	@Override
	protected void execute() 
	{
		// TODO Auto-generated method stub
		
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
//		if(Shooter.BallPresent())
//		{
//			Shooter.ShootCatapult();		
//		}
//		else
//		{
//			this.start();			
//		}
	}

	@Override
	protected void interrupted() 
	{
		// TODO Auto-generated method stub
		this.end();
	}

}
