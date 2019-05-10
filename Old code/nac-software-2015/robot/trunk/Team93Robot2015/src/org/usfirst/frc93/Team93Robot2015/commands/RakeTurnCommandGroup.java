package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This command sets the rake to a certain input, based off of values inside the
 * class.
 * 
 */
public class RakeTurnCommandGroup extends CommandGroup {

    public RakeTurnCommandGroup() {

        // RobotMap.rakeControl.setOutputRange(-0.5, 0.0);

        double setPointZero = 1.88;
        double setPointOne = 3.8;// 4.13; // These are values on the encoders.
        double setPointTwo = 1.25;
        double maxError = 0.15; // This is how picky it is.
        double waitTime = 0.5; // This is how long it waits between setpoints.

        double setpointZeroPValue = -0.5;
        double setpointZeroIValue = -0.5;
        double setpointZeroDValue = 0.0;

        double setpointOnePValue = -2.0; // These control the PIDs
        double setpointOneIValue = -0.5;
        double setpointOneDValue = 0.0;

        double setpointTwoPValue = -0.5;
        double setpointTwoIValue = -0.1;
        double setpointTwoDValue = 0.0;

        /**
         * @codereivew ColbyMcKinley: These seem to be unneeded comments, do we
         *             need or want them?
         */
        // Add Commands here:
        // e.g. addSequential(new Command1());
        // addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        // addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.

        // These go in order, from top to bottom.
        addSequential(new TurnRake(setPointZero, maxError, setpointZeroPValue,
                setpointZeroIValue, setpointZeroDValue));

        addSequential(new TurnRake(setPointOne, maxError, setpointOnePValue,
                setpointOneIValue, setpointOneDValue));

        addSequential(new SleepCommand(waitTime));
        addSequential(new TurnRake(setPointTwo, maxError, setpointTwoPValue,
                setpointTwoIValue, setpointTwoDValue));
    }
}