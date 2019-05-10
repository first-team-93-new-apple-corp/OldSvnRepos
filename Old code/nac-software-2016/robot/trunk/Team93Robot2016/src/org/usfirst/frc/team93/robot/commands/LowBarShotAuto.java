package org.usfirst.frc.team93.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LowBarShotAuto extends CommandGroup
{

	public LowBarShotAuto()
	{

		double turn = 34;

		addParallel(new SetFiringAngle(5, 4));
		addSequential(new DriveForward(500, 5));
		addParallel(new TurnRight(turn, 5));
		addSequential(new SetFiringAngle(51.5, 5));
		addSequential(new ShootingSequence(29000));
		addParallel(new SetFiringAngle(5, 5));
		addSequential(new TurnRight(-turn, 5));
	}
}
