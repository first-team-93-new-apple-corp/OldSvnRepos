/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.Intake;

/**
 * Intakes cargo 
 */

public class StartIntakeMotors extends Command {

  public StartIntakeMotors() {
    requires(Robot.intake);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Intake.cargoIntake.set(-1.0);
    //starts motors to intake, signs may need to be flipped
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    // return Intake.lightSensor.get() || RobotMap.stop;
    //command ends when the ball is in the manipulator or the button is pressed again
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Intake.cargoIntake.stopMotor();
    RobotMap.stop = true;
    //stops motors
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
