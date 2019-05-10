package org.usfirst.frc.team93.robot.commands.drive;

import org.usfirst.frc.team93.robot.commands.PrintCommand;
import org.usfirst.frc.team93.robot.commands.SleepCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonomousTestingGroup extends CommandGroup
{
	public AutonomousTestingGroup()
	{
		addSequential(new PrintCommand("STARTING AUTONOMOUS TESTING COMMAND GROUP"));
		
		addSequential(new PrintCommand("STARTING DRIVE FORWARD."));
		addSequential(new DriveAndRealign(1000.0, 10.0, 0));
		addSequential(new PrintCommand("DRIVE FORWARD COMPLETE."));
		
		addSequential(new PrintCommand("WAITING 1 SECOND."));
		addSequential(new SleepCommand(1));
		addSequential(new PrintCommand("WAIT COMPLETE."));
		
		addSequential(new PrintCommand("STARTING DRIVE RIGHT."));
		addSequential(new DriveAndRealign(1000.0, 10.0, -90));
		addSequential(new PrintCommand("STARTING DRIVE RIGHT."));
		
		addSequential(new PrintCommand("WAITING 1 SECOND."));
		addSequential(new SleepCommand(1));
		addSequential(new PrintCommand("WAIT COMPLETE."));
		
		addSequential(new PrintCommand("STARTING DRIVE BACKWARD."));
		addSequential(new DriveAndRealign(1000.0, 10.0, 180));
		addSequential(new PrintCommand("STARTING DRIVE BACKWARD."));
		
		addSequential(new PrintCommand("WAITING 1 SECOND."));
		addSequential(new SleepCommand(1));
		addSequential(new PrintCommand("WAIT COMPLETE."));
		
		addSequential(new PrintCommand("STARTING DRIVE LEFT."));
		addSequential(new DriveAndRealign(1000.0, 10.0, 90));
		addSequential(new PrintCommand("STARTING DRIVE LEFT."));
		
		addSequential(new PrintCommand("WAITING 1 SECOND."));
		addSequential(new SleepCommand(1));
		addSequential(new PrintCommand("WAIT COMPLETE."));
		
		addSequential(new PrintCommand("ENDING AUTONOMOUS TESTING COMMAND GROUP"));
	}
}
