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
import frc.robot.subsystems.Endgame;
import frc.robot.subsystems.Manipulator;

public class ToggleEndgamePID extends Command {
  public ToggleEndgamePID() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.manipulator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if((Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME))
    {
    
     
        Endgame.balancePID.disable();
    }

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if((Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME))
    {
    if(-OI.getOperatorStick2() < 0 && Manipulator.habLiftEncoder.get() >= 0)
    {
      Endgame.habLift.set(0);
    }
    else if(-OI.getOperatorStick2() > 0 && Manipulator.habLiftEncoder.get() <= -500)
    {
      Endgame.habLift.set(0);
    }
    else
    {
      Endgame.habLift.set(-OI.getOperatorStick2());
    }
  }
}

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return (!(Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME));
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    if(Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME)
    {
     Endgame.balancePID.enable();
    }
    
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
