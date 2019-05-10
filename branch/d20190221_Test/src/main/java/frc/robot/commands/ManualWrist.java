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

public class ManualWrist extends Command {
  private double deadZone = 0.03;

  public ManualWrist() {
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
    // System.out.println("Joystick value: " + OI.operator.getRawAxis(3));
    // System.out.println(CargoManipulator.rotateWrist.getVoltage());
    // System.out.println("command be running Ben");
    // System.out.println("Speed: " + CargoManipulator.rotator.get());
    if(Math.abs(OI.operator.getRawAxis(3)) > deadZone){
      System.out.println("manual wrist");
      if(!CargoManipulator.wristUp.get() && OI.operator.getRawAxis(3) < 0){
        CargoManipulator.rotator.set(0);
      }
      else{
        if(Math.abs(OI.operator.getRawAxis(3)) < 0.37){
          if(OI.operator.getRawAxis(3) < 0){
            CargoManipulator.rotator.set(-(Math.pow(OI.operator.getRawAxis(3), 2)) * 2.9);
          }
          else{
            CargoManipulator.rotator.set(Math.pow(OI.operator.getRawAxis(3), 2) * 0.19);
          }
        }
        else{
          if(OI.operator.getRawAxis(3) < 0){
            CargoManipulator.rotator.set((Math.pow(OI.operator.getRawAxis(3), 3)) * 0.38);
          }
          else{
            CargoManipulator.rotator.set(Math.pow(OI.operator.getRawAxis(3), 2) * 0.19);
          }
        }
      }
    
    }  
    else{
      if (CargoManipulator.rotateWrist.getVoltage() < 0.8 ) {
        CargoManipulator.rotator.set(-0.16);
      }
      else if (CargoManipulator.rotateWrist.getVoltage() < 1.7) {
        CargoManipulator.rotator.set(-0.155);
      }
      else{
        CargoManipulator.rotator.set(-0.05);

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
    if (CargoManipulator.rotateWrist.getVoltage() < 0.8 ) {
      CargoManipulator.rotator.set(-0.16);
    }
    else if (CargoManipulator.rotateWrist.getVoltage() < 1.7) {
      CargoManipulator.rotator.set(-0.155);
    }
    else{
      CargoManipulator.rotator.set(-0.05);

    }
   // CargoManipulator.rotator.set(-0.1);
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
    System.out.println("wrist interrupted");
    this.end();
    
  }
}
