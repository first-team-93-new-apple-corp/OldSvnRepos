/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.other.CANVictorSPX;
import frc.robot.utilities.ScaledPIDSource;

/**
 * Add your docs here.
 */
public class CargoManipulator extends Subsystem {
  
public static CANVictorSPX rotator; //rotates the entire manipulator
public static AnalogInput rotateWrist; // hall effect sensor that measures the angle of the rotator
public static PIDController manipPIDEmpty; // the PID for the rotation of the entire manipulator
public static PIDController manipPIDFull; // the PID for the rotation of the entire manipulator
public static boolean cargoMode; // when robot is picking up cargo (robot is either in cargo mode or hatch mode)
public static DigitalInput wristUp;
public static ScaledPIDSource scaledRotateWrist;
public static PIDController manipPIDDownFull;
public static PIDController manipPIDDownEmpty;
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public CargoManipulator(){
    
    rotator = new CANVictorSPX(RobotMap.Rotator);
    rotateWrist = new AnalogInput(RobotMap.RotateManip);
    scaledRotateWrist = new ScaledPIDSource(rotateWrist);
    manipPIDEmpty = new PIDController(-1, 0.00, -0.003, rotateWrist, rotator);
    manipPIDFull = new PIDController(0.1, 0.1, 0.1, rotateWrist, rotator);
    manipPIDDownFull = new PIDController(0.1, 0,1, 0.1, rotateWrist, rotator);
    manipPIDDownEmpty = new PIDController(0.1, 0.1, 0.1, rotateWrist, rotator);
    wristUp = new DigitalInput(RobotMap.wristUpLimit);
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
