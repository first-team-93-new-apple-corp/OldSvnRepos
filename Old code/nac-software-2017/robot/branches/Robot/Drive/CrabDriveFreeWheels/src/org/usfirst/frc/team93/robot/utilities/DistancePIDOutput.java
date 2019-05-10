package org.usfirst.frc.team93.robot.utilities;

import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.PIDOutput;

/**
 * We're using this utility during our DriveForward command.
 * It verifies that as we drive, any change in the yaw/heading
 * of the robot is made up for by a change in the direction of
 * the crab wheels.  The robot continues to drive forward even
 * if the heading is not straight forwards.
 */
public class DistancePIDOutput implements PIDOutput
{	
    @Override
    public void pidWrite(double output) 
    {
    	//set the drive speed to the output value the PID calculates
    	//set the crab wheels to rotate opposite to the gyro reading
    	//(if our heading changes, we want to change the direction our crab wheels are facing)
    	//set the heading to the current gyro reading (this does nothing, but an argument is required)
    	Drive.CRAB_DRIVE_COORDINATOR.request(output, -Drive.GYRO.get(), Drive.GYRO.get());
    }
}