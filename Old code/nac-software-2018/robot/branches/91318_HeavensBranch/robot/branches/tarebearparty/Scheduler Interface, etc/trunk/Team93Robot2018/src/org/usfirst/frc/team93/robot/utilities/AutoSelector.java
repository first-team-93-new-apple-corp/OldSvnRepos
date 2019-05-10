package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import realisticAutoCases.LeftLeftScale;
import realisticAutoCases.LeftLeftSwitch;
import realisticAutoCases.LeftRightScaleBefore;
import realisticAutoCases.LeftRightScaleBehind;
import realisticAutoCases.LeftRightSwitchBefore;
import realisticAutoCases.LeftRightSwitchBehind;
import realisticAutoCases.MidLeftScale;
import realisticAutoCases.MidLeftSwitch;
import realisticAutoCases.MidRightScale;
import realisticAutoCases.MidRightSwitch;
import realisticAutoCases.Mobility;
import realisticAutoCases.RightLeftScaleBefore;
import realisticAutoCases.RightLeftScaleBehind;
import realisticAutoCases.RightLeftSwitchBefore;
import realisticAutoCases.RightLeftSwitchBehind;
import realisticAutoCases.RightRightScale;
import realisticAutoCases.RightRightSwitch;

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
		SWITCH, SCALE, MOBILITY
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
		priority.addObject("SCALE", prioritySelect.SCALE);
		whichPath = new SendableChooser<path>();
		whichPath.addDefault("Front", path.FRONT);
		whichPath.addObject("Behind", path.BEHIND);
		SmartDashboard.putNumber("TimeBeforeAuto", 0);
	}
	
	public Command getAutoCommand()
	{
		String sFMSData = DriverStation.getInstance().getGameSpecificMessage().substring(0, 2);
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
								return (new LeftRightSwitchBefore());
							
							case BEHIND:
								return (new LeftRightSwitchBehind());
							
						}
					case MIDDLE:
						return (new MidRightSwitch());
					default:
						return (new RightRightSwitch());
				}
			}
			else
			{
				switch (StartingPosition.getSelected())
				{
					case LEFT:
						return (new LeftLeftSwitch());
					case MIDDLE:
						return (new MidLeftSwitch());
					default:
						switch (whichPath.getSelected())
						{
							case FRONT:
								return (new RightLeftSwitchBefore());
							
							case BEHIND:
								return (new RightLeftSwitchBehind());
							
						}
				}
			}
		}
		else if (priority.getSelected() == prioritySelect.SCALE)
		{
			if (sFMSData.equals("LR") || sFMSData.equals("RR"))
			{
				switch (StartingPosition.getSelected())
				{
					case LEFT:
						switch (whichPath.getSelected())
						{
							case FRONT:
								return (new LeftRightScaleBefore());
							
							case BEHIND:
								return (new LeftRightScaleBehind());
							
						}
					case MIDDLE:
						return (new MidRightScale());
					default:
						return (new RightRightScale());
				}
			}
			else
			{
				switch (StartingPosition.getSelected())
				{
					case LEFT:
						return (new LeftLeftScale());
					case MIDDLE:
						return (new MidLeftScale());
					default:
						switch (whichPath.getSelected())
						{
							case FRONT:
								return (new RightLeftScaleBefore());
							
							case BEHIND:
								return (new RightLeftScaleBehind());
							
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
