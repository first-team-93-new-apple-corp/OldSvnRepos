/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;


import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.other.CANTalonSRX;
import frc.robot.other.OffsetOutput;
import frc.robot.utilities.ElevatorPIDOutput;
import edu.wpi.first.wpilibj.PIDController;

/**
 * creates encoders and speed controllers for the elevator
 */
public class Elevator extends Subsystem {

  public static final double offset = 0.2;

  public static Encoder elevatorEncoder;
  public static CANTalonSRX elevatorMotorLeftController;
  public static CANTalonSRX elevatorMotorRightController;
  public static final double inchesToTicksConversion = 10;
  public static final double Tolerance = 5;
  public static ElevatorPIDOutput elevatorPIDOutput;
  public static PIDController ElevatorPIDUp;
  public static PIDController ElevatorPIDDown;
  public static OffsetOutput elevatorMotorLeft;
  public static OffsetOutput elevatorMotorRight;
  public static DigitalOutput bottomLimit;

  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public Elevator(){
    elevatorMotorLeftController = new CANTalonSRX(RobotMap.ElevatorSpeedControllerLeft);
    elevatorMotorRightController = new CANTalonSRX(RobotMap.ElevatorSpeedControllerRight);
    elevatorMotorRight = new OffsetOutput(elevatorMotorRightController, offset);
    elevatorMotorLeft = new OffsetOutput(elevatorMotorLeftController, offset);
    
    elevatorPIDOutput = new ElevatorPIDOutput(5, Elevator.elevatorMotorLeft, Elevator.elevatorMotorRight);
    elevatorEncoder = new Encoder(RobotMap.ElevatorEncoderA, RobotMap.ElevatorEncoderB);
    
    ElevatorPIDUp = new PIDController(0.1, 0.1, 0.1, elevatorEncoder, elevatorPIDOutput);
    ElevatorPIDDown = new PIDController(0.1, 0.1, 0.1, elevatorEncoder, elevatorPIDOutput);

    

    


    bottomLimit = new DigitalOutput(RobotMap.elevatorBottomLimit);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    //setDefaultCommand(new TeleoperatedElevatorControl());
  }
}
