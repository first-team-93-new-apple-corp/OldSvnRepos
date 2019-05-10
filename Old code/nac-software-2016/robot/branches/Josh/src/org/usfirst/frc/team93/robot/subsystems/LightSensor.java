package org.usfirst.frc.team93.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

public class LightSensor extends Subsystem
{
static int rotations = 0;
	@Override
	protected void initDefaultCommand() 
	{
		// TODO Auto-generated method stub
		
	}
	public static void add ()
	{
		rotations++;
	}
	protected int rotations ()
	{
		return rotations;
		
	}

}
