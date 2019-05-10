/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Manipulator;

public class hatchOuttake extends Command {
  Timer timer;
  Boolean isFinished;
  public hatchOuttake() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.manipulator);
    timer = new Timer();
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    timer.reset();
    timer.start();
    isFinished = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    System.out.println(timer.get());
    if(timer.get() > 0 && timer.get() < 0.25)
    {
      double output = 0.875;

      if(Manipulator.kickerSensor.getVoltage() < 3.8)
      {
        Manipulator.kicker.set(output);
      }
      else{
        Manipulator.kicker.set(0);
      }
    } 
    else if(timer.get() > 0.25 && timer.get() < 2)
    {
      
      double output = -1;
      if(Manipulator.kickerSensor.getVoltage() > 2.1)
      {
        Manipulator.kicker.set(output / 2.5);
      }
      else
      {
        Manipulator.kicker.set(0);
        isFinished = true;
      }
      
      
    }
    else
    {
      Manipulator.kicker.set(0);
      isFinished = true;
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isFinished;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Manipulator.kicker.set(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
