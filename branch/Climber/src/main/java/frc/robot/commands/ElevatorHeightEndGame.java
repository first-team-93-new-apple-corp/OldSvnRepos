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
import frc.robot.subsystems.Elevator;

/**
 * 
 * this command lowers the elevator during end game to lift robot higher
 * 
 */

public class ElevatorHeightEndGame extends Command {
  private double m_height;
  public static double lastHeight;
  private Timer timeElapser;
  private double elevatorSpeed;
  private double previousElevatorVal;
  private double previousTimeVal;
  
  public ElevatorHeightEndGame(double height) {
    requires(Robot.elevator);
    m_height = height;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Elevator.endGamePID.reset();
    Elevator.endGamePID.setAbsoluteTolerance(Elevator.Tolerance);
    Elevator.endGamePID.setSetpoint(m_height);
    Elevator.endGamePID.enable();
    timeElapser.reset();
    timeElapser.start();
    previousTimeVal = -0.001;
    previousElevatorVal = Elevator.elevatorEncoder.get();
  }
  

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    elevatorSpeed = (Math.abs(Elevator.elevatorEncoder.pidGet() - previousElevatorVal)) / Math.abs(timeElapser.get()-previousTimeVal);
    previousElevatorVal = Elevator.elevatorEncoder.pidGet();
    previousTimeVal = timeElapser.get();
  }
  // figures out the elevator speed 

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Elevator.endGamePID.onTarget() && elevatorSpeed < 0.5;
  }
  // ends when the elevator has reach its destiny height and isn't going too fast

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Elevator.endGamePID.disable();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
