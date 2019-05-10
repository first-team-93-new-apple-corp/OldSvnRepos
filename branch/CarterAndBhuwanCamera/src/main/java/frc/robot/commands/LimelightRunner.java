/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.subsystems.Limelight;
import edu.wpi.first.networktables.NetworkTableEntry;

public class LimelightRunner extends Command {
  /**
   * The default command for the limelight; runs continuously
   */
  public LimelightRunner() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.limeLight);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    NetworkTableEntry tx = Limelight.table.getEntry("tx");
NetworkTableEntry ty = Limelight.table.getEntry("ty");
NetworkTableEntry ta = Limelight.table.getEntry("ta");
NetworkTableEntry tv = Limelight.table.getEntry("tv");


double target = tv.getDouble(0.0);
    double x = tx.getDouble(0.0);
double y = ty.getDouble(0.0);
double area = ta.getDouble(0.0);
// publishes the angle off center in the x and y directions 
    SmartDashboard.putNumber("LimelightX", x);
SmartDashboard.putNumber("LimelightY", y);
// whether the Limelight detects a target
SmartDashboard.putNumber("LimelightTarget", target);
SmartDashboard.putNumber("area", area);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
