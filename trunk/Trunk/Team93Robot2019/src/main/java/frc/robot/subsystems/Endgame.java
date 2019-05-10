/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.other.CANVictorSPX;
import frc.robot.other.EndgamePIDOutput;

/**
 * Add your docs here.
 */
public class Endgame extends Subsystem {
  public enum ENDGAMEMODE
  {
    INGAME, ENDGAME;
  }
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public static Servo endgameServo;
  public static Servo ratchetServo;
  public static ENDGAMEMODE endgameMode;
public static PIDController balancePID;
public static CANVictorSPX habLift;

  public Endgame()
  {
    endgameServo = new Servo(2);
    ratchetServo = new Servo(3);
    endgameMode = ENDGAMEMODE.INGAME;
    habLift = new CANVictorSPX(RobotMap.habLift);

    balancePID = new PIDController(0.2, 0, 0, Drive.rollAsPIDSource, new EndgamePIDOutput());
    balancePID.setSetpoint(0);
  }


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
