package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.commands.drive.ChangeRobotHeading;
import org.usfirst.frc.team93.robot.commands.drive.CrabDriveSetMode;
import org.usfirst.frc.team93.robot.commands.drive.DriveAndRealign;
import org.usfirst.frc.team93.robot.commands.drive.DriveDirection;
import org.usfirst.frc.team93.robot.commands.gearmanipulator.MoveGearSafe;
import org.usfirst.frc.team93.robot.commands.gearmanipulator.ReleaseGearA;
import org.usfirst.frc.team93.robot.commands.gearmanipulator.ReleaseGearB;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator;
import org.usfirst.frc.team93.robot.utilities.crabdrive.CrabDriveCoordinator;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousDuluth extends CommandGroup
{
	
	/*
	 * Robot dimensions and encoder tpr calculations
	 */
	// robot length is 38 inches = 74 ticks
	private static final int ROBOT_LENGTH = 74;
	// straight forward heading = 0 degrees (no magic numbers)
	private static final int STRAIGHT = 0;
	
	/*
	 * Field dimensions (in encoder ticks)
	 */
	// 220 ticks = 112 inches
	private static final int DISTANCE_TO_BASE_LINE = 114;
	// This is how far forward we'd drive after we turned to deliver a gear
	private static final int DISTANCE_TO_DRIVE_MOBILITY = 9402 / 8;
	private static final int DISTANCE_TO_DRIVE_BEFORE_TURN = 9402 / 12;
	private static final int DISTANCE_TO_DRIVE_AFTER_TURN_BEFORE_CORRECTION = 6210 / 12;
	private static final int DISTANCE_TO_DRIVE_AFTER_TURN_AFTER_CORRECTION = 3017 / 12;
	private static final int DISTANCE_TO_DRIVE_AFTER_GEAR_DELIVERY = -3017 / 12;
	private static final double DISTANCE_DIAGONAL_BASE_LINE_TO_GEAR_LIFT = 0;
	
	// TODO: Error needs to be calculated
	private static final int MAX_ERROR = 10;
	
	/*
	 * Debugging variables
	 */
	private static final boolean USE_PRINTOUTS = false;
	
	public AutonomousDuluth()
	{
		final int NO_AUTONOMOUS = 0;
		final int PLAN_DRIVE_FORWARD_SIDE = 1;
		final int PLAN_DELIVER_GEAR_CENTER = 2;
		final int PLAN_DELIVER_GEAR_LEFT = 3;
		final int PLAN_DELIVER_GEAR_RIGHT = 4;
		final int PLAN_THE_QUITE_IMPOSSIBLE_TWO_GEAR_AUTONOMOUS_THAT_THE_MENTORS_DREAMED_UP = 5;
		
		/*
		 * Add variables here to eliminate magic numbers
		 */
		// Robot Dimensions for Calculations
		
		int autoPlan = PLAN_DRIVE_FORWARD_SIDE;
		// TODO: Calculate angle, check if ccw is positive
		int degrees = autoPlan == PLAN_DELIVER_GEAR_LEFT ? -60 : 60;
		
		addSequential(new CrabDriveSetMode(CrabDriveCoordinator.CrabDriveMode.RobotCentric));
		switch (autoPlan)
		{
		case NO_AUTONOMOUS:
			// Do nothing
			break;
		case PLAN_DRIVE_FORWARD_SIDE:
			addParallel(new MoveGearSafe(GearManipulator.GearLocation.PEG));
			// addSequential(new DriveAndRealignDuration(1.8, 0.6, 0));
			addSequential(new DriveAndRealign(DISTANCE_TO_DRIVE_MOBILITY, MAX_ERROR, STRAIGHT));
			break;
		case PLAN_DELIVER_GEAR_CENTER:
			/*
			 * @codereview josh hawbaker at this point in the match, the intake
			 * motors won't be running. Wouldn't it be more efficient to just
			 * have a move to setpoint line here instead of ReleaseGearA?
			 */
			// moves gear manipulator to peg setpoint and stops intake motors
			addParallel(new ReleaseGearA());
			// drive straight forward to the front of the tower
			addSequential(new DriveDirection(DISTANCE_TO_BASE_LINE - ROBOT_LENGTH, MAX_ERROR, STRAIGHT));
			// release the gear and push it onto the peg
			addSequential(new ReleaseGearB());
			addSequential(new DriveDirection(-ROBOT_LENGTH, MAX_ERROR, STRAIGHT));
			break;
		// left and right gear delivery modes will be the same, but we will
		// request a different heading that is calculated above
		case PLAN_DELIVER_GEAR_LEFT:
		case PLAN_DELIVER_GEAR_RIGHT:
			// drives halfway across the baseline
			addSequential(new DriveAndRealign(DISTANCE_TO_DRIVE_BEFORE_TURN, MAX_ERROR, STRAIGHT));
			// same issue as above - moves to peg setpoint and stops intake
			// motors
			addParallel(new ReleaseGearA());
			// have the robot face the direction we need (will adjust for right
			// / left - calculated above)
			addSequential(new ChangeRobotHeading(degrees));
			// drive forward to the peg
			addSequential(new DriveDirection(DISTANCE_TO_DRIVE_AFTER_TURN_BEFORE_CORRECTION, MAX_ERROR, STRAIGHT));
			addSequential(new ChangeRobotHeading(degrees));
			addSequential(new DriveDirection(DISTANCE_TO_DRIVE_AFTER_TURN_AFTER_CORRECTION, MAX_ERROR, STRAIGHT));
			// place the gear on the peg
			addSequential(new ReleaseGearB());
			addSequential(new DriveDirection(DISTANCE_TO_DRIVE_AFTER_GEAR_DELIVERY, MAX_ERROR, STRAIGHT));
			break;
		case PLAN_THE_QUITE_IMPOSSIBLE_TWO_GEAR_AUTONOMOUS_THAT_THE_MENTORS_DREAMED_UP:
			// addSequential(new
			// UseWitchcraftToSummonAllianceMemberGear());
			
			break;
		default:
		}
	}
}
