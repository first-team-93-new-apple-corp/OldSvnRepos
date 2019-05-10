package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Autonomous extends CommandGroup
{

	int autonomousSelection;
	public Autonomous()
	{
		autonomousSelection = 0;
		for(int i = 0; i< RobotMap.AutoSwiches.length; i++)
		{
			if(RobotMap.AutoSwiches[i].get())
			{ 
				autonomousSelection += Math.pow(2, i); 
			}
		}
		
		autonomousSelection++;
		
		switch(autonomousSelection)
		{
		case 1:
			// add instructions for first autonomous mode
			
			break;
		case 2:
			// add instructions for second autonomous mode
			
			break;
		case 3:
			// add instructions for third autonomous mode
			
			break;
		case 4:
			// add instructions for fourth autonomous mode
			
			break;
		}
	}
}
