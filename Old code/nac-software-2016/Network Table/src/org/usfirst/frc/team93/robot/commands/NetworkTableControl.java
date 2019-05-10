package org.usfirst.frc.team93.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;


public class NetworkTableControl extends Command
{

	NetworkTable Table;
	
	@Override
	protected void initialize() 
	{
		// TODO Auto-generated method stub
	Table = NetworkTable.getTable("datatable");
	
	
	}

	@Override
	protected void execute()																																																																																																																																																																																																																				
	{
		// TODO Auto-generated method stub
		
		double x = 0;
		double y = 0;
		Table.putNumber("X", x);	
		Table.putNumber("Y", y);
		x += 0.05;
		y += 1.0;		
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
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
