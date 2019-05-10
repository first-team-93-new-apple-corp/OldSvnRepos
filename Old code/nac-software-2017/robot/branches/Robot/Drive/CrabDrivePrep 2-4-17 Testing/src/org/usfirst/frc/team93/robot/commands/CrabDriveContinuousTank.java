package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;


/**
 * Crab Drive Robot Angle Control Flow:
 * Gyro -> GyroPIDSource (Source) + Joystick yaw (setpoint) -> PIDController -
 * -> CrabDriveAngleAdjuster (adjustment) + Joystick (control) -
 * -> CrabDriveSpeedMotor -> PIDOutputGroup -> Talon -> Motor -> Robot Angle
 * 
 * Crab Drive Wheel Direction Control Flow:
 * SPI Encoders -> SPIEncoderPIDSource -
 * -> CrabDriveDirectionPIDSource (source) + Joystick direction (setpoint) -> PIDController -
 * -> CrabDriveAngleAdjuster (adjustment) + Joystick (control) -
 * -> CrabDriveSpeedMotor -> PIDOutputGroup -> Talon -> Motor -> Robot Movement
 */
public class CrabDriveContinuousTank extends Command {
	
    public CrabDriveContinuousTank() { 
    	//requires drive
    	requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
    	Drive.DRIVE_LEFT.set(OI.driver.getRawAxis(1));
    	Drive.DRIVE_RIGHT.set(OI.driver.getRawAxis(3));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//Is never finished unless interrupted
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
		//sets drive motors to 0.0 to stop
    	Drive.setAllMotors(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
}