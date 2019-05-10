package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This class is for operating the scaler
 */
public class Scaler extends Subsystem
{
	private static boolean scalerOut = false;

	public Scaler()
	{

	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	/**
	 * This method lowers or raises the release solenoid of the scaler
	 * 
	 * @param released
	 *            The state of the released solenoid being raised
	 */
	public static void setReleaseSolenoid(boolean released)
	{
		if (released)
		{
			RobotMap.releaseScaler.set(DoubleSolenoid.Value.kForward);
		} else
		{
			RobotMap.releaseScaler.set(DoubleSolenoid.Value.kReverse);
		}
	}

	/**
	 * This method lowers or raises the arm solenoid of the scaler
	 * 
	 * @param scaling
	 *            the state of the arm solenoid being raised
	 */
	public static void setArmSolenoid(boolean scaling)
	{
		if (scaling)
		{
			RobotMap.releaseArm.set(DoubleSolenoid.Value.kForward);
		} else
		{
			RobotMap.releaseArm.set(DoubleSolenoid.Value.kReverse);
		}
	}

	/**
	 * This method returns the state of the scaler being out
	 * 
	 * @codereview ColbyMcKinley: This function needs to be written
	 * @return the state of the scaler if it is out
	 */
	public static boolean getScalerOut()
	{
		return scalerOut;
	}

	/**
	 * This method sets the scaler being out
	 * 
	 * @param state
	 *            the state of the scaler if it is out
	 */
	public static void setScalerOut(boolean state)
	{
		scalerOut = state;
	}

	@Override
	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
