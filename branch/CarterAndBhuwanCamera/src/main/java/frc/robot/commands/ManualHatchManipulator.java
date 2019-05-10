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
import frc.robot.subsystems.HatchManipulator;

public class ManualHatchManipulator extends Command {
  private double deadZone = 0.001;
  private double wantedPosition = 0;
  private double speedProportion = 0.03;
  public ManualHatchManipulator() {
    requires(Robot.hatchManipulator);
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
    System.out.println("Manual Hatch");

   

    if(Math.abs(OI.operator.getRawAxis(3)) > deadZone){
      if(wantedPosition < 0){
        wantedPosition = 0;
      }
      else if(wantedPosition > 1){
        wantedPosition = 1;
      }
      else{
        wantedPosition += Math.pow(OI.operator.getRawAxis(3), 3) * speedProportion;
      }
      HatchManipulator.hatchManipA.set(1-wantedPosition);
      HatchManipulator.hatchManipB.set(wantedPosition);
      
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
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
