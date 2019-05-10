package org.usfirst.frc.team93.robot.utilities;

import org.usfirst.frc.team93.robot.OI;

/**
 * 
 * @author ben.fager
 *
 *         basically I wrote this so that OI is not so clogged up with the drive
 *         chooser being so big
 *
 */
public class DriveChooser
{
	public static double getDriverL()
	{
		// switch (Drive.driveControlMode)
		// {
		// case JOYSTICK_TANK:
		// SmartDashboard.putString("Left drive mode", "Tank Joystick");
		// return
		// getModifiedAxis(CutOff((deadzone(OI.splitDriverControlOne.getRawAxis(1),
		// OI.ControllerDeadzone)),
		// OI.JoystickCutoffVal));
		// case JOYSTICK_SPLIT_AXIS:
		// SmartDashboard.putString("Left drive mode", "Split Joystick");
		return getModifiedAxis(CutOff(
				(deadzone(OI.splitDriverControlOne.getRawAxis(1), OI.ControllerDeadzone)
						- deadzone(OI.splitDriverControlTwo.getRawAxis(0), OI.ControllerDeadzone)),
				OI.JoystickCutoffVal));
		// case JOYSTICK_ARCADE:
		// SmartDashboard.putString("Left drive mode", "Arcade Joystick");
		// return getModifiedAxis(CutOff(
		// (deadzone(OI.splitDriverControlOne.getRawAxis(1), OI.ControllerDeadzone)
		// - deadzone(OI.splitDriverControlOne.getRawAxis(0), OI.ControllerDeadzone)),
		// OI.JoystickCutoffVal));
		// default:
		// SmartDashboard.putString("Left drive mode", "Default");
		// return 0;
		// }
		
	}
	
	public static double getDriverR()
	{
		// switch (Drive.driveControlMode)
		// {
		// case JOYSTICK_TANK:
		// SmartDashboard.putString("Right drive mode", "Tank Joystick");
		// return getModifiedAxis(deadzone(OI.splitDriverControlTwo.getRawAxis(1),
		// OI.ControllerDeadzone));
		// case JOYSTICK_SPLIT_AXIS:
		// SmartDashboard.putString("Right drive mode", "Split Joystick");
		return getModifiedAxis(CutOff(
				(deadzone(OI.splitDriverControlOne.getRawAxis(1), OI.ControllerDeadzone)
						+ deadzone(OI.splitDriverControlTwo.getRawAxis(0), OI.ControllerDeadzone)),
				OI.JoystickCutoffVal));
		// case JOYSTICK_ARCADE:
		// SmartDashboard.putString("Right drive mode", "Arcade Joystick");
		// return getModifiedAxis(CutOff(
		// (deadzone(OI.splitDriverControlOne.getRawAxis(1), OI.ControllerDeadzone)
		// + deadzone(OI.splitDriverControlOne.getRawAxis(0), OI.ControllerDeadzone)),
		// OI.JoystickCutoffVal));
		// default:
		// SmartDashboard.putString("Right drive mode", "Default");
		// return 0;
		//
		// }
		
	}
	
	public static double CutOff(double inputVal, double CutoffVal)
	{
		if (Math.abs(inputVal) > Math.abs(CutoffVal))
		{
			if (inputVal > 0)
			{
				return Math.abs(CutoffVal);
			}
			else
			{
				return (Math.abs(CutoffVal) * -1);
			}
		}
		else
		{
			return inputVal;
		}
	}
	
	public static double deadzone(double input, double minVal)
	{
		if (Math.abs(input) < minVal)
		{
			return 0;
		}
		else
		{
			return input;
		}
	}
	
	public static double getModifiedAxis(double input)
	{
		if (input < 0)
		{
			return (input * input * -1);
		}
		else
		{
			return (input * input);
		}
		
	}
}
