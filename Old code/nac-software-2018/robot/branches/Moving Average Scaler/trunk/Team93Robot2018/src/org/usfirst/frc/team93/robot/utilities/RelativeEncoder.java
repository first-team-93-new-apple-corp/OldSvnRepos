package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class RelativeEncoder implements PIDSource
{
	PIDSourceType sourceType;
	Encoder encoder;
	double resetVal = 0;
	
	public RelativeEncoder(Encoder input)
	{
		encoder = input;
	}
	
	@Override
	public void setPIDSourceType(PIDSourceType pidSource)
	{
		sourceType = pidSource;
	}
	
	@Override
	public PIDSourceType getPIDSourceType()
	{
		// TODO Auto-generated method stub
		return sourceType;
	}
	
	@Override
	public double pidGet()
	{
		// TODO Auto-generated method stub
		return (encoder.getRaw() - resetVal);
	}
	
	public void Reset()
	{
		resetVal = encoder.getRaw();
	}
	
}
