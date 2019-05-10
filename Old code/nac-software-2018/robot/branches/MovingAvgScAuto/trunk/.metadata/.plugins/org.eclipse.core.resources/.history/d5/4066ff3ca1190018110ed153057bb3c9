package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class Autonomous extends CommandGroup
{
	public Autonomous()
	{
		if (Robot.WaitBeforeAutonomous.getSelected())
		{
			// wait a bit
		}
		switch (Robot.ScoringTargetPriority.getSelected())
		{
			case SCALE:
				scalePlan();
				break;
			case SWITCH:
				switchPlan();
				break;
			case LEFT:
				leftPlan();
				break;
			case RIGHT:
				rightPlan();
				break;
			case MOBILITY:
				mobilityPlan();
				break;
			case NO_AUTONOMOUS:
				// Do nothing
				break;
		}
	}
	
	private void scalePlan()
	{
		String sFMSData = DriverStation.getInstance().getGameSpecificMessage().substring(1, 1);
		switch (sFMSData)
		{
			case "L":
				switch (Robot.OurStartingPosition.getSelected())
				{
					case LEFT:
						if (Robot.ScoreFromDriverStationSide.getSelected())
						{
							// drive forward
							// turn right
							// drive forward
							// turn left
						}
						else
						{
							// drive forward
							// turn right
						}
						break;
					case CENTER:
						// drive forward
						// turn left
						if (Robot.ScoreFromDriverStationSide.getSelected())
						{
							// drive forward
							// turn right
							// drive forward
							// turn right
							// drive forward
							// turn left
						}
						else
						{
							// drive forward
							// turn right
							// drive forward
							// drive right
						}
						break;
					case RIGHT:
						// drive forward
						// turn left
						if (Robot.ScoreFromDriverStationSide.getSelected())
						{
							// drive forward
							// turn right
						}
						else
						{
							// drive forward
							// turn right
							// drive forward
							// turn right
						}
						break;
				}
				break;
			case "R":
				switch (Robot.OurStartingPosition.getSelected())
				{
					case LEFT:
						// drive forward
						// turn right
						if (Robot.ScoreFromDriverStationSide.getSelected())
						{
							// drive forward
							// turn left
						}
						else
						{
							// drive forward
							// turn left
							// drive forward
							// turn left
						}
						break;
					case CENTER:
						// drive forward
						// turn right
						if (Robot.ScoreFromDriverStationSide.getSelected())
						{
							// drive forward
							// turn left
							// drive forward
							// turn left
							// drive forward
							// turn right
						}
						else
						{
							// drive forward
							// turn left
							// drive forward
							// turn left
						}
						break;
					case RIGHT:
						if (Robot.ScoreFromDriverStationSide.getSelected())
						{
							// drive forward
							// turn left
							// drive forward
							// turn right
						}
						else
						{
							// drive forward
							// turn left
						}
						break;
				}
				break;
		}
		// raise claw
		// drive forward
		// release cube
	}
	
	private void switchPlan()
	{
		String sFMSData = DriverStation.getInstance().getGameSpecificMessage().substring(0, 0);
		switch (sFMSData)
		{
			case "L":
				switch (Robot.OurStartingPosition.getSelected())
				{
					case LEFT:
						if (!Robot.ScoreFromDriverStationSide.getSelected())
						{
							// drive forward
							// turn right
						}
						break;
					case CENTER:
						// drive forward
						// turn left
						if (Robot.ScoreFromDriverStationSide.getSelected())
						{
							// drive forward
							// turn right
						}
						else
						{
							// drive forward
							// turn right
							// drive forward
							// turn right
						}
						break;
					case RIGHT:
						// drive forward
						// turn left
						if (Robot.ScoreFromDriverStationSide.getSelected())
						{
							// drive forward
							// turn right
						}
						else
						{
							// drive forward
							// turn right
							// drive forward
							// turn right
						}
						break;
				}
				break;
			case "R":
				switch (Robot.OurStartingPosition.getSelected())
				{
					case LEFT:
						// drive forward
						// turn right
						if (Robot.ScoreFromDriverStationSide.getSelected())
						{
							// drive forward
							// turn left
						}
						else
						{
							// drive forward
							// turn left
							// drive forward
							// turn left
						}
						break;
					case CENTER:
						// drive forward
						// turn right
						if (Robot.ScoreFromDriverStationSide.getSelected())
						{
							// drive forward
							// turn left
							// drive forward
							// turn left
							// drive forward
							// turn right
						}
						else
						{
							// drive forward
							// turn left
							// drive forward
							// turn left
						}
						break;
					case RIGHT:
						if (Robot.ScoreFromDriverStationSide.getSelected())
						{
							// drive forward
							// turn left
							// drive forward
							// turn right
						}
						else
						{
							// drive forward
							// turn left
						}
						break;
				}
				break;
		}
		// raise claw
		// drive forward
		// release cube
	}
	
	private void leftPlan()
	{
		String sFMSData = DriverStation.getInstance().getGameSpecificMessage().substring(0, 1);
		switch (sFMSData)
		{
			case "RL":
			case "LL":
				scalePlan();
				break;
			case "LR":
				switchPlan();
				break;
			case "RR":
				mobilityPlan();
				break;
		}
	}
	
	private void rightPlan()
	{
		String sFMSData = DriverStation.getInstance().getGameSpecificMessage().substring(0, 1);
		switch (sFMSData)
		{
			case "RR":
			case "LR":
				scalePlan();
				break;
			case "RL":
				switchPlan();
				break;
			case "LL":
				mobilityPlan();
				break;
		}
	}
	
	private void mobilityPlan()
	{
		// Drive forward
	}
}