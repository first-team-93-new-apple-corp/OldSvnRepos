/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Elevator;

/**
 * 
 * moves the servo so leg inside elevator comes out and stops it from going back in
 * 
 */

public class DropBar extends Command {

  private double m_degrees;
  private double offset = 10;

  public DropBar(double degrees) {
    requires(Robot.elevator);
    m_degrees = degrees;
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
    if (m_degrees + offset < 360){
    m_degrees += offset;
  }
  else{
    m_degrees = m_degrees + offset - 360;
  }
  Elevator.barDrop.set(m_degrees);
  //sets servo to certain degrees
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Elevator.barDrop.stopMotor();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
