package org.usfirst.frc.team93.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SetShooterAngle extends CommandGroup
{
	public SetShooterAngle(double firingAngle)
	{
		addSequential(new SetFiringAngle(13, 3));
		addSequential(new SetFiringAngle(firingAngle, 0.7));
	}
}
