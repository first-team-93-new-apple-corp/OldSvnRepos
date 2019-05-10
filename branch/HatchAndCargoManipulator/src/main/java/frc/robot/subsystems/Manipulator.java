/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogOutput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.other.CANVictorSPX;


/**
 * Add your docs here.
 */
public class Manipulator extends Subsystem {
  
public static CANVictorSPX leftCargoIntake; //left-hand motor for the cargo-intake
public static CANVictorSPX rightCargoIntake; //right-hand motor for the cargo-intake
public static CANVictorSPX rotator; //rotates the entire manipulator
public static CANVictorSPX hatchRemover; //rotates downwards to rip off the hatch from the hatch manipulator
public static Servo hatchManip; // servo that positions in certain area to pick up or remove hatch from different spots
public static AnalogPotentiometer rotatePotentiometer; // potentiometer that measure the angle of the rotator
public static AnalogPotentiometer hatchRemoverPot; // measures rotation that the hatch remover goes
public static AnalogOutput lightSensor; // light sensor that senses to see if ball is in the cargo intake
public static PIDController removerPID; // the PID for the hatch remover
public static PIDController manipPID; // the PID for the rotation of the entire manipulator
public static boolean cargoMode; // when robot is picking up cargo (robot is either in cargo mode or hatch mode)
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public Manipulator(){
    
    leftCargoIntake = new CANVictorSPX(RobotMap.LeftIntake);
    rightCargoIntake = new CANVictorSPX(RobotMap.RightIntake);
    rotator = new CANVictorSPX(RobotMap.Rotator);
    hatchRemover = new CANVictorSPX(RobotMap.HatchRemover);
    hatchManip = new Servo(RobotMap.HatchManip);
    rotatePotentiometer = new AnalogPotentiometer(RobotMap.RotateManip);
    hatchRemoverPot = new AnalogPotentiometer(RobotMap.HatchRemoverPot);
    lightSensor = new AnalogOutput(RobotMap.LightSensor);
    removerPID = new PIDController(0.1, 0.1, 0.1, hatchRemoverPot, hatchRemover);
    manipPID = new PIDController(0.1, 0.1, 0.1, rotatePotentiometer, rotator);
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
