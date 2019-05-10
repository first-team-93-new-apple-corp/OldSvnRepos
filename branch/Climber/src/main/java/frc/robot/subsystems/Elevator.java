/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;


import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.TeleoperatedElevatorControl;
import frc.robot.other.CANVictorSPX;
import frc.robot.other.OffsetOutput;
import frc.robot.utilities.ElevatorPIDOutput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Servo;

/**
 * creates encoders and speed controllers for the elevator
 */
public class Elevator extends Subsystem {

  public static final double OffsetOutput = 0.2;

  public static Encoder elevatorEncoder;
  public static CANVictorSPX elevatorMotorLeftController;
  public static CANVictorSPX elevatorMotorRightController;
  public static final double inchesToTicksConversion = 10;
  public static final double Tolerance = 5;
  public static ElevatorPIDOutput ElevatorPIDOutput;
  public static PIDController ElevatorPIDUp;
  public static PIDController ElevatorPIDDown;
  public static OffsetOutput elevatorMotorLeft;
  public static OffsetOutput elevatorMotorRight;
  public static PIDController endGamePID;
  public static Servo barDrop;

  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public Elevator(){
    elevatorEncoder = new Encoder(RobotMap.ElevatorEncoderA, RobotMap.ElevatorEncoderB);
    elevatorMotorLeftController = new CANVictorSPX(RobotMap.ElevatorSpeedControllerLeft);
    elevatorMotorRightController = new CANVictorSPX(RobotMap.ElevatorSpeedControllerRight);
    ElevatorPIDUp = new PIDController(0.1, 0.1, 0.1, elevatorEncoder, ElevatorPIDOutput);
    ElevatorPIDDown = new PIDController(0.1, 0.1, 0.1, elevatorEncoder, ElevatorPIDOutput);
    endGamePID = new PIDController(0.1, 0.1, 0.1, elevatorEncoder, ElevatorPIDOutput);

    elevatorMotorRight = new OffsetOutput(elevatorMotorRightController, OffsetOutput);
    elevatorMotorLeft = new OffsetOutput(elevatorMotorLeftController, OffsetOutput);

    ElevatorPIDOutput = new ElevatorPIDOutput(5, Elevator.elevatorMotorLeft, Elevator.elevatorMotorRight);
    
    barDrop = new Servo(RobotMap.BarDrop);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new TeleoperatedElevatorControl());
  }
}
