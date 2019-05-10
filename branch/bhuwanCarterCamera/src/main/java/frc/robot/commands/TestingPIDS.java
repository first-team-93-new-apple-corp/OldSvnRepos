/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TestingPIDS extends CommandGroup {
  /**
   * delete
   */
  public TestingPIDS() {
    addSequential(new TurnManipulatorWithNothing(1.0815));
    // addSequential(new TurnManipulatorWithSomething(2.3));
    // addSequential(new TurnManipulatorWithNothing(1.0815));
    //if the 1.0815 value is wrong, make sure to change it everywhere, to find search for it
    //also comment out all but one because you should only test one at a time
    //all PID values when finalized should be changed in the CargoManipulator subsystem
    //the PID that is not here is already done
    //change the set motor at the end of TurnManipulatorWithSomething if it is wrong, it is in the end method






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
  }
}
