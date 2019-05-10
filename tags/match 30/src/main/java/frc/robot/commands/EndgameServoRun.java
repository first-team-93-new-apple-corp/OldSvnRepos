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

public class EndgameServoRun extends Command {
  Timer timer;
  public EndgameServoRun() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.endgame);
    timer = new Timer();
    this.setInterruptible(false);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    timer.reset();
    timer.start();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if((Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME))
    {
    System.out.println("RUNNING");
    System.out.println(timer.get());
    if(timer.get() > 0 && timer.get() < 1)
    {
      Endgame.endgameServo.setAngle(10);
    }
    else
    {
      Endgame.endgameServo.setAngle(100);
    }
  }
  }
  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return ((!(Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME)) || timer.get() > 1.1);
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
