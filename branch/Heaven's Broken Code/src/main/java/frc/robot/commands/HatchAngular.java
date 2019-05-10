/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.HatchManipulator;

/**
 * This command moves the hatch manipulator to a given degree
 */

public class HatchAngular extends Command {

  private double m_degreesA;
  private double m_degreesB;
  private double m_tolerance;
  private double offsetA = 10;
  private double offsetB = 10;
  private boolean outOfRange;

  public HatchAngular(double degrees, double tolerance) {
    requires(Robot.hatchManipulator);
    m_degreesA = degrees;
    m_degreesB = degrees;
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
    m_degreesA += offsetA;
    m_degreesB = 270 - m_degreesB - offsetB;
    if (m_degreesA < 0 || m_degreesA > 270 || m_degreesB < 0 || m_degreesB > 270){
      System.out.println("Error: Servo angle out of range");
      outOfRange = true;
    }
    else{
      HatchManipulator.hatchManipA.setAngle(m_degreesA);
      HatchManipulator.hatchManipB.setAngle(m_degreesB);
      outOfRange = false;
    }
    //sets servo to certain degrees
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return (HatchManipulator.hatchManipA.getAngle() > (m_degreesA - m_tolerance) && 
    HatchManipulator.hatchManipA.getAngle() < (m_degreesA + m_tolerance)) ||
    outOfRange;
    // command ends when turned degrees is within tolerance
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    HatchManipulator.hatchManipA.stopMotor();
    HatchManipulator.hatchManipB.stopMotor();
    // when command ends the servo is turned off
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
