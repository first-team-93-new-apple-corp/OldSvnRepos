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
  public static CANTalonSRX hatchManipulator;

  

  //INTAKE OBJECTS
  public static CANVictorSPX cargoIntake; //left-hand motor for the cargo-intake
  public static DigitalInput intakeLightSensor; // light sensor that senses to see if ball is in the cargo intake

  //ENDGAME OBJECTS
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
    wristPID = new PIDController(-0.7, 0.00, -0.5, rotateWristSensor, wristRotator);
    wristPID.setAbsoluteTolerance(0.1);
    wristUpLimit = new DigitalInput(RobotMap.wristUpLimit);

    //HATCH MANIPULATOR OBJECTS
    hatchManipulator = new CANTalonSRX(RobotMap.hatchOuttake); 

    //INTAKE OBJECTS
    cargoIntake = new CANVictorSPX(RobotMap.intake);
    intakeLightSensor = new DigitalInput(RobotMap.IntakeLightSensor);

    //ENDGAME OBJECTS
    habLiftEncoder = new Encoder(RobotMap.HabLiftEncoderA, RobotMap.HabLiftEncoderB);

    //Hatch outtake objects
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ManipulatorContinuous());
  }
}
