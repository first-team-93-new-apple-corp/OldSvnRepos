package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.Extender;
import frc.robot.commands.Grabber;

public class GrabAndPlace extends CommandGroup{
    public GrabAndPlace(){
        addSequential(new Extender());
        addSequential(new Grabber());
    }
}