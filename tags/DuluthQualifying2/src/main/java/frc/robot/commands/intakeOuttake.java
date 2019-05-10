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

public class intakeOuttake extends Command {
  boolean m_isIntaking;
  Manipulator.Mode storedMode;
  ManipulatorContinuous manipulatorContinuous;
  Timer timer;
  double disableTimerValueAtTouch;
  public intakeOuttake(boolean isIntaking) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    m_isIntaking = isIntaking;
    requires(Robot.manipulator);
    manipulatorContinuous = new ManipulatorContinuous();
    timer = new Timer();
    disableTimerValueAtTouch = 0;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    storedMode = Manipulator.robotMode;
    timer.reset();
    timer.start();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    if(storedMode == Manipulator.Mode.CARGO)
    {
      if(m_isIntaking)
      {
        Manipulator.cargoIntake.set(-1);
        manipulatorContinuous.execute();
        if(Manipulator.intakeLightSensor.get())
        {
          timer.start();
        }
      }
      else
      {
        if(timer.get() > 0 && timer.get() < 0.5)
        {
          double output = 1;

          if(Manipulator.kickerSensor.getVoltage() < 3.8)
          {
            Manipulator.kicker.set(output);
          }
          else{
            Manipulator.kicker.set(0);
          }
        }
        else
        {
          Manipulator.cargoIntake.set(1);
          manipulatorContinuous.execute();
        }
      }
    }
    else
    {
      if(m_isIntaking)
      {
        if(Manipulator.rotateWristSensor.getVoltage() < 3.95)
        {
          new pickupHatchOffGround().start();
        }
        else
        {
          new pickupHatchOffLoadingStation().start();
        }
      }
      else
      {
        new hatchOuttake().start();
      }
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(m_isIntaking)
    {
    return !Manipulator.intakeLightSensor.get() && timer.get() > 0.5;
    }
    else
    {
      return false;
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    if(m_isIntaking && storedMode == Manipulator.Mode.CARGO)
    {
    Manipulator.cargoIntake.set(-.15);
    new putKickerToBall().start();
    }
    else
    {
      Manipulator.cargoIntake.set(0);
    }
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
