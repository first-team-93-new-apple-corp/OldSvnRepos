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

public class LockedAngleControl extends Command {
  
  double m_angleStraight;
  double m_angle270;
  double m_angle90;
  double angleSetpoint;
  boolean isFinished;
  SwerveDrive.CentricMode oldMode;
  boolean cancelAll;
  /**
   * Runs when the angle is locked, uses cameracode when trigger is pressed; selects angle based on POV
   * @param angleStraight
   * The angle wanted when POV is forwards
   * @param angle270
   * The angle wanted when POV is left
   * @param angle90
   * The angle wanted when POV is right
   */
  public LockedAngleControl(double angleStraight, double angle270, double angle90) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.drive);
    m_angleStraight = angleStraight;
    m_angle270 = angle270;
    m_angle90 = angle90;
    cancelAll = false;
  }
  public LockedAngleControl()
  {
    cancelAll = true;
    System.out.println("CANCELING ANGLE");
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if(!(Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME))
    {
    if(cancelAll)
    {
      System.out.println("CANCELING");
      this.cancel();
    }
    if(!cancelAll)
    {
    oldMode = SwerveDrive.getRelativeMode();
    SwerveDrive.setRelativeMode(SwerveDrive.CentricMode.ROBOT);
    isFinished = false;
    if(OI.driver.getPOV() == 270)
    {
      angleSetpoint = m_angle270;
    }
    else if(OI.driver.getPOV() == 0)
    {
      angleSetpoint = m_angleStraight;
    }
    else if(OI.driver.getPOV() == 90)
    {
      angleSetpoint = m_angle90;
    }
    else
    {
      isFinished = true;
    }
    if(!isFinished)
    {
    Drive.rotationPID.setSetpoint(modulo(angleSetpoint, 360));
    Drive.rotationPID.setAbsoluteTolerance(20);
    Drive.closeRotationPID.setSetpoint(modulo(angleSetpoint, 360));
    Drive.closeRotationPID.setAbsoluteTolerance(20);
    Drive.lockedXCamDrive.setSetpoint(0);
    }
    }
  }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(!(Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME))
    {
    if(!cancelAll)
    {
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
    if(!OI.driver.getRawButton(1))
    {
      SwerveDrive.setRelativeMode(oldMode);
    }

    if(OI.driver.getRawButton(4))
    {
      oldMode = SwerveDrive.CentricMode.ROBOT;
    }
    if(OI.driver.getRawButton(3))
    {
      oldMode = SwerveDrive.CentricMode.FIELD;
    }


    SwervePIDOutput.yOutput.pidWrite(OI.getDriverY() * -1);
if(!isFinished){
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
  }  
}
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(!(Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME))
    {
    return (isFinished || cancelAll);
    }
    else
    {
      return true;
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    if(!cancelAll)
    {
    Drive.rotationPID.disable();
    Drive.closeRotationPID.disable();
    }
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
