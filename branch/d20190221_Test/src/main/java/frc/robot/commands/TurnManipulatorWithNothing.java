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
import frc.robot.subsystems.CargoManipulator;


/**
 * turns whole manipulator to given degree
 */

public class TurnManipulatorWithNothing extends Command{
  private double finalVoltage;
  private Timer timeElapser;
  private double manipulatorSpeed;
  private double previousManipulatorVal;
  private double previousTimeVal;


  public TurnManipulatorWithNothing(double voltage) {
    requires(Robot.cargoManipulator);
    finalVoltage = voltage;
    //sets degrees and tolerance to givens and converts degrees to voltage
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    timeElapser = new Timer();
    CargoManipulator.manipPIDEmpty.reset();
    CargoManipulator.manipPIDEmpty.setAbsoluteTolerance(0.1);
    CargoManipulator.manipPIDDownEmpty.reset();
    CargoManipulator.manipPIDDownEmpty.setAbsoluteTolerance(0.1);
    if(finalVoltage < CargoManipulator.rotateWrist.getVoltage()){
      CargoManipulator.manipPIDEmpty.setSetpoint(finalVoltage);
      CargoManipulator.manipPIDEmpty.enable();
    }
    else{
      CargoManipulator.manipPIDDownEmpty.setSetpoint(finalVoltage);
      CargoManipulator.manipPIDDownEmpty.enable();
    }
    timeElapser.reset();
    timeElapser.start();
    previousTimeVal = -0.001;
    previousManipulatorVal = CargoManipulator.rotateWrist.pidGet();
    //resets and enables PID, and sets setpoint and tolerance
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    System.out.println(finalVoltage);
    manipulatorSpeed = (Math.abs(CargoManipulator.rotateWrist.pidGet() - previousManipulatorVal)) / Math.abs(timeElapser.get()-previousTimeVal);
    previousManipulatorVal = CargoManipulator.rotateWrist.pidGet();
    previousTimeVal = timeElapser.get();
    //calculates velocity and sets new values for the previous time and position
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return (((CargoManipulator.manipPIDDownEmpty.isEnabled() && CargoManipulator.manipPIDDownEmpty.onTarget()) || (CargoManipulator.manipPIDEmpty.isEnabled() && CargoManipulator.manipPIDEmpty.onTarget())) && manipulatorSpeed < 0.5) || (!CargoManipulator.wristUp.get() && previousManipulatorVal < 1.35); 
    // the command ends when the manipulator is the correct location and isn't travelling too fast
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    CargoManipulator.manipPIDEmpty.disable();
    CargoManipulator.manipPIDDownEmpty.disable();
    timeElapser.stop();
    CargoManipulator.rotator.set(-0.05);
    // when command ends the PID and timer is stopped
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
