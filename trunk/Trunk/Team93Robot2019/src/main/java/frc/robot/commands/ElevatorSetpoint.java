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

public class ElevatorSetpoint extends Command {

  double m_setpointHatch;
  double m_setpointCargo;
  Timer timer;
  double elevatorDerivative;
  double previousElevatorLocation;
  double previousTime;
  wristAngle45 wristActivate;
  Boolean isAnglingWrist;

  public ElevatorSetpoint(double setpointHatch, double setpointCargo) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    m_setpointHatch = setpointHatch;
    m_setpointCargo = setpointCargo;
    requires(Robot.elevator);
    timer = new Timer();
    isAnglingWrist = false;
    
  }

  public ElevatorSetpoint(double setpointHatch, double setpointCargo, Boolean angleWrist)
  {
    m_setpointHatch = setpointHatch;
    m_setpointCargo = setpointCargo;
    requires(Robot.elevator);
    timer = new Timer();
    isAnglingWrist = true;
    wristActivate = new wristAngle45();
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if(!(Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME))
    {
    if(isAnglingWrist)
    {
      wristActivate.start();
    }
    if(Manipulator.robotMode == Manipulator.Mode.CARGO)
    {
      Elevator.ElevatorPID.setSetpoint(m_setpointCargo);
    }
    else
    {
      Elevator.ElevatorPID.setSetpoint(m_setpointHatch);
    }
    Elevator.ElevatorPID.enable();
    previousElevatorLocation = 0;
    previousTime = -1;
    timer.reset();
    timer.start();
  }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(!(Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME))
    {
    elevatorDerivative = Math.abs((Elevator.elevatorEncoder.pidGet() - previousElevatorLocation) / (timer.get() - previousTime));
    previousElevatorLocation = Elevator.elevatorEncoder.pidGet();
    previousTime = timer.get();
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(!(Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME))
    {
    return elevatorDerivative < 5 && Elevator.ElevatorPID.onTarget();
    }
    else
    {
      return true;
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Elevator.ElevatorPID.disable();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
