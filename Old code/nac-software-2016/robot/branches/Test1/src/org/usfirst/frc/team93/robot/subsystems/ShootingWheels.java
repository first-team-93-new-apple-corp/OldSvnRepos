package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.commands.CANTalonCommand;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ShootingWheels extends Subsystem
{

	@Override
	protected void initDefaultCommand()
	{
		// TODO Auto-generated method stub
		setDefaultCommand(new CANTalonCommand(0.0));
	}

}
