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
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Endgame;
import frc.robot.subsystems.Manipulator;

public class intakeOuttake extends Command {
  boolean m_isIntaking;
  Manipulator.Mode storedMode;
  ManipulatorContinuous manipulatorContinuous;
  Timer timer;
  double disableTimerValueAtTouch;
  public intakeOuttake(boolean isIntaking) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);                                                                   //TRUE
    m_isIntaking = isIntaking;
    requires(Robot.manipulator);
    requires(Robot.elevator);
    manipulatorContinuous = new ManipulatorContinuous();
    timer = new Timer();
    disableTimerValueAtTouch = 0;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if(!(Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME))
    {
    storedMode = Manipulator.robotMode;
    timer.reset();
    timer.start();
    if(m_isIntaking)
    {
    }
  }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    
    if(!(Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME))
    {
    if(storedMode == Manipulator.Mode.CARGO)
    {
      if(m_isIntaking)
      {
        if(!Manipulator.wristPID.isEnabled())
        {
          Manipulator.wristPID.enable();
        }
        if(timer.get() > 0 && timer.get() < 0.5)
        {

        }

        else
        {
          manipulatorContinuous.execute();
        }

        Manipulator.cargoIntake.set(-1);
        
        if(Manipulator.intakeLightSensor.get())
        {
          timer.start();
        }
        else{
          // if(!Elevator.ElevatorPID.isEnabled())
          // {
          //   Elevator.ElevatorPID.setSetpoint(0);
          //   Elevator.ElevatorPID.enable();
          // }
        }
      }
      else
      {
        if(timer.get() > 0 && timer.get() < 0.5)
        {
          Manipulator.cargoIntake.set(1);
          manipulatorContinuous.execute();
        }
        else if(timer.get() > 0.5 && timer.get() < 2)
        {
          Manipulator.cargoIntake.set(1);
          manipulatorContinuous.execute();
        }
        else
        {
          Manipulator.cargoIntake.set(0);
          this.cancel();
        }
      }
    }
    else
    {
      System.out.println("HATCH MODE");
      if(m_isIntaking)
      {
        System.out.println("INTAKING HATCH");
        new hatchIntake().start();
      }
      else
      {
        System.out.println("OUTTAKING HATCH (sorry Quinten)");
        new hatchOuttake().start();
      }
    }
  }
}

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    
    if(!(Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME))
    {
    if(m_isIntaking)
    {
    return !Manipulator.intakeLightSensor.get() && timer.get() > 1;
    }
    else
    {
      return false;
    }
  }
  else
  { 
    return true;
  }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("ENDING COMMAND");
    if(m_isIntaking && storedMode == Manipulator.Mode.CARGO)
    {
    Manipulator.cargoIntake.set(-.15);
    new putKickerToBall().start();
    }
    else
    {
      Manipulator.cargoIntake.set(0);
    }
    if(Elevator.ElevatorPID.isEnabled())
    {
      Elevator.ElevatorPID.disable();
    }
    if(Manipulator.wristPID.isEnabled())
    {
      Manipulator.wristPID.disable();
    }
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
