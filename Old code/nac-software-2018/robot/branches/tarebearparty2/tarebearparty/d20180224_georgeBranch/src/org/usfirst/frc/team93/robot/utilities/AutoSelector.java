package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import realisticAutoCases.LeftLeft;
import realisticAutoCases.LeftRight;
import realisticAutoCases.LeftRightBehind;
import realisticAutoCases.MidLeft;
import realisticAutoCases.MidRight;
import realisticAutoCases.Mobility;
import realisticAutoCases.RightLeft;
import realisticAutoCases.RightLeftBehind;
import realisticAutoCases.RightRight;

public class AutoSelector
{
	public static SendableChooser<location> StartingPosition;
	public static SendableChooser<prioritySelect> priority;
	public static SendableChooser<path> whichPath;
	
	enum location
	{
		LEFT, RIGHT, MIDDLE
	}
	
	enum prioritySelect
	{
		SWITCH, MOBILITY
	}
	
	enum path
	{
		FRONT, BEHIND
	}
	
	public AutoSelector()
	{
		
		StartingPosition = new SendableChooser<location>();
		StartingPosition.addDefault("Left", location.LEFT);
		StartingPosition.addObject("Middle", location.MIDDLE);
		StartingPosition.addObject("Right", location.RIGHT);
		priority = new SendableChooser<prioritySelect>();
		priority.addDefault("SWITCH", prioritySelect.SWITCH);
		priority.addObject("MOBILITY", prioritySelect.MOBILITY);
		whichPath = new SendableChooser<path>();
		whichPath.addDefault("Front", path.FRONT);
		whichPath.addObject("Behind", path.BEHIND);
		SmartDashboard.putNumber("TimeBeforeAuto", 0);
	}
	
	public Command getAutoCommand()
	{
		String sFMSData = DriverStation.getInstance().getGameSpecificMessage().substring(1, 1);
		if (priority.getSelected() == prioritySelect.SWITCH)
		{
			if (sFMSData.equals("RL") || sFMSData.equals("RR"))
			{
				switch (StartingPosition.getSelected())
				{
					case LEFT:
						switch (whichPath.getSelected())
						{
							case FRONT:
								return (new LeftRight());
							
							case BEHIND:
								return (new LeftRightBehind());
							
						}
					case MIDDLE:
						return (new MidRight());
					default:
						return (new RightRight());
				}
			}
			else
			{
				switch (StartingPosition.getSelected())
				{
					case LEFT:
						return (new LeftLeft());
					case MIDDLE:
						return (new MidLeft());
					default:
						switch (whichPath.getSelected())
						{
							case FRONT:
								return (new RightLeft());
							
							case BEHIND:
								return (new RightLeftBehind());
							
						}
				}
			}
		}
		else
		{
			return (new Mobility());
		}
		return null;
	}
}
