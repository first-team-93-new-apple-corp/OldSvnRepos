package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class RelativeEncoder implements PIDSource, CustomScheduledObject
{
	PIDSourceType sourceType;
	Encoder encoder;
	double resetVal = 0;
	BooleanGetter boolInput;
	
	public RelativeEncoder(Encoder input, BooleanGetter resetSource)
	{
		encoder = input;
		boolInput = resetSource;
	}
	
	@Override
	public void setPIDSourceType(PIDSourceType pidSource)
	{
		sourceType = pidSource;
	}
	
	@Override
	public PIDSourceType getPIDSourceType()
	{
		return sourceType;
	}
	
	@Override
	public double pidGet()
	{
		return encoder.getRaw() - resetVal;
	}
	
	public void Reset()
	{
		resetVal = encoder.getRaw();
	}
	
	@Override
	public void schedule()
	{
		if (boolInput.get())
		{
			Reset();
		}
	}
	
}
