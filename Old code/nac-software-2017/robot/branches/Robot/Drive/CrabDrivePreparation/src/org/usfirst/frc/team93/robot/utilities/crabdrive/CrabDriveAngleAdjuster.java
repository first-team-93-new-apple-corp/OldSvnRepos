package org.usfirst.frc.team93.robot.utilities.crabdrive;

import edu.wpi.first.wpilibj.PIDOutput;

/**
 * CrabDriveAngleAdjuster
 * @author Evans Chen
 * 
 * Crab Drive Robot Angle Control Flow:
 * Gyro -> GyroPIDSource (Source) + Joystick yaw (setpoint) -> PIDController -
 * -> CrabDriveAngleAdjuster (adjustment) + Joystick (control) -
 * -> CrabDriveSpeedMotor -> PIDOutputGroup -> Talon -> Motor -> Robot Angle
 * 
 * This class takes input from a PIDController, and adjusts the CrabDriveSpeedMotors.
 * This class is tuned by tuning the PIDController that is writing to it.
 * 
 * This class outputs to two CrabDriveSpeedMotors to control the Robot Angle.
 * It does this by:
 * 
 * 1. Limiting max speed:
 * If all motors are set to 1.0, then no angle adjustments can be made.
 * In this case, the speed of one of the motors must be limited so that the robot can turn to adjust its angle.
 * 
 * 2. Adding speed to one of the motors:
 * To compensate for being knocked off of the correct angle, this class gives a bonus to the side that is behind.
 * 
 * For example:
 * Robot is knocked around, and turns 45 degrees to the left
 * Max speed is limited to 75% power
 * Right motors turn at 75% power
 * Left motors turn at 100% power
 * Robot re-adjusts back to normal angle.
 * Max speed limits are removed.
 * 
 * NOTE:
 * This class does NOT directly write to the CrabDriveSpeedMotors!
 * It only sets the limit and adjust values.
 * These adjustments will only take effect when pidWrite or set is run the next time.
 * Thus, for this class to take effect, there MUST be a command or PIDController doing so.
 */
public class CrabDriveAngleAdjuster implements PIDOutput
{
	
	CrabDriveSpeedMotor m_left;
	CrabDriveSpeedMotor m_right;
	
	//The maximum adjustment.
	//Set to 0.5 by default so that some degree of driver control is assured.
	double maxAdjustment = 1.0;
	
	public CrabDriveAngleAdjuster(CrabDriveSpeedMotor left, CrabDriveSpeedMotor right)
	{
		m_left = left;
		m_right = right;
	}
	
	/**
	 * Takes input and adjusts the CrabDriveSpeedMotors.
	 */
	@Override
	public void pidWrite(double output)
	{
		//Limits by the maximum adjustment.
		double adjustment = limit(output, maxAdjustment);
		
		///Adjusts the CrabDriveSpeedMotors
		
		//1. Limit the max speed
		m_left.setLimit(1 - adjustment);
		m_right.setLimit(1 - adjustment);
		
		//2. Add speed to a motor to adjust angle
		//If Robot is turned to the left...
		if (adjustment >= 0)
		{
			//Add speed to the left motors to adjust
			m_left.setAdjust(Math.abs(adjustment));
			m_right.setAdjust(-Math.abs(adjustment));
		}
		//If Robot is turned to the right
		else
		{
			//Add speed to the right motors to adjust
			m_left.setAdjust(-Math.abs(adjustment));
			m_right.setAdjust(Math.abs(adjustment));
		}
	}
	
	/**
	 * Sets the maximum adjustment value.
	 * @param max The maximum adjustment value.
	 */
	public void setMaxAdjustment(double max)
	{
		maxAdjustment = max;
	}
	
	/**
	 * Limits a number so that its absolute value is under the limit.
	 * @param value The number to limit.
	 * @param limit The maximum absolute value limit.
	 * @return The limited value.
	 */
    private double limit(double value, double limit)
    {
    	double val = Math.abs(value);
    	if (Math.abs(value) > limit)
    	{
    		val = limit;
    	}
    	return val * Math.signum(value);
    }
	
}
