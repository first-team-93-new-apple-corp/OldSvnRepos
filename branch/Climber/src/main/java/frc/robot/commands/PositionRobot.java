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
import frc.robot.subsystems.Climber;

/**
 * 
 * lowers the back wheels to lift the robot and drive it for end game  
 * 
 */

public class PositionRobot extends Command {
  
  static double inchesToEncoderTicksConversion = 15;
  static double setPoint; // does this need to be already made bc of only one height??
  private Timer timerElapser;
  private double previousScalarVal;
  private double previousTimeVal;
  private double scalarSpeed;
  public static double lastHeight;

  private static double inchesToEncoderTicks(double length){
    double encoderTicks = length * inchesToEncoderTicksConversion;
    return encoderTicks;
  }

  public PositionRobot(double height) {
    requires(Robot.climber);
    setPoint = inchesToEncoderTicks(height);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Climber.scalarUpPID.reset();
    Climber.scalarDownPID.reset();
    Climber.scalarUpPID.setAbsoluteTolerance(0.1);
    Climber.scalarDownPID.setAbsoluteTolerance(0.1);
    if(setPoint > lastHeight){
      Climber.scalarUpPID.setSetpoint(setPoint);
      Climber.scalarUpPID.enable();
    }
    else{
      Climber.scalarDownPID.setSetpoint(setPoint);
      Climber.scalarDownPID.enable();
    }
    // says whether to move up or down
    timerElapser.reset();
    timerElapser.start();
    previousTimeVal = -0.001;
    previousScalarVal = Climber.scalarEncoder.get();
    
  }
  

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    scalarSpeed = (Math.abs(Climber.scalarEncoder.pidGet() - previousScalarVal)) / Math.abs(timerElapser.get()-previousTimeVal);
    previousScalarVal = Climber.scalarEncoder.pidGet();
    previousTimeVal = timerElapser.get();

  }
  // sets the speed

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return ((Climber.scalarUpPID.isEnabled() && Climber.scalarUpPID.onTarget()) || (Climber.scalarDownPID.isEnabled() && Climber.scalarDownPID.onTarget())) && scalarSpeed < 0.5;
  }
  // is done when the scalar has reached its target and isnot traveling toooo fast

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Climber.scalarUpPID.disable();
    Climber.scalarDownPID.disable();
    timerElapser.stop();
    lastHeight = setPoint;
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
