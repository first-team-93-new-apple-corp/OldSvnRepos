package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.commands.drive.ChangeRobotHeading;
import org.usfirst.frc.team93.robot.commands.drive.CrabDriveResetWheels;
import org.usfirst.frc.team93.robot.commands.drive.CrabDriveSetMode;
import org.usfirst.frc.team93.robot.commands.drive.CrabDriveSetWheels;
import org.usfirst.frc.team93.robot.commands.drive.DriveAndRealign;
import org.usfirst.frc.team93.robot.commands.drive.DriveDirection;
import org.usfirst.frc.team93.robot.commands.gearmanipulator.MoveGear;
import org.usfirst.frc.team93.robot.commands.gearmanipulator.MoveGearSafe;
import org.usfirst.frc.team93.robot.commands.gearmanipulator.OpenClaws;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator.GearLocation;
import org.usfirst.frc.team93.robot.utilities.crabdrive.CrabDriveCoordinator;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This class is used as our autonomous command at the beginning of each game
 * based off of the selection made on SmartDashboard. It uses the selection from
 * SmartDashboard to return the corresponding autonomous command group.
 */
public class Autonomous extends CommandGroup
{
	// ticks per inch
	final double TICKS_PER_INCH = 10.52;
	
	// dimensions in inches
	final double ROBOT_LENGTH = 41.0;
	final double ROBOT_WIDTH = 36.0;
	final double AIRSHIP_SIDE_LENGTH = 40.0;
	final double DRIVER_STATION_TO_AIRSHIP = 114.0;
	final double CENTER_TO_KEY_RED = 54.25;
	final double CENTER_TO_LOADING_ZONE_RED = 77.75;
	final double CENTER_TO_KEY_BLUE = 52.5;
	final double CENTER_TO_LOADING_ZONE_BLUE = 77.325;
	// max error in inches
	final double MAX_ERROR = 0.1;
	
	// The autonomous plans
	public enum AutonomousPlan
	{
		NO_AUTONOMOUS, PLAN_DRIVE_FORWARD_SIDE, PLAN_DELIVER_GEAR_CENTER, PLAN_DELIVER_GEAR_LEFT_RED, PLAN_DELIVER_GEAR_RIGHT_RED, PLAN_DELIVER_GEAR_LEFT_BLUE, PLAN_DELIVER_GEAR_RIGHT_BLUE;
	}
	
	/**
	 * Creates a command group for a given autonomous plan
	 * 
	 * @param plan
	 *            The plan to create a command group for
	 */
	public Autonomous(AutonomousPlan plan)
	{
		addSequential(new CrabDriveSetMode(CrabDriveCoordinator.CrabDriveMode.RobotCentric));
		switch (plan)
		{
			case NO_AUTONOMOUS:
				// Do nothing
				break;
			
			case PLAN_DRIVE_FORWARD_SIDE:
				// calibrate the carriage encoder
				addParallel(new MoveGearSafe(GearManipulator.GearLocation.CHUTE));
				// drive forward 10 feet
				addSequential(new DriveAndRealign(ticks(120), ticks(MAX_ERROR), 0));
				// reset wheels
				addSequential(new CrabDriveResetWheels());
				break;
			
			case PLAN_DELIVER_GEAR_CENTER:
				// calibrates the carriage encoder
				addParallel(new MoveGear(GearLocation.CHUTE));
				// waits one second for the carriage encoder to calibrate
				addSequential(new SleepCommand(1.0));
				
				// put the carriage in peg position
				addParallel(new MoveGear(GearLocation.PEG));
				// drive to the airship
				addSequential(new DriveDirection(ticks(DRIVER_STATION_TO_AIRSHIP - ROBOT_LENGTH), ticks(MAX_ERROR), 0));
				
				// release the gear
				addParallel(new OpenClaws());
				// wait for the gear to drop
				addSequential(new SleepCommand(0.5));
				
				// back up
				addSequential(new DriveDirection(ticks(-ROBOT_LENGTH), ticks(MAX_ERROR), 0));
				
				// reset wheels
				addSequential(new CrabDriveResetWheels());
				break;
			
			// side gear autonomous depends on the plan
			case PLAN_DELIVER_GEAR_LEFT_RED:
				prepareSideGearPlan(plan, 0.0, 0.0);
				break;
			case PLAN_DELIVER_GEAR_RIGHT_RED:
				prepareSideGearPlan(plan, 0.0, 0.0);
				break;
			case PLAN_DELIVER_GEAR_LEFT_BLUE:
				prepareSideGearPlan(plan, 0.0, 0.0);
				break;
			case PLAN_DELIVER_GEAR_RIGHT_BLUE:
				prepareSideGearPlan(plan, 0.0, 0.0);
				break;
			
			// by default do nothing
			default:
				break;
		}
	}
	
