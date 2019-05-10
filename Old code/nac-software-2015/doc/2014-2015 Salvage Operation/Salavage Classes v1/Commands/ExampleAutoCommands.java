package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc93.Team93Robot2015.RobotMap;

public class ExampleAutoCommands extends CommandGroup {

    public ExampleAutoCommands() {

        /*
         * Declare the desired autonomous modes here and assign them with a non
         * designated number
         */
        final int PLAN_NO_AUTONOMOUS = 0;
        final int PLAN_STEAL_ONE_BIN = 1;
        final int PLAN_STEAL_TWO_BINS = 2;

        /*
         * Declare variables, clearly named based off of the purpose of them
         * here, with the proper value here
         */
        final double DISTANCE_BETWEEN_FIRST_BINS = -368.0;
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

        default:
            break;
        }
    }
}
