package org.usfirst.frc93.FinalRobot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc93.FinalRobot.commands.DriveCommand;
import org.usfirst.frc93.FinalRobot.commands.FireDiscCommand;
import org.usfirst.frc93.FinalRobot.commands.RotateCommand;
import org.usfirst.frc93.FinalRobot.commands.ShooterGroup;

/**
 *
 * @author Controls
 */
public class LowCloseGroup extends CommandGroup {
  
    public LowCloseGroup() {
        addParallel(new ShooterGroup());
        addSequential(new DriveCommand(320)); //.075 inches per tick
        addSequential(new RotateCommand(90));//Degrees
        addSequential (new DriveCommand(1520));//.075 inches per tick
        addSequential(new RotateCommand(55));//Degrees
        addSequential(new FireDiscCommand());// Fire
        addSequential(new FireDiscCommand());//Fire 
        addSequential(new FireDiscCommand());// FIRE

    }
}
