package org.usfirst.frc93.Team93Robot2015.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc93.Team93Robot2015.RobotMap;

/**
 * @author New Apple Corps Robotics Team 93
 * 
 * @codereview ColbyMcKinley: In this area you should write a brief description
 *             of the subsystem, within the comments.
 */

public class BinSnatch extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    private double m_defaultZ = 0.0;

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    /**
     * Gets the default accelerometer value when the robot is sitting on flat
     * ground. This method should be run once, when the robot starts.
     */
    public void registerDefaultZ() {
        m_defaultZ = RobotMap.ROBOT_ACCELEROMETER.getZ();

    }

    /**
     * Calculates the angle at which the robot is at, using the accelerometer.
     * 
     * @return The angle at which the robot is at. 0 degrees is on flat ground.
     */
    public double calcAngle() {
        return (Math.toDegrees(Math.acos(m_defaultZ
                / RobotMap.ROBOT_ACCELEROMETER.getZ())));
    }

}
