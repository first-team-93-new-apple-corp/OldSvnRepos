/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.Manipulator;

/**
 * command figures out if there is cargo or there is a hatch in the manipulator
 */

public class PlacingOnCS extends Command {

  public PlacingOnCS(double elevatorHeight) {
    requires(Robot.manipulator);
    RobotMap.m_elevatorHeight = elevatorHeight;
    // uses elevator height from robotMap

    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(Manipulator.cargoMode == false)
    {
      OI.hatchPlace.isActive = true;
      OI.cargoPlace.isActive = false;
      RobotMap.m_hatchAngle = 90;
    }
    else
    {
      OI.cargoPlace.isActive = true;
      OI.hatchPlace.isActive = false;
      RobotMap.m_elevatorHeight = RobotMap.cargoCargoShipHeight - 8.5; //changes height for placing cargo since the height difference isn't the same as the rocket
    }
    //determines if in cargo mode or not
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
