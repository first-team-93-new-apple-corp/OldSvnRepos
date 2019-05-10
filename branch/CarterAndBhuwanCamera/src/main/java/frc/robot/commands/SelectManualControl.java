/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;

public class SelectManualControl extends Command {
  public SelectManualControl() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    OI.hatchManipulatorManual.isActive = false;
    OI.elevatorManual.isActive = false;
    OI.wristManual.isActive = false;
    OI.wristManualWithCargo.isActive = false;
    OI.wristManualWithHatch.isActive = false;
    OI.kickerManual.isActive = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(OI.operator.getPOV() == 90){
      OI.hatchManipulatorManual.isActive = true;
      OI.elevatorManual.isActive = false;
      OI.wristManual.isActive = false;
      OI.wristManualWithCargo.isActive = false;
      OI.wristManualWithHatch.isActive = false;
      OI.kickerManual.isActive = false;
    }
    else if(OI.operator.getPOV() == 270){
      OI.wristManual.isActive = true;
      OI.elevatorManual.isActive = false;
      OI.hatchManipulatorManual.isActive = false;
      OI.wristManualWithCargo.isActive = false;
      OI.wristManualWithHatch.isActive = false;
      OI.kickerManual.isActive = false;
    }
    else if(OI.operator.getPOV() == 225) {
      OI.wristManual.isActive = false;
      OI.elevatorManual.isActive = false;
      OI.hatchManipulatorManual.isActive = false;
      OI.wristManualWithCargo.isActive = true;
      OI.wristManualWithHatch.isActive = false;
      OI.kickerManual.isActive = false;
    }
    else if(OI.operator.getPOV() == 180) {
      OI.wristManual.isActive = false;
      OI.elevatorManual.isActive = false;
      OI.hatchManipulatorManual.isActive = false;
      OI.wristManualWithCargo.isActive = false;
      OI.wristManualWithHatch.isActive = true;
      OI.kickerManual.isActive = false;
    }
    else if (OI.operator.getPOV() == 0) {
      OI.wristManual.isActive = false;
      OI.elevatorManual.isActive = false;
      OI.hatchManipulatorManual.isActive = false;
      OI.wristManualWithCargo.isActive = false;
      OI.wristManualWithHatch.isActive = false;
      OI.kickerManual.isActive = true;
    }
    else{
      OI.elevatorManual.isActive = true;
      OI.wristManual.isActive = false;
      OI.hatchManipulatorManual.isActive = false;
      OI.wristManualWithCargo.isActive = false;
      OI.wristManualWithHatch.isActive = false;
      OI.kickerManual.isActive = false;
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
    OI.hatchManipulatorManual.isActive = false;
    OI.elevatorManual.isActive = false;
    OI.wristManual.isActive = false;
    OI.wristManualWithCargo.isActive = false;
    OI.wristManualWithHatch.isActive = false;
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
