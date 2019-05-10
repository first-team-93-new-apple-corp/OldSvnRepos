package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.Endgame;
import frc.robot.subsystems.Manipulator;

public class angleWrist extends Command {
  Boolean m_isUp;
  Timer timer;
  double previousTime;
  double previousLocation;
  double previousElevatorLocation;
  double derivativeValue;
  Manipulator.Mode storedMode;

  
  public angleWrist(Boolean isUp) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.manipulator);
    requires(Robot.elevator);
    m_isUp = isUp;
    timer = new Timer();
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if(!(Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME))
    {
    timer.reset();
    timer.start();
    previousTime = 0;
    previousLocation = Manipulator.rotateWristSensor.getVoltage();
    derivativeValue = 1;
    storedMode = Manipulator.robotMode;
    if(m_isUp)
    {
      Manipulator.wristPID.setSetpoint(1.65);
    }
    else
    {
      Manipulator.wristPID.setSetpoint(0.65);
      if(storedMode == Manipulator.Mode.CARGO)
      {
      }
      else
      {
      }
      //Elevator.ElevatorPID.enable();
    }
    
    Manipulator.wristPID.enable();
  }
}

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(!(Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME))
    {
    

    if(Manipulator.robotMode == Manipulator.Mode.CARGO)
    {
      if(m_isUp)
      {
        //DO NOTHING BECAUSE ONLY WRIST PID SHOULD BE RUNNING
      }
      else
      {
      }
    }
    else
    {
      if(m_isUp)
      {
        //DO NOTHING BECAUSE ONLY WRIST PID SHOULD BE RUNNING
      }
      else
      {
        
      }
    }

    //CALCULATE DERIVATIVE VALUE
    derivativeValue = Math.abs((Manipulator.rotateWristSensor.getVoltage() - previousLocation) / (timer.get() - previousTime));
    previousTime = timer.get();
    previousLocation = Manipulator.rotateWristSensor.getVoltage();

  }
}

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(!(Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME))
    {
    if(m_isUp || Manipulator.robotMode == Manipulator.Mode.HATCH)
    {
      return derivativeValue < 0.1 && Manipulator.wristPID.onTarget();
    }
    else
    {
      return derivativeValue < 0.1 && Manipulator.wristPID.onTarget();
    }
  }
  else{
    return true;
  }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    if(Manipulator.wristPID.isEnabled())
    {
      Manipulator.wristPID.disable();
    }
    OI.holdWristPID.start();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
