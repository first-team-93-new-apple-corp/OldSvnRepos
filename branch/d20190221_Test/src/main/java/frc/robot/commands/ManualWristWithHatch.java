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
import frc.robot.subsystems.CargoManipulator;

public class ManualWristWithHatch extends Command {
  private double deadZone = 0.03;

  public ManualWristWithHatch() {
    requires(Robot.cargoManipulator);
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

    if(Math.abs(OI.operator.getRawAxis(3)) > deadZone){
      System.out.println("manual wrist with hatch");
      if(!CargoManipulator.wristUp.get() && OI.operator.getRawAxis(3) < 0){
        CargoManipulator.rotator.set(0);
      }
      else{
        if(OI.operator.getRawAxis(3) < 0.08){
          if(OI.operator.getRawAxis(3) < 0){
            CargoManipulator.rotator.set(-(Math.pow(OI.operator.getRawAxis(3), 2)) * 1.05);
          }
          else{
            CargoManipulator.rotator.set(Math.pow(OI.operator.getRawAxis(3), 2) * 0.09);
          }
        }
        else{
          if(OI.operator.getRawAxis(3) < 0){
            CargoManipulator.rotator.set(-(Math.pow(OI.operator.getRawAxis(3), 2)) * 0.5);
          }
          else{
            CargoManipulator.rotator.set(Math.pow(OI.operator.getRawAxis(3), 2) * 0.05);
          }
        }
      }
    
    }  
    else{
      if (CargoManipulator.rotateWrist.getVoltage() < 0.8 ) {
        CargoManipulator.rotator.set(-0.14);
      }
      else if (CargoManipulator.rotateWrist.getVoltage() < 1.7) {
        CargoManipulator.rotator.set(-0.2);
      }
      else{
        CargoManipulator.rotator.set(-0.14);

      }
      //test with hatch
      
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
    CargoManipulator.rotator.set(-0.1);
  //   if(CargoManipulator.rotateWrist.getVoltage() < 0.9){
  //     CargoManipulator.rotator.set(-0.3);
  //   }
  //   else if(CargoManipulator.rotateWrist.getVoltage() < 1.8){
  //     CargoManipulator.rotator.set(-0.2);
  //   }
  //   else{
  //     CargoManipulator.rotator.set(-0.1);
  //   }
   }


  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
