/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nacteam93.robot2014.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author Controls
 */
public class retractionGroup extends CommandGroup {
    
    public retractionGroup() {
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
        addSequential(new SetShooterDogGear(false));
        addSequential(new timedRetraction(1500));
        addSequential(new RetractToSwitch());
    }
}