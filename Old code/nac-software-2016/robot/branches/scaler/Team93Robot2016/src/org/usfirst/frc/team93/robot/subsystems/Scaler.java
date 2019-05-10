package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Scaler extends Subsystem
{

	public Scaler()
	{

	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public static void setReleaseSolenoid(boolean released)
	{
		if (released)
		{
			RobotMap.leftScaler.set(DoubleSolenoid.Value.kForward);
		} else
		{
			RobotMap.leftScaler.set(DoubleSolenoid.Value.kReverse);
		}
	}

	public static void setScaleSolenoid(boolean scaling)
	{
		if (scaling)
		{
			RobotMap.leftScaler.set(DoubleSolenoid.Value.kForward);
		} else
		{
			RobotMap.leftScaler.set(DoubleSolenoid.Value.kReverse);
		}
	}

	@Override
	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
