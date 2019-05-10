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
import frc.robot.other.ChainedServos;
import frc.robot.subsystems.Endgame;
import frc.robot.subsystems.Manipulator;

public class ManipulatorContinuous extends Command {

  final double voltsToDegreesForManualWrist = 1;
  Boolean hasEndedFrontOffset;

  public ManipulatorContinuous() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.manipulator);
    hasEndedFrontOffset = false;
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
      //System.out.println("manipulatorcontinuous");
    switch(OI.operator.getPOV())
    {

      case 0:   //Kicker Manual

        if(OI.getOperatorStick() > 0)
        {
          if(Manipulator.kickerSensor.getVoltage() < 4.079589426)
          {
            Manipulator.kicker.set(OI.getOperatorStick());
          }
          else{
            Manipulator.kicker.set(0);
          }
        }
        else{
            if(Manipulator.kickerSensor.getVoltage() > 0.205078104)
            {
              Manipulator.kicker.set(OI.getOperatorStick() / 3.0);
            } 
            else
            {
              Manipulator.kicker.set(0);
            }
        }
          

        Manipulator.wristRotator.set(0 /** PLUS FORMULA TO FIGURE OUT REQUIRED OFFSET VOLTAGE */); //todo figure out formula
        break;

      case 270: //Wrist Manual
      System.out.println("RUNNING WRIST");
        if(Manipulator.robotMode == Manipulator.Mode.CARGO)
        {

          ChainedServos.set(0);
          Manipulator.wristRotator.set(OI.getOperatorStick()/** PLUS FORMULA TO FIGURE OUT REQUIRED OFFSET VOLTAGE */); //todo figure out formula
        }
        else if(Manipulator.robotMode == Manipulator.Mode.HATCH)
        {
          
          System.out.println((.4 * (Manipulator.rotateWristSensor.getVoltage() - .55)) + .5 );
          ChainedServos.set((.4 * (Manipulator.rotateWristSensor.getVoltage() - .55)) + .5 );   //TROUBLESHOOT THIS
          Manipulator.wristRotator.set(OI.getOperatorStick() /** PLUS FORMULA TO FIGURE OUT REQUIRED OFFSET VOLTAGE */); //todo figure out formula
        }
        Manipulator.kicker.set(0);
        break;
        
      default:
        Manipulator.wristRotator.set(0 /** PLUS FORMULA TO FIGURE OUT REQUIRED OFFSET VOLTAGE */); //todo figure out formula
        Manipulator.kicker.set(0);
        break;
    }
  }
  else
  {
    System.out.println(-OI.getOperatorStick2());
    if(-OI.getOperatorStick2() < 0 && Manipulator.habLiftEncoder.get() >= 0)
    {
      Manipulator.habLift.set(0);
      System.out.println("TOP LIMIT");
    }
    else if(-OI.getOperatorStick2() > 0 && Manipulator.habLiftEncoder.get() <= -500)
    {
      Manipulator.habLift.set(0);
      System.out.println("BOTTOM LIMIT");
    }
    else
    {
      Manipulator.habLift.set(-OI.getOperatorStick2() * (8.0/9.0) + getFrontOffset());
    }
    
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
  }

  double getFrontOffset()
  {
    if(OI.driver.getRawButton(11))
    {
      hasEndedFrontOffset = true;
    }
      if(!hasEndedFrontOffset)
    {
      return 0.2;
    }
    else{
      return 0;
    }
    
  }
}
