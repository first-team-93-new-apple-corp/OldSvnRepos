package org.usfirst.frc.team93.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LowBarNoShotAuto extends CommandGroup
{
	public LowBarNoShotAuto()
	{
		// double turn = 30;

		addParallel(new SetFiringAngle(2, 4));
		addSequential(new DriveForward(850, 5));
		// addParallel(new TurnRight(turn, 5));
		// addSequential(new SetFiringAngle(56.5, 5));
		// addSequential(new ShootingSequence(29000));
		// addParallel(new SetFiringAngle(5, 5));
		// addSequential(new TurnRight(-turn, 5));
		// addSequential(new DriveForward(-475, 5));
		// addParallel(new TurnRight(-turn, 5));
		// addSequential(new IntakeBall(1, 0));
		// addSequential(new TurnRight(turn, 5));
		// addSequential(new DriveForward(475, 5));
		// addParallel(new TurnRight(turn, 5));
		// addSequential(new SetFiringAngle(56.5, 5));
		// addSequential(new ShootingSequence(29000));
	}
}
