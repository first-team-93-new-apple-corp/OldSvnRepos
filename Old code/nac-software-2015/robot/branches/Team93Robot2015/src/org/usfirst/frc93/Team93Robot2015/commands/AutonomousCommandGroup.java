/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * 
 *
 * @author NEW Apple Corpses Controls
 */
public class AutonomousCommandGroup extends CommandGroup {

    public AutonomousCommandGroup() {
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

        // Takes 2 bins from Landfill
        // addSequential(new LowerPWD(64));
        addSequential(new UltrasonicSensor(4));
        // addSequential(new GrabBins(90));
        // addSequential(new RaisePWD(20));
        // addParallel(new DriveBackward(1400));
        // addSequential(new LowerPWD(20));
        // addSequential(new ReleaseBins(90));
        // addSequential(new DriveBackward(200));
        // addSequential(new RaisePWD(64));
        // addParallel(new DriveForward(600));
    }

    /**
     * @param camera
     */

    // TODO Auto-generated method stub

}
