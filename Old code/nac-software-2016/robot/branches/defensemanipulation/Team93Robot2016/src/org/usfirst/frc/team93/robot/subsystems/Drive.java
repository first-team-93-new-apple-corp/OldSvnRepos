package org.usfirst.frc.team93.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.commands.DriveContinuous;

/**
 * This is the subsystem code for drive
 * 
 * @author New Apple Corps Robotics Team 93
 * 
 */
public class Drive extends Subsystem 
{

    public Drive()
    {
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    @Override
    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new DriveContinuous(1.0));

    }

    /**
     * Sets the left motor group.
     * 
     * @param value
     *            The value to set the left motor group to.
     */
    public static void setLeftMotors(double value) 
    {
        RobotMap.leftDriveGroup.set(value);
    }

    /**
     * Sets the right motor group.
     * 
     * @param value
     *            The value to set the right motor group to.
     */
    public static void setRightMotors(double value)
    {
        RobotMap.rightDriveGroup.set(value);
    }

    /**
     * Sets all of the motors to the given value. Uses the gains of each motor
     * group. To ignore gains, use setAllMotorsIgnoreGains.
     * 
     * @param value
     *            The value to set the motors to.
     */
    public static void setAllMotors(double value)
    {
        setLeftMotors(value);
        setRightMotors(value);
    }

    /**
     * Sets all of the motors to the given value, ignoring gains.
     * 
     * @param value
     *            The value to set the motors to, ignoring gains.
     */
    public static void setAllMotorsIgnoreGains(double value)
    {
        
    	double oldLeftGain = RobotMap.driveAllMotorsGroup.getGainA();
        double oldRightGain = RobotMap.driveAllMotorsGroup.getGainB();

        RobotMap.driveAllMotorsGroup.setGains(1.0, 1.0);

        RobotMap.driveAllMotorsGroup.set(value);

        RobotMap.driveAllMotorsGroup.setGains(oldLeftGain, oldRightGain);
        
    }

    /**
     * Resets the Drive Encoders.
     */
    public static void resetSensors() 
    {
        //RobotMap.RIGHT_MOTOR_ENCODER.reset();
        //RobotMap.LEFT_MOTOR_ENCODER.reset();
    }
}


