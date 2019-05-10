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

 
/**
 * This command lifts the elevator up to a given height
 */

public class ElevatorHeight extends Command {

  
  //uses ElevatorPIDSource and ElevatorPIDOutput so that multiple motors can be used with only one PID
  private double m_height;
  private double groundToManipulator = 10; //change
  public static double lastHeight;
  private Timer timeElapser;
  private double elevatorSpeed;
  private double previousElevatorVal;
  private double previousTimeVal;

  public ElevatorHeight(double height) {
    requires(Robot.elevator);
    m_height = height;
    // sets default height to input height
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    timeElapser = new Timer();
    Elevator.ElevatorPIDUp.reset();
    Elevator.ElevatorPIDUp.setAbsoluteTolerance(Elevator.Tolerance);
    Elevator.ElevatorPIDDown.reset();
    Elevator.ElevatorPIDDown.setAbsoluteTolerance(Elevator.Tolerance);
    timeElapser.reset();
    timeElapser.start();
    previousTimeVal = -0.001;
    previousElevatorVal = Elevator.elevatorEncoder.pidGet();

    if(m_height > lastHeight)
    {
      Elevator.ElevatorPIDUp.setSetpoint(m_height*Elevator.inchesToTicksConversion - (groundToManipulator*Elevator.inchesToTicksConversion));
      Elevator.ElevatorPIDUp.enable();
    }
    else
    {
      Elevator.ElevatorPIDDown.setSetpoint(m_height*Elevator.inchesToTicksConversion - (groundToManipulator*Elevator.inchesToTicksConversion));
      Elevator.ElevatorPIDDown.enable();
    }
    
    //resets and enables PIDs, and sets PIDs' tolerance, also set the initial values for calculating velocity
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    elevatorSpeed = (Math.abs(Elevator.elevatorEncoder.pidGet() - previousElevatorVal)) / Math.abs(timeElapser.get()-previousTimeVal);
    previousElevatorVal = Elevator.elevatorEncoder.pidGet();
    previousTimeVal = timeElapser.get();
    if(!Elevator.bottomLimit.get()){
      Elevator.elevatorEncoder.reset();
    }
  // finds velocity of the elevator 
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return ((Elevator.ElevatorPIDUp.isEnabled() && Elevator.ElevatorPIDUp.onTarget()) || 
    (Elevator.ElevatorPIDDown.isEnabled() && Elevator.ElevatorPIDDown.onTarget()) && elevatorSpeed < 0.5) ||
    (!Elevator.bottomLimit.get() && Elevator.ElevatorPIDDown.isEnabled());
    //makes sure that code doesn't terminate until the elevator is at the required position and isn't travelling too fast
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Elevator.ElevatorPIDUp.disable();
    Elevator.ElevatorPIDDown.disable();
    timeElapser.stop();
    lastHeight = m_height;
    //disables the PIDs and timer, also sets the last height value to its current height
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