	/**
	 * Create a side gear autonomous plan
	 * 
	 * @param plan
	 * @param initialDistanceAdjust
	 * @param turnedDistanceAdjust
	 */
	private void prepareSideGearPlan(AutonomousPlan plan, double initialDistanceAdjust, double turnedDistanceAdjust)
	{
		// see whether the plan is the left or the right gear
		double TURN_DIRECTION = 0.0;
		if (plan == AutonomousPlan.PLAN_DELIVER_GEAR_LEFT_RED || plan == AutonomousPlan.PLAN_DELIVER_GEAR_LEFT_BLUE)
		{
			TURN_DIRECTION = -60.0;
		}
		if (plan == AutonomousPlan.PLAN_DELIVER_GEAR_RIGHT_RED || plan == AutonomousPlan.PLAN_DELIVER_GEAR_RIGHT_BLUE)
		{
			TURN_DIRECTION = 60.0;
		}
		// see whether the plan is starting in the key or the loading zone
		double HORIZONTAL_DISTANCE_FROM_CENTER = 0.0;
		switch (plan)
		{
			case PLAN_DELIVER_GEAR_LEFT_RED:
				HORIZONTAL_DISTANCE_FROM_CENTER = CENTER_TO_LOADING_ZONE_RED;
				break;
			case PLAN_DELIVER_GEAR_RIGHT_RED:
				HORIZONTAL_DISTANCE_FROM_CENTER = CENTER_TO_KEY_RED;
				break;
			case PLAN_DELIVER_GEAR_LEFT_BLUE:
				HORIZONTAL_DISTANCE_FROM_CENTER = CENTER_TO_KEY_BLUE;
				break;
			case PLAN_DELIVER_GEAR_RIGHT_BLUE:
				HORIZONTAL_DISTANCE_FROM_CENTER = CENTER_TO_LOADING_ZONE_RED;
				break;
			default:
				break;
		}
		
		// calculate distance to go forward
		double HORIZONTAL_DISTANCE = HORIZONTAL_DISTANCE_FROM_CENTER + (ROBOT_WIDTH / 2) - (AIRSHIP_SIDE_LENGTH / 2) - (AIRSHIP_SIDE_LENGTH / 4);
		double INITIAL_FORWARD_DISTANCE = DRIVER_STATION_TO_AIRSHIP + (Math.sqrt(3) * AIRSHIP_SIDE_LENGTH / 4) - (Math.sqrt(3) * HORIZONTAL_DISTANCE / 3);
		double TURNED_FORWARD_DISTANCE = 2 * Math.sqrt(3) * HORIZONTAL_DISTANCE / 3;
		
		// calibrate the carriage encoder
		addParallel(new MoveGear(GearLocation.CHUTE));
		// drive before turning to 60 degrees
		addSequential(new DriveDirection(ticks(INITIAL_FORWARD_DISTANCE - (ROBOT_LENGTH / 2) + initialDistanceAdjust), ticks(MAX_ERROR), 0));
		
		// turn to 60 degrees
		addSequential(new ChangeRobotHeading(TURN_DIRECTION));
		
		// move the gear to peg position
		// prepare the wheels to go straight forward so that we don't jerk
		// to the side
		addParallel(new MoveGear(GearLocation.PEG));
		addSequential(new CrabDriveSetWheels(TURN_DIRECTION));
		
		// drive forward to the peg
		addSequential(new DriveDirection(ticks(TURNED_FORWARD_DISTANCE - (ROBOT_LENGTH / 2) + turnedDistanceAdjust), ticks(MAX_ERROR), TURN_DIRECTION, TURN_DIRECTION));
		
		// open the claws to place the gear on the peg
		// wait 0.5 seconds for the gear to drop onto the peg
		addParallel(new OpenClaws());
		addSequential(new SleepCommand(0.5));
		
		// back up
		addSequential(new DriveDirection(ticks(-ROBOT_LENGTH), ticks(MAX_ERROR), TURN_DIRECTION, TURN_DIRECTION));
		
		// reset the wheels to forward
		addSequential(new CrabDriveResetWheels());
	}
	
	/**
	 * Converts a number in inches to drive encoder ticks.
	 */
	private double ticks(double inches)
	{
		return inches * TICKS_PER_INCH;
	}
}
