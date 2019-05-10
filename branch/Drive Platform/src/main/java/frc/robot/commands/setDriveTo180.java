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
import frc.robot.other.SwervePIDOutput;
import frc.robot.subsystems.Drive;

public class setDriveTo180 extends Command {
  public setDriveTo180() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.drive);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

    Drive.rotationPID.setSetpoint(180);
    Drive.rotationPID.setAbsoluteTolerance(20);
    Drive.closeRotationPID.setSetpoint(180);
    Drive.closeRotationPID.setAbsoluteTolerance(20);
    
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    SwervePIDOutput.xOutput.pidWrite(OI.getDriverX() * -1);
    SwervePIDOutput.yOutput.pidWrite(OI.getDriverY() * -1);
    if(Drive.rotationPID.onTarget())
    {
      if(Drive.rotationPID.isEnabled())
      {
        Drive.rotationPID.disable();
      }
      if(!Drive.closeRotationPID.isEnabled())
      {
        Drive.closeRotationPID.enable();
      }
    }
    else
    {
      if(Drive.closeRotationPID.isEnabled())
      {
        Drive.closeRotationPID.disable();
      }
      if(!Drive.rotationPID.isEnabled())
      {
        Drive.rotationPID.enable();
      }
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Drive.rotationPID.disable();
    Drive.closeRotationPID.disable();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }

}
