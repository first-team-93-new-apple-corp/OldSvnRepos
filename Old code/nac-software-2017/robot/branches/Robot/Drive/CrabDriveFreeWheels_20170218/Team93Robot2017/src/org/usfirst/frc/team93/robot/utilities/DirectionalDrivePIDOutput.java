package org.usfirst.frc.team93.robot.utilities;

import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.PIDOutput;

/**
 * We're using this utility during our DriveForward command as our PID output.
 * It verifies that as we drive, any change in the yaw/heading of the robot is
 * made up for by a change in the direction of the crab wheels. The robot
 * continues to drive forward even if the heading is not straight forwards.
 */
public class DirectionalDrivePIDOutput implements PIDOutput
{
	double m_driveDirection;

	/*
	 * This utility is used during autonomous driving as a PID output to ensure
	 * that any change in heading of the robot is made up for by a change in the
	 * direction our crab drive wheels are facing.
	 * 
	 * @param driveDirection The desired direction for the crab drive wheels to
	 * be facing (ex: 90 for right)
	 */
	public DirectionalDrivePIDOutput(double driveDirection)
	{
		m_driveDirection = driveDirection;
	}

	@Override
	public void pidWrite(double output)
	{
		// set the drive speed to the output value the PID calculates
		// set the crab wheels to rotate opposite to the gyro reading
		// (if our heading changes, we want to change the direction our crab
		// wheels are facing)
		// set the heading to the current gyro reading (this does nothing, but
		// an argument is required)
		Drive.CRAB_DRIVE_COORDINATOR.request(output, -Drive.GYRO.get() + m_driveDirection, Drive.GYRO.get());
	}
}