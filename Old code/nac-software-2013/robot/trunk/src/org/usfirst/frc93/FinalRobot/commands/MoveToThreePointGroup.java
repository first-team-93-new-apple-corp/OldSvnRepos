/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc93.FinalRobot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author Controls
 */
public class MoveToThreePointGroup extends CommandGroup {
    //Diameter of the wheels is 6 inch
    //255 ticks on an encoder
    public MoveToThreePointGroup() {
        double wheelCircum = 6.0f* 2.0f * 3.14159f; //Inches
        double move2Ft =  24; //Inches
        addParallel(new ShooterGroup());
        addSequential(new DriveCommand((move2Ft/wheelCircum)*255.0f));
        addSequential(new RotateCommand(90));
        double move47in = 47;
        addSequential(new DriveCommand(move47in/wheelCircum*255.0f));
        addSequential(new RotateCommand(-58.6));
        addSequential(new FireDiscCommand());
        addSequential(new FireDiscCommand());
        addSequential(new FireDiscCommand());
    }
}
