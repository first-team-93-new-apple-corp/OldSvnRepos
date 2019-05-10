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
import frc.robot.other.CameraCodeAsPIDSource;
import frc.robot.other.SwerveDrive;
import frc.robot.other.SwervePIDOutput;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Endgame;

public class setDriveTo180 extends Command {
  SwerveDrive.CentricMode oldMode;
  /**
   * Sets the locked angle to 180
   */
  public setDriveTo180() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.drive);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if(!(Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME))
    {
    oldMode = SwerveDrive.getRelativeMode();
    Drive.rotationPID.setSetpoint(180);
    Drive.rotationPID.setAbsoluteTolerance(20);
    Drive.closeRotationPID.setSetpoint(180);
    Drive.closeRotationPID.setAbsoluteTolerance(20);
    }
    
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(!(Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME))
    {
    if(OI.driver.getRawButton(4))
    {
      oldMode = SwerveDrive.CentricMode.ROBOT;
    }
    if(OI.driver.getRawButton(3))
    {
      oldMode = SwerveDrive.CentricMode.FIELD;
    }
    if(OI.driver.getRawButton(1) && (CameraCodeAsPIDSource.cameraXSource.pidGet() != 0.0))
    {
        if(!Drive.lockedXCamDrive.isEnabled())
       {
         Drive.lockedXCamDrive.enable();
         SwerveDrive.setRelativeMode(SwerveDrive.CentricMode.ROBOT);
       }
    }
    else
    {
      if(Drive.lockedXCamDrive.isEnabled())
      {
        Drive.lockedXCamDrive.disable();
      }
      SwervePIDOutput.xOutput.pidWrite(OI.getDriverX() * -1);
    }


    SwervePIDOutput.yOutput.pidWrite(OI.getDriverY() * -1);
    if(Drive.rotationPID.onTarget())
    {
      if(Drive.rotationPID.isEnabled())
      {
        Drive.rotationPID.disable();
      }
      if(!Drive.closeRotationPID.isEnabled())
      {
        Drive.closeRotationPID.enable();
      }
    }
    else
    {
      if(Drive.closeRotationPID.isEnabled())
      {
        Drive.closeRotationPID.disable();
      }
      if(!Drive.rotationPID.isEnabled())
      {
        Drive.rotationPID.enable();
      }
    }
    
    if(!OI.driver.getRawButton(1))
    {
      SwerveDrive.setRelativeMode(oldMode);
      if(oldMode == SwerveDrive.CentricMode.FIELD)
      {
      }
      else
      {
      }
    }
  }
  }
  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(!(Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME))
    {
    return false;
    }
    else
    {
      return true;
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Drive.rotationPID.disable();
    Drive.closeRotationPID.disable();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }

}
