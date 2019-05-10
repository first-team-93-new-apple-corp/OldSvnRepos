/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RaiseRobot extends CommandGroup {
  /**
   * 
   * elevates entire robot to height of third level to be driven onto the platform
   * 
   */

  public RaiseRobot() {
    addSequential(new ElevatorHeight(0));
    addSequential(new DropBar(90));
    addParallel(new ElevatorHeightEndGame(-20)); //change value
    addSequential(new PositionRobot(-20)); //also change height
    addSequential(new DropBar(0));
    addParallel(new ElevatorHeightEndGame(-40)); //change value
    addSequential(new PositionRobot(-40)); //also change height


    

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
