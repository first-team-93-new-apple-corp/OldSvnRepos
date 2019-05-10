package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc93.Team93Robot2015.RobotMap;

/**
 * This command runs the autonomous code based off of a manual input in the code
 * or switches on the robot. As of April 18, 2015 are eight (8) tested plans.
 * Testers include Conor, Colby, Grant, and Evans.
 */
public class AutoCommandsMilwaukee extends CommandGroup {

    public AutoCommandsMilwaukee() {

        /*
         * Declare the desired autonomous modes here and assign them with a non
         * designated number
         */
        final int PLAN_NO_AUTONOMOUS = 0;
        final int PLAN_STEAL_ONE_BIN = 1;
        final int PLAN_STEAL_TWO_BINS = 2;
        final int PLAN_STEAL_THREE_BINS = 3;
        final int PLAN_STEAL_FOUR_BINS = 4;
        final int PLAN_STEAL_MIDDLE_TWO_BINS = 5;

        final int MOVE_TO_AUTO = 6;
        final int MOVE_TOTE_TO_AUTO = 7;
        final int MOVE_BIN_TO_AUTO = 8;

        /*
         * Declare variables, clearly named based off of the purpose of them
         * here, with the proper value here
         */
        final double DISTANCE_BETWEEN_FIRST_BINS = -368.0;
        final double DISTANCE_BETWEEN_MIDDLE_BINS = -512.0;
        final double DISTANCE_FROM_TOTE_TO_AUTO = 540.0;
        final double TURN_TO_THIRD_BIN = 15.0;
        final double DISTANCE_TO_MISS_BIN = 17.23865;
        // final double BACKWARD_DISTANCE_FROM_TOTE_TO_AUTO = -540.0;
        final double DRIVER_STATION_TO_AUTO = -700;
        final double DRAWBRIDGE_DELAY_TIME = 2.0;

        /*
         * When not using switches, comment out the for loop and set the desired
         * Autonomous mode to AutoPlan
         */
        int AutoPlan = PLAN_NO_AUTONOMOUS;
        /*
         * When using switches, the for loop will override the AutoPlan value,
         * and make sure that AutoPlan is set to 0 or PLAN_NO_AUTONOMOUS
         */
        for (int i = 0; i < RobotMap.AutoSwitches.length; i++) {
            AutoPlan += (RobotMap.AutoSwitches[i].get()) ? Math.pow(2, i) : 0;
        }

        switch (AutoPlan) {
        case PLAN_NO_AUTONOMOUS:
            // Does nothing
            break;

        case PLAN_STEAL_ONE_BIN:
            addSequential(new MoveDrawbridgeOut());
            addSequential(new SleepCommand(2.0));
            addSequential(new RakeTurnCommandGroup());
            break;

        case PLAN_STEAL_TWO_BINS:
            // Grabs to plan from the edge
            addSequential(new MoveDrawbridgeOut());
            addSequential(new SleepCommand(DRAWBRIDGE_DELAY_TIME));
            addSequential(new RakeTurnCommandGroup());
            addSequential(new DriveForward(DISTANCE_BETWEEN_FIRST_BINS, 10.0));
            addSequential(new RakeTurnCommandGroup());
            break;

        case PLAN_STEAL_THREE_BINS:
            addSequential(new MoveDrawbridgeOut());
            addSequential(new SleepCommand(DRAWBRIDGE_DELAY_TIME));
            addSequential(new RakeTurnCommandGroup());
            addSequential(new DriveForward(DISTANCE_BETWEEN_FIRST_BINS, 10.0));
            addSequential(new RakeTurnCommandGroup());
            addSequential(new TurnLeft(TURN_TO_THIRD_BIN, 10.0));
            addSequential(new DriveForward(DISTANCE_BETWEEN_MIDDLE_BINS, 10.0));
            addSequential(new TurnRight(TURN_TO_THIRD_BIN, 10.0));
            addSequential(new RakeTurnCommandGroup());
            break;

        case PLAN_STEAL_FOUR_BINS:
            addSequential(new MoveDrawbridgeOut());
            addSequential(new SleepCommand(DRAWBRIDGE_DELAY_TIME));
            addSequential(new RakeTurnCommandGroup());
            addSequential(new DriveForward(DISTANCE_BETWEEN_FIRST_BINS, 5.0));
            addSequential(new RakeTurnCommandGroup());
            addSequential(new TurnLeft(TURN_TO_THIRD_BIN, 10.0));
            addSequential(new DriveForward(DISTANCE_BETWEEN_MIDDLE_BINS, 10.0));
            addSequential(new TurnRight(TURN_TO_THIRD_BIN, 10.0));
            addSequential(new RakeTurnCommandGroup());
            addSequential(new DriveForward(DISTANCE_BETWEEN_FIRST_BINS, 5.0));
            addSequential(new RakeTurnCommandGroup());
            break;

        case PLAN_STEAL_MIDDLE_TWO_BINS:
            addSequential(new MoveDrawbridgeOut());
            addSequential(new SleepCommand(DRAWBRIDGE_DELAY_TIME));
            addSequential(new RakeTurnCommandGroup());
            addSequential(new DriveForward(DISTANCE_BETWEEN_MIDDLE_BINS, 5.0));
            addSequential(new RakeTurnCommandGroup());
            break;

        case MOVE_TO_AUTO:
            addSequential(new DriveForward(DISTANCE_FROM_TOTE_TO_AUTO, 10.0));
            break;

        /**
         * @codereview ColbyMcKinley: This auto plan does not seem to do what
         *             the title describes. I do not see us picking up a tote
         *             and moving the robot and tote to the auto zone, I read
         *             that the robot drives forward from the driver station to
         *             the auto zone, empty.
         */
        case MOVE_TOTE_TO_AUTO:
            addSequential(new DriveForward(DRIVER_STATION_TO_AUTO, 10.0));
            break;

        /**
         * @codereview ColbyMcKinley: The concept seems correct, have values 200
         *             and -590 been tested and what is the significance behind
         *             these values? Use variables or comments to justify these
         *             values going there.
         */
        case MOVE_BIN_TO_AUTO:
            addSequential(new ReverseClaw());
            addSequential(new SleepCommand(2.0));
            // addParallel(new RotateContainer(-180.0));
            addSequential(new MoveElevator(200, 30.0));
            addSequential(new DriveForward(-590, 10.0));
            break;

        default:
            break;
        }
    }
}
