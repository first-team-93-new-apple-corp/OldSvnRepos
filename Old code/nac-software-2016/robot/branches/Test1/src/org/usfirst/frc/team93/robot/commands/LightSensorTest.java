package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.utilities.LightSensor;

import edu.wpi.first.wpilibj.command.*;
import edu.wpi.first.wpilibj.util.*;

public class LightSensorTest extends Command 
{
	LightSensor ls = new LightSensor();
	public LightSensorTest()
	{
		
	}
	
	protected void initialize() 
	{
		// TODO Auto-generated method stub
		
	}
	
	public void execute()
	{
		//LightSensor.update();
		System.out.println(ls.pidGet());
		//System.out.println(RobotMap.lightSensor.get());
		System.out.flush();
	}
	
	
	public boolean isFinished()
	{
		return false;
		
	}
	
	public void end()
	{
		
	}
	
	protected void interrupted() 
	{
		// TODO Auto-generated method stub
		
	}

	

	
}
