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
 * Intakes cargo 
 */

public class CargoIntake extends Command {

  public CargoIntake() {
    requires(Robot.manipulator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Manipulator.leftCargoIntake.set(-1.0);
    Manipulator.rightCargoIntake.set(1.0);
    //starts motors to intake, signs may need to be flipped
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Manipulator.lightSensor.getVoltage() <= 0.4;
    //command ends when the ball is in the maipulator
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Manipulator.leftCargoIntake.stopMotor();
    Manipulator.rightCargoIntake.stopMotor();
    //stops motors
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
