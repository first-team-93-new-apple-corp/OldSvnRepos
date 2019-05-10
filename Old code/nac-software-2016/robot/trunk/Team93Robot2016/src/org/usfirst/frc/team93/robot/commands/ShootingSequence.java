package org.usfirst.frc.team93.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ShootingSequence extends CommandGroup
{
	public ShootingSequence(double shootingSpeed)
	{
		addSequential(new SetShooterBrake(true));
		addSequential(new PrepareSetShooterWheels(.2, 600));
		addSequential(new SetShooterBrake(false));
		addSequential(new SetShootWheels(shootingSpeed, shootingSpeed / 80));
		addSequential(new ShootBoulder(shootingSpeed));
	}
}