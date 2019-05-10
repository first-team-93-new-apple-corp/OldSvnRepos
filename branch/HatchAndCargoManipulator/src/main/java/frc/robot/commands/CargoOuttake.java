/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Manipulator;

/**
 * shoots the cargo
 */

public class CargoOuttake extends Command {

  private Timer timer;

  public CargoOuttake() {
    requires(Robot.manipulator);
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
    Manipulator.leftCargoIntake.set(1.0);
    Manipulator.rightCargoIntake.set(-1.0);
    //starts motors to outtake, signs may need to be flipped
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return timer.get() > 0.5 && Manipulator.lightSensor.getVoltage() >= 4.5;
    //command ends after half a second 
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    timer.stop();
    Manipulator.leftCargoIntake.set(0);
    Manipulator.rightCargoIntake.set(0);
    //stops motors and timer
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
