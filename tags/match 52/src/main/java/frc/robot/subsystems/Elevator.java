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
import frc.robot.commands.ElevatorContinuous;
import frc.robot.other.CANTalonSRX;
import frc.robot.other.OffsetOutput;
import frc.robot.other.ZeroableEncoder;
import frc.robot.utilities.ElevatorPIDOutput;
import edu.wpi.first.wpilibj.PIDController;

/**
 * creates encoders and speed controllers for the elevator
 */
public class Elevator extends Subsystem {

  public static final double offset = -0.06;

  public static Encoder m_elevatorEncoder;
  public static ZeroableEncoder elevatorEncoder;
  public static CANTalonSRX elevatorMotorLeftController;
  public static CANTalonSRX elevatorMotorRightController;
  public static final double inchesToTicksConversion = 10;//change
  public static final double Tolerance = 5;
  public static ElevatorPIDOutput elevatorPIDOutput;
  public static PIDController ElevatorPID;
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
    m_elevatorEncoder = new Encoder(RobotMap.ElevatorEncoderA, RobotMap.ElevatorEncoderB);
    bottomLimit = new DigitalOutput(RobotMap.elevatorBottomLimit);
    elevatorEncoder = new ZeroableEncoder(m_elevatorEncoder, bottomLimit);
    
    ElevatorPID = new PIDController(-.01, 0, 0, 0, elevatorEncoder, elevatorPIDOutput);
    ElevatorPID.setAbsoluteTolerance(5);
    
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    //setDefaultCommand(new TeleoperatedElevatorControl());


    setDefaultCommand(new ElevatorContinuous());
  }
}
