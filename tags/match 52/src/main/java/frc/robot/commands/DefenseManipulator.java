/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.Endgame;
import frc.robot.subsystems.Manipulator;
import frc.robot.other.ChainedServos;

public class DefenseManipulator extends Command {
  boolean wasPIDOnTarget;
  Timer timer;
  public DefenseManipulator() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.manipulator);
    timer = new Timer();
    
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if(!(Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME))
    {
    Manipulator.wristPID.setSetpoint(1.80);
    Manipulator.wristPID.enable();
    ChainedServos.set(1);
    OI.defenseElevator.start();
    wasPIDOnTarget = false;
    timer.reset();
    timer.start();
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(!(Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME))
    {
    double output = -1;
      if(Manipulator.kickerSensor.getVoltage() > 0.205078104)
      {
        Manipulator.kicker.set(output / 3.0);
      } 
      else
      {
        Manipulator.kicker.set(0);
      }
      if(Manipulator.wristPID.onTarget() && Manipulator.wristPID.isEnabled())
      {
        Manipulator.wristPID.disable();
        wasPIDOnTarget = true;
      }
      if(wasPIDOnTarget)
      {
        double joystickInput = Math.abs(OI.getDriverRot()) + OI.getDriverY();
        if(joystickInput * 0.4 > 0.1)
        {
          Manipulator.wristRotator.set(-(joystickInput * 0.4));
          timer.start();
        }
        else
        {
          if(timer.get() < 0.5)
          {
            Manipulator.wristRotator.set((-0.4));
          }
          else
          {
          Manipulator.wristRotator.set(-0.1);
          }
        }
      }
    }
    
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(!(Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME))
    {
    return false;
    }
    else
    {
      return true;
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Manipulator.kicker.set(0);
    if(Manipulator.removerPID.isEnabled())
    {
      Manipulator.wristPID.disable();
    }
    else
    {
      Manipulator.wristRotator.set(0);    
    }
    if(Manipulator.robotMode == Manipulator.Mode.HATCH)
    {
      ChainedServos.set((.4 * (Manipulator.rotateWristSensor.getVoltage() - .55)) + .5 );
    }
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
