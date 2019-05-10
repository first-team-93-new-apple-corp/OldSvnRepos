/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.other.CANVictorSPX;

/**
 * Add your docs here.
 */
public class Intake extends Subsystem {
  public static CANVictorSPX cargoIntake; //left-hand motor for the cargo-intake
  public static DigitalInput lightSensor; // light sensor that senses to see if ball is in the cargo intake

  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public Intake(){
    cargoIntake = new CANVictorSPX(RobotMap.intake);
    lightSensor = new DigitalInput(RobotMap.LightSensor);

  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
