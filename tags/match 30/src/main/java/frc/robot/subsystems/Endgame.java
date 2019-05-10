/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

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

  public Endgame()
  {
    endgameServo = new Servo(2);
    ratchetServo = new Servo(3);
    endgameMode = ENDGAMEMODE.INGAME;
  }


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
