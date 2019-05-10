/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.other.CANVictorSPX;
import frc.robot.utilities.ElevatorPIDOutput;
import frc.robot.utilities.ElevatorPIDSource;
import edu.wpi.first.wpilibj.PIDController;

/**
 * creates encoders and speed controllers for the elevator
 */
public class Elevator extends Subsystem {

  public static Encoder elevatorEncoder;
  public static CANVictorSPX elevatorMotorLeft;
  public static CANVictorSPX elevatorMotorRight;
  public static final double inchesToTicksConversion = 10;
  public static final double Tolerance = 0.1;
  public static ElevatorPIDOutput ElevatorPIDOutput = new ElevatorPIDOutput(5, Elevator.elevatorMotorLeft, Elevator.elevatorMotorRight);
  public static PIDController ElevatorPID;

  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public Elevator(){
    elevatorEncoder = new Encoder(RobotMap.ElevatorEncoderA, RobotMap.ElevatorEncoderB);
    elevatorMotorLeft = new CANVictorSPX(RobotMap.ElevatorSpeedControllerLeft);
    elevatorMotorRight = new CANVictorSPX(RobotMap.ElevatorSpeedControllerRight);
    ElevatorPID = new PIDController(0.1, 0.1, 0.1, elevatorEncoder, ElevatorPIDOutput);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
