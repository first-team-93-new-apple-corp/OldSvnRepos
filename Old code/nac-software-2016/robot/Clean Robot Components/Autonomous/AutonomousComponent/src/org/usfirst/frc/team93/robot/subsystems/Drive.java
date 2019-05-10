
package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *  This is the drive subsystem.  It holds the functions that will be used in commands.
 */
public class Drive extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
	/**
	 * Sets the left motor group.
	 * 
	 * @param value
	 *            The value to set the left motor group to.
	 */
	public static void setLeftMotors(double speed)
	{
		RobotMap.DRIVE_LEFT_MOTOR_FRONT.set(speed);
		RobotMap.DRIVE_LEFT_MOTOR_TOP.set(speed);
		RobotMap.DRIVE_LEFT_MOTOR_BACK.set(speed);
	}

	/**
	 * Sets the right motor group.
	 * 
	 * @param value
	 *            The value to set the right motor group to.
	 */
	public static void setRightMotors(double speed)
	{
		RobotMap.DRIVE_RIGHT_MOTOR_FRONT.set(speed);
		RobotMap.DRIVE_RIGHT_MOTOR_TOP.set(speed);
		RobotMap.DRIVE_RIGHT_MOTOR_BACK.set(speed);
	}

	/**
	 * Sets all of the motors to the given value. Uses the gains of each motor
	 * group. To ignore gains, use setAllMotorsIgnoreGains.
	 * 
	 * @param value
	 *            The value to set the motors to.
	 */
	public static void setAllMotors(double speed)
	{
		setLeftMotors(speed);
		setRightMotors(speed);
	}
	
	/**
	 * Resets the Drive Encoders.
	 */
	public static void resetSensors()
	{
		RobotMap.RIGHT_DRIVE_ENCODER.reset();
		RobotMap.LEFT_DRIVE_ENCODER.reset();
	}
}

