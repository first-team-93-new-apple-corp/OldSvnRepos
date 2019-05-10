/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class CameraSwitcher extends Command {
  boolean hasRun;
  /**
   * Used to switch cameras
   */
  public CameraSwitcher() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    hasRun = false;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

      System.out.println("Command is running");

      if (hasRun) /**(Robot.server.getSource().getHandle() == Robot.camera1.getHandle()) */
      {
        System.out.print("Setting camera 1\n");
        Robot.server.setSource(Robot.camera2);
        System.out.println(hasRun);
      }
      else/**(Robot.server.getSource().getHandle() == Robot.camera2.getHandle()) */
      {
        System.out.print("Setting camera 2\n");
        Robot.server.setSource(Robot.camera1);
        System.out.println(hasRun);
      }
      hasRun = !hasRun;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
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
  }
}
