package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This transitions gears
 */
public class TransitionGear extends CommandGroup {

    /**
     * 
     * @param oldGear  The current gear
     * @param newGear  The gear to transition to
     * @param transitionTime  Time for transition to happen
     */
    public TransitionGear(double oldGear, double newGear, int transitionTime) {

        int cycles = transitionTime / 20;
        double increment = (newGear - oldGear) / cycles;

        for (int i = 0; i <= cycles; i++) {
            addSequential(new DriveContinuousSingleCycle(oldGear
                    + (increment * i)));
        }

        addSequential(new DriveContinuous(newGear));

    }
}
