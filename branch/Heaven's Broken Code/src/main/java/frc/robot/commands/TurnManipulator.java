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

public class TurnManipulator extends Command{

  private double m_degrees;
  private double finalVoltage;
  private double offset = 10; //change offset later
  private Timer timeElapser;
  private double manipulatorSpeed;
  private double previousManipulatorVal;
  private double previousTimeVal;
  private double maxVoltage = 4.8;
  private double minVoltage = 0.2;
  private double range = maxVoltage - minVoltage;

  private void degreesToVolts(){
    if (m_degrees + offset < 360){
      m_degrees += offset;
    }
    else{
      m_degrees = m_degrees + offset - 360;
    }

    finalVoltage = m_degrees * (range/360) + minVoltage;
    //converts degrees to voltage and conpensates for offset of potentiometer
  }

  public TurnManipulator(double degrees) {
    requires(Robot.cargoManipulator);
    m_degrees = degrees;
    degreesToVolts();
    //sets degrees and tolerance to givens and converts degrees to voltage
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    CargoManipulator.manipPID.reset();
    CargoManipulator.manipPID.setAbsoluteTolerance(0.1);
    CargoManipulator.manipPID.setSetpoint(finalVoltage);
    CargoManipulator.manipPID.enable();
    timeElapser.reset();
    timeElapser.start();
    previousTimeVal = -0.001;
    previousManipulatorVal = CargoManipulator.rotateWrist.pidGet();
    //resets and enables PID, and sets setpoint and tolerance
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    manipulatorSpeed = (Math.abs(CargoManipulator.rotateWrist.pidGet() - previousManipulatorVal)) / Math.abs(timeElapser.get()-previousTimeVal);
    previousManipulatorVal = CargoManipulator.rotateWrist.pidGet();
    previousTimeVal = timeElapser.get();
    //calculates velocity and sets new values for the previous time and position
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return CargoManipulator.manipPID.onTarget() && manipulatorSpeed < 0.5 || (!CargoManipulator.wristUp.get() /* TODO: add logic to ensure we're commanding the robot to the topmost position */); 
    // the command ends when the manipulator is the correct location and isn't travelling too fast
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    CargoManipulator.manipPID.disable();
    timeElapser.stop();
    // when command ends the PID and timer is stopped
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
