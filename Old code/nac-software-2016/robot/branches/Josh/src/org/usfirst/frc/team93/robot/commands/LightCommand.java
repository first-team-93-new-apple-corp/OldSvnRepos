package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.subsystems.LightSensor;

import edu.wpi.first.wpilibj.command.Command;

public class LightCommand extends Command
{
public static boolean SameRotation = false;
	@Override
	protected void initialize() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() 
	{
		// TODO Auto-generated method stub
		if (RobotMap.lightSensor.get())
		{
			if(!SameRotation)
			{
				LightSensor.add();
				SameRotation = true;
			}
			
		}
		else{
			SameRotation = false;
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
