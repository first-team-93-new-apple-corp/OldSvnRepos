package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class GearshiftSub extends Subsystem
{

	public static void shiftHighgear()
	{
		// shifts the solonoids outward, putting us in high gear
		RobotMap.Gearshift.set(DoubleSolenoid.Value.kForward);

	}

	public static void shiftLowgear()
	{
		// retracts the solonoids inward, putting us in low gear
		RobotMap.Gearshift.set(DoubleSolenoid.Value.kReverse);
	}

	@Override
	protected void initDefaultCommand()
	{
		// TODO Auto-generated method stub

	}

}
