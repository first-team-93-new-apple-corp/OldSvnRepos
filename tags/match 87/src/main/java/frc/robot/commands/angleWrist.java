package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.other.ChainedServos;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Endgame;
import frc.robot.subsystems.Manipulator;

public class angleWrist extends Command {
  Boolean m_isUp;
  Timer timer;
  double previousTime;
  double previousLocation;
  double previousElevatorLocation;
  double derivativeValue;
  double elevatorDerivative;
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
    previousElevatorLocation = Elevator.elevatorEncoder.pidGet();
    derivativeValue = 1;
    storedMode = Manipulator.robotMode;
    if(m_isUp)
    {
      Manipulator.wristPID.setSetpoint(1.80);
    }
    else
    {
      Manipulator.wristPID.setSetpoint(0.55);
      if(storedMode == Manipulator.Mode.CARGO)
      {
        Elevator.ElevatorPID.setSetpoint(40);
      }
      else
      {
        Elevator.ElevatorPID.setSetpoint(0);
      }
      Elevator.ElevatorPID.enable();
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
        elevatorDerivative = Math.abs((Elevator.elevatorEncoder.pidGet() - previousElevatorLocation) / (timer.get() - previousTime));
        previousElevatorLocation = Elevator.elevatorEncoder.pidGet();
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
        elevatorDerivative = Math.abs((Elevator.elevatorEncoder.pidGet() - previousElevatorLocation) / (timer.get() - previousTime));
        previousElevatorLocation = Elevator.elevatorEncoder.pidGet();
        ChainedServos.set(1);
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
    if(m_isUp)
    {
      return derivativeValue < 0.1 && Manipulator.wristPID.onTarget();
    }
    else
    {
      return derivativeValue < 0.1 && Manipulator.wristPID.onTarget() && elevatorDerivative < 5 && Elevator.ElevatorPID.onTarget();
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
    if(Elevator.ElevatorPID.isEnabled())
    {
      Elevator.ElevatorPID.disable();
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
