package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Drive extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    static double m_maxspeed = 1.0;
    
    public static void setLeftMotors(double speed)
    {
    	double real_speed = limit(speed, m_maxspeed);
    	RobotMap.DRIVE_LEFT_FRONT.set(real_speed);
    	RobotMap.DRIVE_LEFT_BACK.set(real_speed);
    }
    
    public static void setRightMotors(double speed)
    {
    	double real_speed = limit(speed, m_maxspeed);
    	RobotMap.DRIVE_RIGHT_FRONT.set(real_speed);
    	RobotMap.DRIVE_RIGHT_BACK.set(real_speed);
    }
    
    public static void resetSensors()
    {
    	RobotMap.LEFT_DRIVE_ENCODER.reset();
    	RobotMap.RIGHT_DRIVE_ENCODER.reset();
    }
    
    public static void setAllMotors(double speed)
    {
    	setLeftMotors(speed);
    	setRightMotors(speed);
    }
    
    public static void setSpeedLimit(double speed)
    {
    	m_maxspeed = speed;
    }
    
    public static double getSpeedLimit()
    {
    	return m_maxspeed;
    }
    
    public static double limit(double value, double limit)
    {
    	double val = Math.abs(value);
    	if (Math.abs(value) > limit)
    	{
    		val = limit;
    	}
    	return val * Math.signum(value);
    }
}