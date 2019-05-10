/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.CargoMover;
import frc.robot.commands.D_HatchMover;
import frc.robot.commands.EntireMovement;

/**
 * Add your docs here.
 */
public class SUBsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public static SpeedController CargoMotor;
  public static SpeedController HatchMover;
  public static SpeedController EntirePivot;
  public static SpeedController CargoMotor2;



  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());

    EntirePivot = new Talon(RobotMap.EntirePivot);
    CargoMotor = new Talon(RobotMap.CargoMotor);
    CargoMotor2 = new Talon(RobotMap.CargoMotor2);
    HatchMover = new Talon(RobotMap.HatchMover);

  }
}
