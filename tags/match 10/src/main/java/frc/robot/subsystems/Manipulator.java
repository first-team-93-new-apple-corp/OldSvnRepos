/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.utilities.ScaledPIDSource;
import frc.robot.RobotMap;
import frc.robot.commands.ManipulatorContinuous;
import frc.robot.other.CANTalonSRX;
import frc.robot.other.CANVictorSPX;

/**
 * Add your docs here.
 */
public class Manipulator extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public enum Mode{
    HATCH, CARGO
  };

  public static Mode robotMode;

  //WRIST OBJECTS
  public static CANVictorSPX wristRotator; //rotates the entire manipulator
  public static AnalogInput rotateWristSensor; // hall effect sensor that measures the angle of the rotatorpulator
  public static boolean cargoMode; // when robot is picking up cargo (robot is either in cargo mode or hatch mode)
  public static DigitalInput wristUpLimit;
  public static ScaledPIDSource scaledRotateWrist;
  public static PIDController wristPID; //PID FOR CONTROLLING THE WRIST

  //HATCH MANIPULATOR OBJECTS
  public static Servo hatchManipulatorServoRight; // servo that positions in certain area to pick up or remove hatch from different spots
  public static Servo hatchManipulatorServoLeft; // servo that positions in certain area to pick up or remove hatch from different spots

  //KICKER OBJECTS
  public static CANTalonSRX kicker; //rotates downwards to rip off the hatch from the hatch manipulator
  public static AnalogInput kickerSensor; // measures rotation that the hatch remover goes
  public static PIDController removerPID; // the PID for the hatch remover
  public static ScaledPIDSource scaledHatchRemoverRotator;

  //INTAKE OBJECTS
  public static CANVictorSPX cargoIntake; //left-hand motor for the cargo-intake
  public static DigitalInput intakeLightSensor; // light sensor that senses to see if ball is in the cargo intake

  //ENDGAME OBJECTS
  public static CANVictorSPX habLift;
  public static Encoder habLiftEncoder;

  //SAYS WHETHER WE HAVE HATCH OR NOT, USER DEFINES
  public static Boolean haveHatch = false;

  

  public Manipulator()
  {
    robotMode = Mode.CARGO;

    //WRIST OBJECTS
    wristRotator = new CANVictorSPX(RobotMap.Wrist);
    rotateWristSensor = new AnalogInput(RobotMap.WristSensor);
    scaledRotateWrist = new ScaledPIDSource(rotateWristSensor, 100);
    wristPID = new PIDController(-1, 0.00, -0.5, rotateWristSensor, wristRotator);
    wristPID.setAbsoluteTolerance(0.1);
    wristUpLimit = new DigitalInput(RobotMap.wristUpLimit);

    //HATCH MANIPULATOR OBJECTS
    hatchManipulatorServoRight = new Servo(RobotMap.HatchManipulatorServoRight);
    hatchManipulatorServoLeft = new Servo(RobotMap.HatchManipulatorServoLeft);

    //KICKER OBJECTS
    kicker = new CANTalonSRX(RobotMap.kicker);
    kicker.setInverted();
    kickerSensor = new AnalogInput(RobotMap.kickerSensor);
    scaledHatchRemoverRotator = new ScaledPIDSource(kickerSensor, 100);
    removerPID = new PIDController(0.1, 0.1, 0.1, scaledHatchRemoverRotator, kicker);

    //INTAKE OBJECTS
    cargoIntake = new CANVictorSPX(RobotMap.intake);
    intakeLightSensor = new DigitalInput(RobotMap.IntakeLightSensor);

    //ENDGAME OBJECTS
    habLift = new CANVictorSPX(RobotMap.habLift);
    habLiftEncoder = new Encoder(RobotMap.HabLiftEncoderA, RobotMap.HabLiftEncoderB);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ManipulatorContinuous());
  }
}
