package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This command puts the draw bridge up and puts the rake in.
 */
public class BridgeDownFixRake extends CommandGroup {

    public BridgeDownFixRake() {
        addSequential(new MoveDrawbridgeOut());
        addSequential(new SleepCommand(1));
        addParallel(new TurnRake(1.88, 0.1));

    }
}
