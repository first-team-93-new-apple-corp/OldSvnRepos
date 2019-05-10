package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.PIDSource;

/**
 * PIDSourceEnhanced
 * 
 * @author Evans Chen This interface allows additional functionality to be
 * placed on PIDSources, such as a gain multiplier.
 */
public interface PIDSourceEnhanced extends PIDSource
{
	/**
	 * Alias of pidGet() for ease of use.
	 * 
	 * @return The pidGet() value.
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
	 * @param value The gain multiplier to set the PIDSourceExtended to.
	 */
	public void setGain(double value);
	
	/**
	 * Returns the PIDSource that this PIDSourceExtended takes input from.
	 * 
	 * @return The PIDSource
	 */
	public PIDSource getPIDSource();
	
}
