package org.usfirst.frc.team93.robot.utilities;

import org.usfirst.frc.team93.robot.Auto.LeftToLeftScale;
import org.usfirst.frc.team93.robot.Auto.LeftToLeftSwitchFromSide;
import org.usfirst.frc.team93.robot.Auto.MiddleToLeftSwitch;
import org.usfirst.frc.team93.robot.Auto.MiddleToRightSwitch;
import org.usfirst.frc.team93.robot.Auto.Mobility;
import org.usfirst.frc.team93.robot.Auto.RightToRightScale;
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
		SmartDashboard.putData("Starting Position", StartingPosition);
		priority = new SendableChooser<prioritySelect>();
		priority.addDefault("SWITCH", prioritySelect.SWITCH);
		priority.addObject("Mobility", prioritySelect.MOBILITY);
		priority.addObject("Scale", prioritySelect.SCALE);
		SmartDashboard.putData("Priority", priority);
		// whichPath = new SendableChooser<path>();
		// whichPath.addDefault("Front", path.FRONT);
		// whichPath.addObject("Behind", path.BEHIND);
		SmartDashboard.putNumber("TimeBeforeAuto", 0);
	}
	
	public static Command getAutoCommand()
	{
		String RawFMSData = DriverStation.getInstance().getGameSpecificMessage();
		if (RawFMSData == null || RawFMSData.length() < 2)
		{
			System.out.println("Null, Mobility");
			return new Mobility();
		}
		String sFMSData = RawFMSData.substring(0, 2);
		System.out.println(sFMSData);
		switch (StartingPosition.getSelected())
		{
			case MIDDLE:
				if (sFMSData.equals("RR") || sFMSData.equals("RL"))
				{
					System.out.println("Middle To Right Switch");
					return (new MiddleToRightSwitch());
				}
				else
				{
					System.out.println("Middle To Left Switch");
					return (new MiddleToLeftSwitch());
				}
			case LEFT:
				switch (priority.getSelected())
				{
					case MOBILITY:
						System.out.println("MOBILITY");
						return (new Mobility());
					case SWITCH:
						if (sFMSData.equals("LR") || sFMSData.equals("LL"))
						{
							System.out.println("Left To Left Switch");
							return new LeftToLeftSwitchFromSide();
						}
						else if (sFMSData.equals("RL"))
						{
							System.out.println("Left to left scale");
							return new LeftToLeftScale();
						}
						else
						{
							System.out.println("MOBILITY");
							return new Mobility();
						}
					case SCALE:
						if (sFMSData.equals("RL") || sFMSData.equals("LL"))
						{
							System.out.println("Left to left scale");
							return new LeftToLeftScale();
						}
						else if (sFMSData.equals("LR"))
						{
							System.out.println("Left to left switch");
							return new LeftToLeftSwitchFromSide();
						}
						else
						{
							System.out.println("MOBILITY");
							return new Mobility();
						}
				}
			case RIGHT:
				switch (priority.getSelected())
				{
					case MOBILITY:
						System.out.println("MOBILITY");
						return new Mobility();
					case SWITCH:
						if (sFMSData.equals("RL") || sFMSData.equals("RR"))
						{
							System.out.println("RIGHT TO RIGHT SWITCH");
							return new RightToRightSwitchFromSide();
						}
						else if (sFMSData.equals("LR"))
						{
							System.out.println("Right to right scale");
							return new RightToRightScale();
						}
						else
						{
							System.out.println("MOBILITY");
							return new Mobility();
						}
					case SCALE:
						if (sFMSData.equals("RR") || sFMSData.equals("LR"))
						{
							System.out.println("Right to right scale");
							return new RightToRightScale();
						}
						else if (sFMSData.equals("RL"))
						{
							System.out.println("Right to right switch");
							return new RightToRightSwitchFromSide();
						}
						else
						{
							System.out.println("MOBILITY");
							return new Mobility();
						}
				}
		}
		return (new Mobility());
	}
}
