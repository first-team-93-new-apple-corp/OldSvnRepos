package org.usfirst.frc.team93.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DefenseManipulator extends Subsystem
{

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	static int m_sign = 1;

	@Override
	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public static int toggleSign()
	{
		// TODO Auto-generated method stub
		System.out.println("TOBBLE!");
		m_sign = -m_sign;
		return m_sign;
	}

	public static int getToggle()
	{
		return (m_sign);
	}
}
