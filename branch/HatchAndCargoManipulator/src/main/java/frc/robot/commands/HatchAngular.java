/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Manipulator;

/**
 * This command moves the hatch manipulator to a given degree
 */

public class HatchAngular extends Command {

  private double m_degrees;
  private double m_tolerance;
  private double offset = 10;

  public HatchAngular(double degrees, double tolerance) {
    requires(Robot.manipulator);
    m_degrees = degrees;
    m_tolerance = tolerance;
    //sets degrees and tolerance to givens
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
    Manipulator.hatchManip.setAngle(m_degrees);
    //sets servo to certain degrees
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Manipulator.hatchManip.getAngle() > m_degrees - m_tolerance && Manipulator.hatchManip.getAngle() < m_degrees + m_tolerance;
    // command ends when turned degrees is within tolerance
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Manipulator.hatchManip.stopMotor();
    // when command ends the servo is turned off
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
