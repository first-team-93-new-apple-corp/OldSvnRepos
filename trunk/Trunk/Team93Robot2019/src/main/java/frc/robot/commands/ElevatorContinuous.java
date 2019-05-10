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
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Endgame;

public class ElevatorContinuous extends Command {
  Boolean reduceTopLimit;
  Boolean hasEndedBackOffset;
  /**
   * 
   * manual controls for moving the elevator up and down
   * 
   */

  public ElevatorContinuous() {
    requires(Robot.elevator);
    reduceTopLimit = false;
    hasEndedBackOffset = false;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if((Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME) && Elevator.bottomLimit.get())
    {
      reduceTopLimit = true;
    }

    if (OI.operator.getPOV() == -1) 
    {

      if(!(Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME))
      {
        
      if (Elevator.bottomLimit.get() && -OI.getOperatorStick() > 0)
      {

        Elevator.elevatorMotorLeft.pidWrite(0);
        Elevator.elevatorMotorRight.pidWrite(0);

      }
      else if(Elevator.elevatorEncoder.pidGet() > getTopSetpoint() && -OI.getOperatorStick() < 0)
      {
        Elevator.elevatorMotorLeft.pidWrite(0);
        Elevator.elevatorMotorRight.pidWrite(0);
      }
      else
      {
        Elevator.elevatorMotorLeft.pidWrite(-OI.getOperatorStick());
        Elevator.elevatorMotorRight.pidWrite(-OI.getOperatorStick());
      }
    }
    else
    {
      if ((Elevator.bottomLimit.get() || Elevator.elevatorEncoder.pidGet() < 0) && -OI.getOperatorStick() > 0)
      {

        Elevator.elevatorMotorLeft.pidWrite((-OI.getOperatorStick() * 1) + getFrontOffset());
        Elevator.elevatorMotorRight.pidWrite((-OI.getOperatorStick() * 1) + getFrontOffset());

      }
      else if(Elevator.elevatorEncoder.pidGet() > getTopVal() && -OI.getOperatorStick() < 0)
      {
        Elevator.elevatorMotorLeft.pidWrite(getFrontOffset());
        Elevator.elevatorMotorRight.pidWrite(getFrontOffset());
      }
      else
      {
        Elevator.elevatorMotorLeft.pidWrite(-OI.getOperatorStick() + getFrontOffset());
        Elevator.elevatorMotorRight.pidWrite(-OI.getOperatorStick() + getFrontOffset());
      }

    }
  }
}
public static double getTopVal()
{
  if(Robot.hasChangedEndgameSetpoint)
  {
    return 542;
  }
  else{
    return 70;
  }
}

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {

    return false;

    // command stops when button stops being held
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    
    Elevator.elevatorMotorLeft.pidWrite(0);
    Elevator.elevatorMotorRight.pidWrite(0);
    // Elevator.elevatorPIDOutput.pidWrite(0); //TODO: check this for PIDs and
    // comment when you understand

  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }

  double getTopSetpoint()
  {
    if(reduceTopLimit)
    {
      return 70;
    }
    else
    {
      return 542;
    }
  }

  
  double getFrontOffset()
  {
    if(OI.driver.getRawButton(7))
    {
      hasEndedBackOffset = true;
    }
      if(!hasEndedBackOffset)
    {
      return 0.2;
    }
    else{
      return 0;
    }
    
  }
}
