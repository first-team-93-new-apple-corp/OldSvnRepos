/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;


import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.other.CANVictorSPX;
import frc.robot.utilities.ScaledPIDSource;


/**
 * Add your docs here.
 */
public class HatchRemoval extends Subsystem {
  
public static CANVictorSPX hatchRemover; //rotates downwards to rip off the hatch from the hatch manipulator
public static AnalogInput hatchRemoverRotator; // measures rotation that the hatch remover goes
public static PIDController removerPID; // the PID for the hatch remover
public static ScaledPIDSource scaledHatchRemoverRotator;
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public HatchRemoval(){
    
    hatchRemover = new CANVictorSPX(RobotMap.HatchRemover);
    hatchRemoverRotator = new AnalogInput(RobotMap.HatchRemoverRotator);
    scaledHatchRemoverRotator = new ScaledPIDSource(hatchRemoverRotator);
    removerPID = new PIDController(0.1, 0.1, 0.1, scaledHatchRemoverRotator, hatchRemover);
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
