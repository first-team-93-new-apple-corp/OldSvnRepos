package org.usfirst.frc.team93.robot.commands.drive;

import java.util.ArrayList;

import org.usfirst.frc.team93.robot.commands.SleepCommand;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This command group goes through and runs TestMotor for every motor.
 */
public class TestMotors extends CommandGroup
{
	// tests a list of motors
	public TestMotors(ArrayList<PIDOutput> outputs, double value, double seconds, int timems)
	{
		// iterate through the motors
		for (int i = 0; i < outputs.size(); i++)
		{
			// run the motor for a while
			addSequential(new TestMotor(outputs.get(i), value, timems));
			// delay
			addSequential(new SleepCommand(seconds));
		}
	}
}