/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Drive;

public class setLockedAngle extends Command {
  double m_setpoint;
  /**
   * Sets the value of the setpoint in LockRotationDrive
   * @param setpoint
   * the setpoint value
   */
  @Deprecated
  public setLockedAngle(double setpoint) {
    m_setpoint = setpoint;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    OI.lockRotationDrive.lockedAngle = m_setpoint;
    Drive.rotationPID.reset();
    Drive.closeRotationPID.reset();
    //Drive.rotationPID.enable();
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