/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team93.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team93.robot.Robot;

/**
 * This command drives the robot over a given distance with simple proportional
 * control This command will drive a given distance limiting to a maximum speed.
 */
public class DriveForward extends Command {
	private double m_driveForwardSpeed;
	private double m_distance;
	private double m_error;
	private static final double kTolerance = 0.1;
	private static final double kP = -1.0 / 5.0;

	PIDController driveForwardControllerFrontRight = new PIDController(0.1, 0.1, 0.1, Robot.drivetrain.getRightEncoder(), Robot.drivetrain.m_frontRightCIM);
	PIDController driveForwardControllerFrontLeft = new PIDController(0.1, 0.1, 0.1, Robot.drivetrain.getLeftEncoder(), Robot.drivetrain.m_frontLeftCIM);
	PIDController driveForwardControllerRearRight = new PIDController(0.1, 0.1, 0.1, Robot.drivetrain.getRightEncoder(), Robot.drivetrain.m_rearRightCIM);
	PIDController driveForwardControllerRearLeft = new PIDController(0.1, 0.1, 0.1, Robot.drivetrain.getLeftEncoder(), Robot.drivetrain.m_rearLeftCIM);
	
	public DriveForward() {
		this(10, 0.5);
	}

	public DriveForward(double dist) {
		this(dist, 0.5);
	}

	public DriveForward(double dist, double maxSpeed) {
		requires(Robot.drivetrain);
		m_distance = dist;
		m_driveForwardSpeed = maxSpeed;
	}

	@Override
	protected void initialize() {
		Robot.drivetrain.getRightEncoder().reset();
		Robot.drivetrain.getLeftEncoder().reset();
		driveForwardControllerFrontRight.reset();
		driveForwardControllerFrontLeft.reset();
		driveForwardControllerRearRight.reset();
		driveForwardControllerRearLeft.reset();
		setTimeout(2);
		driveForwardControllerFrontRight.enable();
		driveForwardControllerFrontLeft.enable();
		driveForwardControllerRearRight.enable();
		driveForwardControllerRearLeft.enable();
	}

	@Override
	protected void execute() {
		
	}

	@Override
	protected boolean isFinished() {
		return (driveForwardControllerFrontRight.onTarget() && 
				driveForwardControllerFrontLeft.onTarget() &&
				driveForwardControllerRearRight.onTarget() && 
				driveForwardControllerRearLeft.onTarget ());
	}

	@Override
	protected void end() {
		Robot.drivetrain.stop();
		driveForwardControllerFrontRight.disable();
		driveForwardControllerFrontLeft.disable();
		driveForwardControllerRearRight.disable();
		driveForwardControllerRearLeft.disable();
	}
}
