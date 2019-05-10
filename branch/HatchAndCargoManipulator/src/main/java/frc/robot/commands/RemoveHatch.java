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
 * 
 * This command moves the hatch remover to a given degree in order to remove the hatch
 * 
 */

public class RemoveHatch extends Command {

  private double m_degrees;
  private double finalVoltage;
  private double offset = 10; //change offset later
  private Timer timeElapser;
  private double removerSpeed;
  private double previousRemoverVal;
  private double previousTimeVal;

  private void degreesToVolts(){
    if (m_degrees + offset < 360){
      m_degrees += offset;
    }
    else{
      m_degrees = m_degrees + offset - 360;
    }

    finalVoltage = m_degrees * (4.5/360) + 0.25;
    //converts degrees to voltage and conpensates for offset of potentiometer
  }

  public RemoveHatch(double degrees) {
    requires(Robot.manipulator); 
    m_degrees = degrees;
    degreesToVolts();
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Manipulator.removerPID.reset();
    Manipulator.removerPID.setAbsoluteTolerance(0.1);
    Manipulator.removerPID.setSetpoint(finalVoltage); 
    Manipulator.removerPID.enable();
    timeElapser.reset();
    timeElapser.start();
    previousTimeVal = -0.001;
    previousRemoverVal = Manipulator.hatchRemoverPot.get();
    //resets and enables PID and timer, sets PID tolerance and setpoint, also sets the initial time and position for velocity calculations
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    removerSpeed = (Math.abs(Manipulator.hatchRemoverPot.pidGet() - previousRemoverVal)) / Math.abs(timeElapser.get()-previousTimeVal);
    previousRemoverVal = Manipulator.hatchRemoverPot.pidGet();
    previousTimeVal = timeElapser.get();
    //calculates velocity and sets new values for the previous time and position
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Manipulator.removerPID.onTarget() && removerSpeed < 0.5;
    // command stops when motor reaches intended angle and isn't travelling too fast
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Manipulator.removerPID.disable();
    timeElapser.stop();
    // when command is ended the PID and timer stop
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
