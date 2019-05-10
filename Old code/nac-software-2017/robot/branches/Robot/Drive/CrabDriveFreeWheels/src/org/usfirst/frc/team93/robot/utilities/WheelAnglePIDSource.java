package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.PIDSource;

public interface WheelAnglePIDSource extends PIDSource
{
	
	public void reset();
	
	public double get();
	
	public void set(double value);
	
	public void setGain(double gain);
	
	public void finishReset();
	
	public int getMisreads();
}
