package org.usfirst.frc.team93.robot.utilities;

import org.usfirst.frc.team93.robot.Auto.LeftToLeftSwitchFromSide;
import org.usfirst.frc.team93.robot.Auto.MiddleToLeftSwitch;
import org.usfirst.frc.team93.robot.Auto.MiddleToRightSwitch;
import org.usfirst.frc.team93.robot.Auto.Mobility;
import org.usfirst.frc.team93.robot.Auto.RightToRightSwitchFromSide;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoSelector
{
	public static SendableChooser<location> StartingPosition;
	public static SendableChooser<prioritySelect> priority;
	public static SendableChooser<path> whichPath;
	
	public final static location startSpot = location.MIDDLE;
	public final static prioritySelect priorityTo = prioritySelect.SWITCH;
	
	enum location
	{
		LEFT, RIGHT, MIDDLE
	}
	
	enum prioritySelect
	{
		SWITCH, SCALE, MOBILITY
	}
	
	enum path
	{
		FRONT, BEHIND
	}
	
	public AutoSelector()
	{
		
		StartingPosition = new SendableChooser<location>();
		StartingPosition.addDefault("Middle", location.MIDDLE);
		StartingPosition.addObject("Left", location.LEFT);
		StartingPosition.addObject("Right", location.RIGHT);
		priority = new SendableChooser<prioritySelect>();
		priority.addDefault("SWITCH", prioritySelect.SWITCH);
		priority.addDefault("Mobility", prioritySelect.MOBILITY);
		// whichPath = new SendableChooser<path>();
		// whichPath.addDefault("Front", path.FRONT);
		// whichPath.addObject("Behind", path.BEHIND);
		SmartDashboard.putNumber("TimeBeforeAuto", 0);
	}
	
	public static Command getAutoCommand()
	{
		String sFMSData = DriverStation.getInstance().getGameSpecificMessage().substring(0, 2);
		if (priorityTo.equals(prioritySelect.MOBILITY))
		{
			return (new Mobility());
		}
		if (sFMSData.equals("RL") || sFMSData.equals("RR"))
		{
			if (startSpot.equals(location.MIDDLE))
			{
				System.out.println("Middle to right switch");
				
				return (new MiddleToRightSwitch());
			}
			else if (startSpot.equals(location.RIGHT))
			{
				return (new RightToRightSwitchFromSide());
			}
			else if (startSpot.equals(location.LEFT))
			{
				return (new Mobility());
			}
		}
		else
		{
			if (startSpot.equals(location.MIDDLE))
			{
				System.out.println("Middle to left switch");
				
				return (new MiddleToLeftSwitch());
			}
			else if (startSpot.equals(location.RIGHT))
			{
				return (new Mobility());
			}
			else if (startSpot.equals(location.LEFT))
			{
				
				return (new LeftToLeftSwitchFromSide());
			}
		}
		// System.out.println(sFMSData);
		return (new Mobility());
	}
}
