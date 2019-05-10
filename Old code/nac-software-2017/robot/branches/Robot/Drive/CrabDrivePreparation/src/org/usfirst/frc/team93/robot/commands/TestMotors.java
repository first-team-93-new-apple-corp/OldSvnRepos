package org.usfirst.frc.team93.robot.commands;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * 
 */
public class TestMotors extends CommandGroup
{
	//tests a list of motors
    public TestMotors(ArrayList<PIDOutput> outputs, double value, int delayms, int timems)
    {
    	//iterate through the motors
    	for (int i = 0; i < outputs.size(); i++)
    	{
    		//run the motor for a while
    		addSequential(new TestMotor(outputs.get(i), value, timems));
    		//delay
    		addSequential(new SleepCommand(delayms));
    	}
    }
}