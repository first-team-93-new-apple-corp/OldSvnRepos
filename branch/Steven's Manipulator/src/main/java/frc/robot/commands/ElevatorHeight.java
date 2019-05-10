/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Elevator;

 
/**
 * This command lifts the elevator up to a given height
 */

public class ElevatorHeight extends Command {

  
  //uses ElevatorPIDSource and ElevatorPIDOutput so that multiple motors can be used with only one PID
  double m_height;

  public ElevatorHeight(double height) {
    requires(Robot.elevator);
    m_height = height;
    // sets default height to input height
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Elevator.ElevatorPID.reset();
		Elevator.ElevatorPID.enable();
    Elevator.ElevatorPID.setAbsoluteTolerance(Elevator.Tolerance);
    Elevator.ElevatorPID.setSetpoint(m_height*Elevator.inchesToTicksConversion);
    Elevator.elevatorEncoderLeft.reset();
    Elevator.elevatorEncoderRight.reset();
    //resets and enables PID, and sets PID tolerance
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(Elevator.ElevatorPID.onTarget() == true){
      return true;
    }
    else{
      return false;
    }
    //makes sure that code doesn't terminate until the elevator is at the required position
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Elevator.ElevatorPID.disable();
    //disables the PID
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
