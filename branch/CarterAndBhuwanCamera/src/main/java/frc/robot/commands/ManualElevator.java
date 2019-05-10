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

public class ManualElevator extends Command {
  private double deadZone = 0.1;
  public ManualElevator() {
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
<<<<<<< .mine
    if(OI.driver.getRawAxis(3) > deadZone){
      System.out.println("Manual Elevator");
      Elevator.elevatorMotorLeftController.set(Math.pow(OI.operator.getRawAxis(3), 3));
      Elevator.elevatorMotorRightController.set(Math.pow(OI.operator.getRawAxis(3), 3));
||||||| .r122
    if(OI.driver.getRawAxis(3) > deadZone){Elevator.elevatorPIDOutput.pidWrite(Math.pow(OI.operator.getRawAxis(3), 3));
=======
    if(OI.driver.getRawAxis(3) > deadZone)
    {
      Elevator.elevatorPIDOutput.pidWrite(Math.pow(OI.operator.getRawAxis(3), 3));
>>>>>>> .r131
      if(!Elevator.bottomLimit.get() && OI.operator.getRawAxis(3) < 0){
        Elevator.elevatorMotorLeftController.set(0);
        Elevator.elevatorMotorRightController.set(0);
      }

    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Elevator.elevatorPIDOutput.pidWrite(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
