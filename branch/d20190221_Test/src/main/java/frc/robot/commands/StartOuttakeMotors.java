/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.Intake;

/**
 * shoots the cargo
 */

public class StartOuttakeMotors extends Command {

  private Timer timer;

  public StartOuttakeMotors() {
    requires(Robot.intake);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    timer = new Timer(); 
    timer.reset();
    timer.start();
    //creates, resets, and starts timer
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Intake.cargoIntake.set(1.0);
    //starts motors to outtake, signs may need to be flipped
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    // return ((Intake.lightSensor.get()) && timer.hasPeriodPassed(0.5))|| RobotMap.stop;
    return false;
    // return !OI.outtake.isActive;// THIS
    //command ends when when outake is not in manipulator
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    timer.stop();
    Intake.cargoIntake.set(0);
    OI.outtake.isActive = false;
    RobotMap.stop = true;
    //stops motors and timer
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
