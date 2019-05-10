package org.usfirst.frc.team93.robot.utilities;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.subsystems.Drive;

/**
 * 
 * @author ben.fager
 *
 *basically I wrote this so that OI is not so clogged up with the drive chooser being so big
 *
 */
public class DriveChooser 
{
	public static double getDriverL() 
	{
		if(Drive.driveControlMode == 1) 
		{
			return deadzone(OI.driver.getRawAxis(1), OI.ControllerDeadzone);
		}
		else if(Drive.driveControlMode == 2) 
		{
			return deadzone(OI.splitDriverControlOne.getRawAxis(1), OI.ControllerDeadzone);
		}
		else if(Drive.driveControlMode == 3)
		{
			return CutOff((deadzone(OI.driver.getRawAxis(1), OI.ControllerDeadzone) - deadzone(OI.driver.getRawAxis(2), OI.ControllerDeadzone)), OI.JoystickCutoffVal);
		}
		else if(Drive.driveControlMode == 4)
		{
			return CutOff((deadzone(OI.splitDriverControlOne.getRawAxis(1), OI.ControllerDeadzone) - deadzone(OI.splitDriverControlTwo.getRawAxis(0), OI.ControllerDeadzone)), OI.JoystickCutoffVal);
		}
		else
		{
			return (0);
		}
	}
	public static double CutOff(double inputVal, double CutoffVal)
	{
		if(Math.abs(inputVal) > Math.abs(CutoffVal)) 
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
		double m_input = input;
		if(Math.abs(input) < minVal) 
		{
			return 0;
		}
		else
		{
			return input;
		}
	}
	public static double getDriverR()
	{ 
		if(Drive.driveControlMode == 1) 
		{
			return deadzone(OI.driver.getRawAxis(3), OI.ControllerDeadzone);
		}
		else if(Drive.driveControlMode == 2)
		{
			return deadzone(OI.splitDriverControlTwo.getRawAxis(1), OI.ControllerDeadzone);
		}
		else if(Drive.driveControlMode == 3)
		{
			return CutOff((deadzone(OI.driver.getRawAxis(1), OI.ControllerDeadzone) + deadzone(OI.driver.getRawAxis(2), OI.ControllerDeadzone)), OI.JoystickCutoffVal);
		}
		else if (Drive.driveControlMode == 4)
		{
			return CutOff((deadzone(OI.splitDriverControlOne.getRawAxis(1), OI.ControllerDeadzone) + deadzone(OI.splitDriverControlTwo.getRawAxis(0), OI.ControllerDeadzone)), OI.JoystickCutoffVal);
		}
		else
		{
			return (0);
		}
	}
}
