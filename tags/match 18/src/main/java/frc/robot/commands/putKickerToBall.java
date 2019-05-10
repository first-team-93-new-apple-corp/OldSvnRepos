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
import frc.robot.subsystems.Endgame;
import frc.robot.subsystems.Manipulator;

public class putKickerToBall extends Command {
  ManipulatorContinuousWithoutKicker manipulatorContinuousWithoutKicker;
  Timer timer;
  Boolean isfinished;

  public putKickerToBall() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.manipulator);
    manipulatorContinuousWithoutKicker = new ManipulatorContinuousWithoutKicker();
    timer = new Timer();
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if(!(Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME))
    {
    timer.reset();
    timer.start();
    isfinished=false;
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(!(Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME))
    {
    manipulatorContinuousWithoutKicker.execute();
    
    if(timer.get() > 0 && timer.get() < 0.25)
    {      
      double output = -1;
      if(Manipulator.kickerSensor.getVoltage() > 0.205078104)
      {
        Manipulator.kicker.set(output / 3.0);
      } 
      else
      {
        isfinished = true;
        Manipulator.kicker.set(0);
      }
    }
    else
    {
      double output = -.4;
      if(Manipulator.kickerSensor.getVoltage() > 0.205078104)
      {
        Manipulator.kicker.set(output / 3.0);
      } 
      else
      {
        isfinished = true;
        Manipulator.kicker.set(0);
      }
    }
  }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(!(Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME))
    {
    return isfinished;
    }
    else{
      return true;
    }
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
