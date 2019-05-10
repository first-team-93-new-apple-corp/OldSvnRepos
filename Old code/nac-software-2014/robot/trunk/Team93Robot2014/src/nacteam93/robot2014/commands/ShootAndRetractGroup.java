/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nacteam93.robot2014.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author NEW Apple Corps Controls
 */
public class ShootAndRetractGroup extends CommandGroup {
    
    public ShootAndRetractGroup() {
        requires(CommandBase.shooter);
        requires(CommandBase.bacquisition);
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
        addSequential(new FireShooter());
        addSequential(new retractionGroup());
    }
}
