package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.commands.AdjustManipulator;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Manipulator extends Subsystem
{

	private final double MANIPULATOR_LOWER_TRAVEL = -1000.0;
	private final double MANIPULATOR_UPPER_TRAVEL = 1000.0;
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	@Override
	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		setDefaultCommand(new AdjustManipulator(0.5));
	}

	public static void setManipulator(double speed)
	{
		RobotMap.manipulator.set(speed, OI.operator.getRawButton(9));
	}

	public double getUpperLimit()
	{
		return MANIPULATOR_UPPER_TRAVEL;
	}

	public double getLowerLimit()
	{
		return MANIPULATOR_LOWER_TRAVEL;
	}

}
