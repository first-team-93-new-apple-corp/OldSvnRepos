package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc93.Team93Robot2015.RobotMap;

/**
 * This Command Group is the new Autonmous Code written in and for Duluth.
 * 
 * @author Admin93
 */
public class TheNewAutoCommandsDuluth extends CommandGroup {

    public TheNewAutoCommandsDuluth() {

        int AutoPlan = 0;
        for (int i = 0; i < RobotMap.AutoSwitches.length; i++) {
            AutoPlan += (RobotMap.AutoSwitches[i].get()) ? 1 : 0;
        }

        switch (AutoPlan) {
        case 0:
            /*
             * Should do nothing.
             */
            break;
        case 1:// Snatch One Bin
            addSequential(new MoveDrawbridgeOut());
            addSequential(new SleepCommand(4));
            addSequential(new RakeTurnCommandGroup());
            break;

        case 2:// Snatch Two Bins
            addSequential(new MoveDrawbridgeOut());
            addSequential(new SleepCommand(2));
            addSequential(new RakeTurnCommandGroup());
            addSequential(new DriveForward(-368.0, 5.0));
            addSequential(new RakeTurnCommandGroup());
            break;

        case 3:// Grab Bin Reverse to Auto
            addSequential(new ReverseClaw());
            addSequential(new SleepCommand(2));
            addParallel(new RotateContainer(-180.0));
            addSequential(new MoveElevator(100, 10.0));
            addSequential(new DriveForward(-590, 10.0));
            break;

        case 4:// Push Tote to Auto
            addSequential(new DriveForward(700, 10.0));
            break;

        default:
            break;
        }

    }
}
