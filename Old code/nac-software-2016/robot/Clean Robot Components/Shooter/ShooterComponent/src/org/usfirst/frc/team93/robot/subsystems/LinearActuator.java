package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.commands.AdjustFiringAngle;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LinearActuator extends Subsystem
{

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	@Override
	public void initDefaultCommand()
	{
		setDefaultCommand(new AdjustFiringAngle(1.0));
	}

	public static void ManualAdjustShootingAngle(double Speed)
	{
		RobotMap.linAcq.set(Speed, false);
	}

}
