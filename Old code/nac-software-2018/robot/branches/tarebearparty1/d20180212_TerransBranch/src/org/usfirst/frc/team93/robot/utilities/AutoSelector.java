package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class AutoSelector
{
	public static SendableChooser<location> StartingPosition;
	public static SendableChooser<prioritySelect> priority;
	public static SendableChooser<crossing> CrossSide;
	public static SendableChooser<defaultLocation> DefaultChooser;
	public static SendableChooser<Boolean> LeftSwitch;
	public static SendableChooser<Boolean> RightSwitch;
	public static SendableChooser<Boolean> LeftScale;
	public static SendableChooser<Boolean> RightScale;
	
	enum location
	{
		LEFT, RIGHT, MIDDLE
	}
	
	enum crossing
	{
		BEHIND, AHEAD
	}
	
	enum defaultLocation
	{
		PRIORITY, MOBILITY
	}
	
	enum prioritySelect
	{
		SWITCH, SCALE
	}
	
	public AutoSelector()
	{
		
		StartingPosition = new SendableChooser<location>();
		StartingPosition.addDefault("Left", location.LEFT);
		StartingPosition.addObject("Middle", location.MIDDLE);
		StartingPosition.addObject("Right", location.RIGHT);
		priority = new SendableChooser<prioritySelect>();
		priority.addDefault("SWITCH", prioritySelect.SWITCH);
		priority.addObject("Scale", prioritySelect.SCALE);
		CrossSide = new SendableChooser<crossing>();
		CrossSide.addDefault("Ahead", crossing.AHEAD);
		CrossSide.addObject("Behind", crossing.BEHIND);
		DefaultChooser = new SendableChooser<defaultLocation>();
		DefaultChooser.addDefault("Priority", defaultLocation.PRIORITY);
		DefaultChooser.addObject("Mobility", defaultLocation.MOBILITY);
		LeftSwitch = new SendableChooser<Boolean>();
		LeftSwitch.addDefault("Yes", true);
		LeftSwitch.addObject("No", false);
		RightSwitch = new SendableChooser<Boolean>();
		RightSwitch.addDefault("Yes", true);
		RightSwitch.addObject("No", false);
		LeftScale = new SendableChooser<Boolean>();
		LeftScale.addDefault("Yes", true);
		LeftScale.addObject("No", false);
		RightScale = new SendableChooser<Boolean>();
		RightScale.addDefault("Yes", true);
		RightScale.addObject("No", false);
	}
	
	public Command getAutoCommand()
	{
		String FMSData = DriverStation.getInstance().getGameSpecificMessage().substring(1, 1);
		switch (FMSData)
		{
			case "LL":
				switch (priority.getSelected())
				{
					case SWITCH:
						
						break;
					case SCALE:
						
						break;
				}
		}
		// therkehe = new ahskbjdfekj;
		return null;
	}
	
	public String getTerranGrfrnd()
	{
		return null;
	}
}
