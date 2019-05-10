package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Endgame;
import frc.robot.subsystems.Manipulator;

public class StartGame extends Command {
  Timer timer;
  double previousTime;
  double previousLocation;
  double derivativeValue;
  hatchIntake hatchintake;

  public StartGame() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.manipulator);
    timer = new Timer();
    hatchintake = new hatchIntake();
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if(!(Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME))
    {
    timer.reset();
    timer.start();
    previousTime = 0;
    previousLocation = 1;
    derivativeValue = 1;
      Manipulator.wristPID.setSetpoint(1.55);
      Manipulator.wristPID.setP(-2.5);
      
    Manipulator.wristPID.enable();
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(!(Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME))
    {
    derivativeValue = Math.abs((Manipulator.rotateWristSensor.getVoltage() - previousLocation) / (timer.get() - previousTime));
    previousTime = timer.get();
    previousLocation = Manipulator.rotateWristSensor.getVoltage();
    }
    System.out.println("RUNNING");
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(!(Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME))
    {
      return derivativeValue < 0.1 && Manipulator.wristPID.onTarget();
    }
    else
    {
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
    hatchintake.start();  
    Manipulator.wristPID.setP(-.7);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
