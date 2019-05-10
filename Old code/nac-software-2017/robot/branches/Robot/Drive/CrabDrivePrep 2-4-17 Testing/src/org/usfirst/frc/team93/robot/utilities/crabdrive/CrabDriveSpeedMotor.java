package org.usfirst.frc.team93.robot.utilities.crabdrive;

import edu.wpi.first.wpilibj.PIDOutput;

/**
 * CrabDriveSpeedMotor
 * @author Evans Chen
 * 
 * Crab Drive Robot Angle Control Flow:
 * GyroPIDSource (Source) + Joystick yaw (setpoint) -> PIDController -
 * -> CrabDriveAngleAdjuster (adjustment) + Joystick (control) -
 * -> CrabDriveSpeedMotor -> PIDOutputGroup -> Talon -> Motor -> Robot Angle
 * 
 * CrabDriveAngleAdjuster gives input to this class.
 * This class outputs to PIDOutputGroups.
 * 
 * This class is designed to act as an output for the Speed motors of the crab drive.
 * When the robot's angle has been disturbed, CrabDriveSpeedMotors limit their maximum speed under the control of its AngleAdjuster.
 * This is done so that the robot can re-adjust.
 * When the maximum speed is limited, the robot will then allocate more speed to the side that needs to re-adjust.
 * 
 * For example:
 * Robot is knocked around, and turns 45 degrees to the left
 * Max speed is limited to 75% power
 * Right motors turn at 75% power
 * Left motors turn at 100% power
 * Robot re-adjusts back to normal angle.
 * Max speed limits are removed.
 * 
 * This system also works for different angles, as a setpoint can be passed in as well.
 * 
 * Keep in mind that, although this class is named CrabDriveSpeedMotor,
 * It is designed to write to an entire PIDOutputGroup.
 * 
 * Use:
 * Talon DRIVE_LEFT_FRONT = new Talon(0);
 * Talon DRIVE_LEFT_BACK = new Talon(1);
 * PIDOutputGroup DRIVE_LEFT = new PIDOutputGroup(LEFT_FRONT, LEFT_BACK);
 * CrabDriveSpeedMotor CRAB_DRIVE_LEFT = new CrabDriveSpeedMotor(DRIVE_LEFT);
 */
public class CrabDriveSpeedMotor implements PIDOutput
{
	
	//The bonus speed given to the motor that needs to compensate for changing angle.
	double adjust = 0.0;
	
	//The limit of the max speed.
	//Depends on the robot's angle deviation from the desired angle.
	double limit = 1.0;
	
	//the PIDOutput that this writes to
	PIDOutput m_pidOutput;
	
	//The last value the CrabDriveSpeedMotor was set to.
	//Used for get()
	double m_lastValue = 0;
	
	/**
	 * The constructor of a CrabDriveSpeedMotor.
	 * 
	 * Example:
	 * Talon DRIVE_LEFT_FRONT = new Talon(0);
	 * Talon DRIVE_LEFT_BACK = new Talon(1);
	 * PIDOutputGroup DRIVE_LEFT = new PIDOutputGroup(LEFT_FRONT, LEFT_BACK);
	 * CrabDriveSpeedMotor CRAB_DRIVE_LEFT = new CrabDriveSpeedMotor(DRIVE_LEFT);
	 * 
	 * @param output The PIDOutput to write to from the CrabDriveSpeedMotor.
	 */
	public CrabDriveSpeedMotor(PIDOutput output)
	{
		m_pidOutput = output;
	}
	
	/**
	 * Sets the CrabDriveSpeedMotor.
	 * Alias of set.
	 */
	@Override
	public void pidWrite(double output) {
		m_pidOutput.pidWrite(calculateOutput(output));
		m_lastValue = calculateOutput(output);
	}
	
	/**
	 * Sets the CrabDriveSpeedMotor.
	 * @param output The value to set the PIDOutput to.
	 */
	public void set(double output)
	{
		pidWrite(output);
	}
	
	/**
	 * Gets the value of the CrabDriveSpeedMotor.
	 * @return The last set value of the CrabDriveSpeedMotor.
	 */
	public double get()
	{
		return m_lastValue;
	}
	
	/**
	 * Sets the limit value.
	 * @param lim The maximum value the motor can be controlled to.
	 */
	public void setLimit(double lim)
	{
		limit = lim;
	}
	
	/**
	 * Gets the limit value.
	 * @return The maximum value the motor can be controlled to.
	 */
	public double getLimit()
	{
		return m_lastValue;
	}
	
	/**
	 * Sets the adjust value.
	 * @param adj The value added to adjust the robot angle.
	 */
	public void setAdjust(double adj)
	{
		adjust = adj;
	}
	
	/**
	 * Gets the adjust value.
	 * @return The value added to adjust the robot angle.
	 */
	public double getAdjust()
	{
		return m_lastValue;
	}
	
	/**
	 * The PIDOutput which the CrabDriveSpeedMotor is writing to.
	 * @return The linked PIDOutput
	 */
	public PIDOutput getPIDOutput()
	{
		return m_pidOutput;
	}
	
	/**
	 * Calculates the true output, after applying limits and adjustment.
	 * @param output The requested value.
	 * @return The true value.
	 */
	private double calculateOutput(double output)
	{
		return limit(output, limit) + adjust;
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
