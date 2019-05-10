/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc93.FinalRobot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 * @author Controls
 */
public class StationaryShootingGroup extends CommandGroup {

    public StationaryShootingGroup() {
        addSequential(new setShooterThreePoint());
        addParallel(new ShooterGroup());
        addSequential(new WaitCommand(5)); //Idk if this is rite
        addSequential(new FireDiscCommand());
        addSequential(new FireDiscCommand());
        addSequential(new FireDiscCommand());
        addSequential(new DisableShooterMotors());
    }
    public String getName(){
        return "Stationary Three Point Shot";
    }
}
