// RobotBuilder Version: 1.5
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This command has the robot drive forwards to a desired distance.
 * 
 * @author Admin93
 */

public class DriveForward extends Command
{

	private double m_driveDistance;
	private double m_maxError;
	private int m_errorTimer;

	private static Timer totalTime;

	/**
	 * 
	 * @param driveDistance
	 *            How far to drive
	 * @param maxError
	 *            Acceptable range of this position
	 */
	public DriveForward(double driveDistance, double maxError)
	{

		m_driveDistance = driveDistance;
		m_maxError = maxError;

		// Use requires() here to declare subsystem dependencies
		requires(Robot.drive);

		totalTime = new Timer();
	}

	// Called just before this Command runs the first time
	@Override
	/**
	 * This function sets the motors up to run to a certain distance
	 */
	protected void initialize()
	{
		Drive.resetSensors();

		RobotMap.driveSpeedControl.reset();
		RobotMap.driveSpeedControl.enable();
		RobotMap.driveSpeedControl.setSetpoint(m_driveDistance);
		RobotMap.steerAndSpeed.setSteeringGain(-0.2);
		RobotMap.driveEncoderSteering.reset();
		RobotMap.driveEncoderSteering.enable();
		RobotMap.driveEncoderSteering.setSetpoint(0.0);

		totalTime.start();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		// returns true when error is low enough for 0.5s
		/*
		 * double currentError =
		 * Math.abs(RobotMap.driveSpeedControl.getError()); if (currentError <=
		 * m_maxError) { m_errorTimer += 20; if (m_errorTimer >= 500) { return
		 * true; } } else { m_errorTimer = 0; }
		 * 
		 * if (totalTime.get() >= Math.abs(m_driveDistance / 150)) { return
		 * true; }
		 * 
		 */
		return false;

	}

	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		// RobotMap.driveEncoderSteering.disable();
		// RobotMap.driveSpeedControl.disable();
		Drive.setAllMotors(0.0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		this.end();
	}
}