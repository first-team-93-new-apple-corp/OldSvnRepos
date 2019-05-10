/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.other.CameraCodeAsPIDSource;

/**
 * Add your docs here.
 */
public class Limelight extends Subsystem {
  public static NetworkTable table;
  public static CameraCodeAsPIDSource cameraCodeAsPIDSource;
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public Limelight()
  {

    //code taken from example code from limelight
    cameraCodeAsPIDSource = new CameraCodeAsPIDSource();
    table = NetworkTableInstance.getDefault().getTable("limelight");
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}