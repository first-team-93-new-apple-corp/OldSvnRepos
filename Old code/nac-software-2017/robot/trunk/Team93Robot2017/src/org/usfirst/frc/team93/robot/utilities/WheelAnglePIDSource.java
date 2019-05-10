package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.PIDSource;

/**
 * This is an interface used to make sure that everything we use as a PIDSource
 * for the crab wheels has the methods we need
 */
public interface WheelAnglePIDSource extends PIDSource
{
	
	public void reset();
	
	public double get();
	
	public void set(double value);
	
	public void setGain(double gain);
	
	public void finishReset();
	
	public int getMisreads();
}
