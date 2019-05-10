/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.other.CANVictorSPX;

/**
 * Add your docs here.
 */



public class Climber extends Subsystem {
  public static SpeedController scalarMotor;
  public static SpeedController climberDriver;
  public static Encoder scalarEncoder;
  public static PIDController scalarUpPID;
  public static PIDController scalarDownPID;

  

  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public Climber(){
    scalarMotor = new CANVictorSPX(RobotMap.ScalarMotor);
    climberDriver = new CANVictorSPX(RobotMap.ClimberDriver);
    scalarEncoder = new Encoder(RobotMap.ScalarEncoder1, RobotMap.ScalarEncoder2);
    scalarUpPID = new PIDController(0.1, 0.1, 0.1, scalarEncoder, scalarMotor);
    scalarDownPID = new PIDController(0.1, 0.1, 0.1, scalarEncoder, scalarMotor);

  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}


