/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.other.CANVictorSPX;

/**
 * Add your docs here.
 */
public class CargoManipulator extends Subsystem {
  
public static CANVictorSPX leftCargoIntake; //left-hand motor for the cargo-intake
public static CANVictorSPX rightCargoIntake; //right-hand motor for the cargo-intake
public static CANVictorSPX rotator; //rotates the entire manipulator
public static AnalogInput rotateWrist; // hall effect sensor that measures the angle of the rotator
public static DigitalOutput lightSensor; // light sensor that senses to see if ball is in the cargo intake
public static PIDController manipPID; // the PID for the rotation of the entire manipulator
public static boolean cargoMode; // when robot is picking up cargo (robot is either in cargo mode or hatch mode)
public static DigitalInput wristUp;
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public CargoManipulator(){
    
    leftCargoIntake = new CANVictorSPX(RobotMap.LeftIntake);
    rightCargoIntake = new CANVictorSPX(RobotMap.RightIntake);
    rotator = new CANVictorSPX(RobotMap.Rotator);
    rotateWrist = new AnalogInput(RobotMap.RotateManip);
    lightSensor = new DigitalOutput(RobotMap.LightSensor);
    manipPID = new PIDController(0.1, 0.1, 0.1, rotateWrist, rotator);
    wristUp = new DigitalInput(RobotMap.wristUpLimit);
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
