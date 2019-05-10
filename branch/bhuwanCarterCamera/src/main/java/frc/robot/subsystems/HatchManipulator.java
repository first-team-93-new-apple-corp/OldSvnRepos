/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;


/**
 * Add your docs here.
 */
public class HatchManipulator extends Subsystem {
  
public static Servo hatchManipA; // servo that positions in certain area to pick up or remove hatch from different spots
public static Servo hatchManipB; // servo that positions in certain area to pick up or remove hatch from different spots
public static DigitalInput hatchDetectorLeft;
public static DigitalInput hatchDetectorRight;
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public HatchManipulator(){
    
    hatchManipA = new Servo(RobotMap.HatchManipA);
    hatchManipB = new Servo(RobotMap.HatchManipB);
    hatchDetectorLeft = new DigitalInput(RobotMap.HatchDetectorLeft);
    hatchDetectorRight = new DigitalInput(RobotMap.HatchDetectorRight);
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
