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
import frc.robot.other.SwerveDrive;
import frc.robot.subsystems.Drive;

public class TurnAngle extends Command {
  double m_degrees;
  double previousGyroVal;
  double previousTimeVal;
  double rotationSpeed;
  Timer timer;
  SwerveDrive.CentricMode previousMode;
  /**
   * Rotates the robot
   * @param degrees
   * how many degrees of rotation
   */
  @Deprecated
  
  public TurnAngle(double degrees) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    m_degrees = degrees;
    requires(Robot.drive);
    timer = new Timer();
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    SwerveDrive.setRelativeMode(SwerveDrive.CentricMode.ROBOT);
    Drive.rotationPID.setSetpoint(modulo(Drive.gyroPIDSource.pidGet() + m_degrees, 360));
    Drive.rotationPID.setAbsoluteTolerance(5);
    timer.reset();
    timer.start();
    Drive.rotationPID.reset();
    Drive.rotationPID.enable();
    previousGyroVal = Drive.gyroPIDSource.pidGet();
    previousTimeVal = -.001;  //This is set to -.001 to prevent crashing due to dividing by zero
    previousMode = SwerveDrive.getRelativeMode();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() 
  {
    //Calculate Derivative
    rotationSpeed = (Math.abs(Drive.gyroPIDSource.pidGet() - previousGyroVal)) / Math.abs(timer.get()-previousTimeVal);
    previousGyroVal = Drive.gyroPIDSource.pidGet();
    previousTimeVal = timer.get();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return rotationSpeed <= 0.5 && Drive.rotationPID.onTarget();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Drive.rotationPID.disable();
    Drive.rotationPID.reset();
    SwerveDrive.setRelativeMode(previousMode);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
  private static double modulo(double a, double b)
	{
		return (a < 0 ? b + (a % b) : a % b);
	}
}
