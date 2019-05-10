package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Manipulator;

public class angleWrist extends Command {
  Boolean m_isUp;
  Timer timer;
  double previousTime;
  double previousLocation;
  double derivativeValue;
  public angleWrist(Boolean isUp) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.manipulator);
    m_isUp = isUp;
    timer = new Timer();
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    timer.reset();
    timer.start();
    previousTime = 0;
    previousLocation = Manipulator.rotateWristSensor.getVoltage();
    derivativeValue = 1;
    if(m_isUp)
    {
      Manipulator.wristPID.setSetpoint(4.6);
    }
    else
    {
      Manipulator.wristPID.setSetpoint(3.4);
    }
    Manipulator.wristPID.enable();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //CALCULATE DERIVATIVE VALUE
    derivativeValue = Math.abs((Manipulator.rotateWristSensor.getVoltage() - previousLocation) / (timer.get() - previousTime));
    previousTime = timer.get();
    previousLocation = Manipulator.rotateWristSensor.getVoltage();

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

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(m_isUp)
    {
      return derivativeValue < 0.1 && Manipulator.wristPID.onTarget();
    }
    else
    {
      return derivativeValue < 0.1 && Manipulator.wristPID.onTarget();
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
