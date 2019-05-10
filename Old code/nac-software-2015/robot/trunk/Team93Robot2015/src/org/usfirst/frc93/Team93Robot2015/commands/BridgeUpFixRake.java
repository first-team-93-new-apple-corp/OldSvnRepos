package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This command puts the draw bridge up and puts the rake in.
 */
public class BridgeUpFixRake extends CommandGroup {

    public BridgeUpFixRake() {
        addSequential(new MoveDrawbridgeIn());
        addParallel(new TurnRake(0.20, 0.1));

    }
}
