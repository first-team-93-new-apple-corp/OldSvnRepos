/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.other.SwerveDrive;
import frc.robot.other.SwerveDrive.CentricMode;

public class SwapDriveMode extends Command {
  boolean m_isRobotCentric;
  /**
   * Sets whether the robot is robotcentric or fieldcentric
   * @param isRobotCentric
   * true if setting to robobt centric, false if setting to field centric
   */
  public SwapDriveMode(boolean isRobotCentric) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    m_isRobotCentric = isRobotCentric;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if(m_isRobotCentric)
    {
      SwerveDrive.setRelativeMode(CentricMode.ROBOT);
    }
    else
    {
      SwerveDrive.setRelativeMode(CentricMode.FIELD);
    }
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
