/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.RobotMap;

public class CargoPlacer extends CommandGroup {
  /**
   * sets elevator height to height of the level (pre-determined) and add offset of cargo versus hatch level
   * sets manipulator to upright position to place cargo
   */
  public CargoPlacer() {

    addParallel(new ElevatorHeight(RobotMap.m_elevatorHeight + 8.5));
    addParallel(new RemoveHatch(180));
    addParallel(new TurnManipulator(90)); //double check angle
    addSequential(new HatchAngular(180, 0.1)); //double check angle and/or tollerance 
    addSequential(new TurnOffTriggers());

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
