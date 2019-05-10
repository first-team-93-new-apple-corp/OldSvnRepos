package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * This interface ensures that everything we use as a GyroPIDSource has all of
 * these methods, which we want.
 */
public interface GyroPIDSource extends PIDSource
{
	
	/**
	 * Alias of getRawAngle().
	 * 
	 * @return The raw angle.
	 */
	public double getAngle();
	
	/**
	 * PID Source Type can only be kDisplacement. This method does nothing.
	 */
	@Override
	public void setPIDSourceType(PIDSourceType pidSource);
	
	/**
	 * Returns the raw heading, straight from the gyro, without offsets.
	 * 
	 * @return The gyro's raw heading.
	 */
	public double getRawAngle();
	
	/**
	 * Returns the global heading, unaffected by set and reset.
	 * 
	 * @return The gyro's raw global heading.
	 */
	public double getGlobalAngle();
	
	/**
	 * Returns the angle that the robot is facing.
	 * 
	 * @return The heading of the robot.
	 */
	public double get();
	
	/**
	 * Resets the PIDSource.
	 */
	public void reset();
	
	/**
	 * Sets the PIDSource to a set heading.
	 * 
	 * @param angle The angle to set the heading to.
	 */
	public void set(double angle);
	
	/**
	 * Gets the gain.
	 * 
	 * @return the gain
	 */
	public double getGain();
	
	/**
	 * Sets the gain multiplier.
	 * 
	 * @param gain
	 */
	public void setGain(double gain);
}