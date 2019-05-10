/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.HatchRemoval;

public class ManualKicker extends Command {
  private double deadZone = 0.001;
  public ManualKicker() {
    requires(Robot.hatchRemoval);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    System.out.println("Manual Kicker");
    if(Math.abs(OI.operator.getRawAxis(3)) > deadZone){
      // double axis = OI.operator.getRawAxis(3);
      
      // axis /= 2.0;
      // axis += 0.5;
      // System.out.println(axis);
      HatchRemoval.hatchRemover.set(Math.pow(-(OI.operator.getRawAxis(3)), 3)*0.5);
    }
   
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    HatchRemoval.hatchRemover.set(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
