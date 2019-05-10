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

public class ManipulatorContinuousWithoutKicker extends Command {

  final double voltsToDegreesForManualWrist = 1;

  public ManipulatorContinuousWithoutKicker() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.manipulator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(!(Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME))
    {
    switch(OI.operator.getPOV())
    {

      case 0:   //Kicker Manual

        // if(OI.getOperatorStick() > 0)
        // {
        //   if(Manipulator.kickerSensor.getVoltage() < 3.8)
        //   {
        //     Manipulator.kicker.set(OI.getOperatorStick());
        //   }
        //   else{
        //     Manipulator.kicker.set(0);
        //   }
        // }
        // else{
        //     if(Manipulator.kickerSensor.getVoltage() > 2.1)
        //     {
        //       Manipulator.kicker.set(OI.getOperatorStick() / 2.5);
        //     } 
        //     else
        //     {
        //       Manipulator.kicker.set(0);
        //     }
        // }
          

        // Manipulator.wristRotator.set(0 /** PLUS FORMULA TO FIGURE OUT REQUIRED OFFSET VOLTAGE */); //todo figure out formula
        // break;

      case 270: //Wrist Manual
        if(Manipulator.robotMode == Manipulator.Mode.CARGO)
        {

          Manipulator.wristRotator.set(OI.getOperatorStick()/** PLUS FORMULA TO FIGURE OUT REQUIRED OFFSET VOLTAGE */); //todo figure out formula
        }
        else if(Manipulator.robotMode == Manipulator.Mode.HATCH)
        {

          Manipulator.wristRotator.set(OI.getOperatorStick() /** PLUS FORMULA TO FIGURE OUT REQUIRED OFFSET VOLTAGE */); //todo figure out formula
        }
        //Manipulator.kicker.set(0);
        break;
        
      default:
        Manipulator.wristRotator.set(0 /** PLUS FORMULA TO FIGURE OUT REQUIRED OFFSET VOLTAGE */); //todo figure out formula
        //Manipulator.kicker.set(0);
        break;

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
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
