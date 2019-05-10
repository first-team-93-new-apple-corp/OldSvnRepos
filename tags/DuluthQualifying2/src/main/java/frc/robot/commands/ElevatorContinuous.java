/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.Elevator;

public class ElevatorContinuous extends Command {
  /**
   * 
   * manual controls for moving the elevator up and down
   * 
   */

  public ElevatorContinuous() {
    requires(Robot.elevator);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // System.out.println("Motor speed: " +
    // Elevator.elevatorMotorLeftController.get());
    // System.out.println(OI.operator.getRawAxis(3));
    // System.out.println("limit switch is: " + Elevator.bottomLimit.get());
    if (OI.operator.getPOV() == -1) 
    {
      
        // System.out.println("Manual Elevator");
        // moves elevator up based on joystick values
      if (Elevator.bottomLimit.get() && -OI.getOperatorStick() > 0)
      {
        // Elevator.elevatorMotorLeftController.set(0);
        // Elevator.elevatorMotorRightController.set(0);
        Elevator.elevatorMotorLeftController.set(-OI.getOperatorStick());
        Elevator.elevatorMotorRightController.set(-OI.getOperatorStick());
      }
      else
      {
        Elevator.elevatorMotorLeftController.set(-OI.getOperatorStick());
        Elevator.elevatorMotorRightController.set(-OI.getOperatorStick());
      }
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
    // command stops when button stops being held
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Elevator.elevatorMotorLeftController.set(0);
    Elevator.elevatorMotorRightController.set(0);
    // Elevator.elevatorPIDOutput.pidWrite(0); //TODO: check this for PIDs and
    // comment when you understand

  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
