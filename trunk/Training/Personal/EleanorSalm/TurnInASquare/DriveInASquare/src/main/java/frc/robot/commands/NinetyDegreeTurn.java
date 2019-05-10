/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class NinetyDegreeTurn extends Command {
  public NinetyDegreeTurn() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);

    public RobotDrive myRobot;
    public Gyro gyro;

    double Kp = 0.03;

    
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    gyro = new AnalogGyro(1);
    myRobot = new RobotDrive(1, 2);
    myRobot.setExperiation(0.01);
    
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    gyro.reset();
    while (is)


  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
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
