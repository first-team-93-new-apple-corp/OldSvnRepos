package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.PIDOutput;

/**
 * PIDOutputEnhanced
 * 
 * @author Evans Chen PIDOutput with a gain multiplier.
 */
public interface PIDOutputEnhanced extends PIDOutput
{
	/**
	 * Sets the value of the PIDOutput using pidWrite().
	 * 
	 * @param value The value to set the PIDOutput to.
	 */
	public void set(double value);
	
	/**
	 * Gets the last set value.
	 * 
	 * @return The last set value of the PIDOutputEnhanced
	 */
	public double get();
	
	/**
	 * Gets the gain.
	 * 
	 * @return The gain multiplier
	 */
	public double getGain();
	
	/**
	 * Sets the gain.
	 * 
	 * @param value The gain multiplier to set the PIDOutputEnhanced to.
	 */
	public void setGain(double value);
	
	/**
	 * Returns the PIDOutput that this PIDOutputEnhanced writes to.
	 * 
	 * @return The PIDOutput
	 */
	public PIDOutput getPIDOutput();
}
