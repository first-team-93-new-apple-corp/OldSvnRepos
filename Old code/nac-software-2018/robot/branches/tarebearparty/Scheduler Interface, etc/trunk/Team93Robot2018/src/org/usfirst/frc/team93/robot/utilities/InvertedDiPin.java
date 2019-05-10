package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.DigitalInput;

public class InvertedDiPin implements BooleanGetter
{
	DigitalInput m_digitalInput;
	
	public InvertedDiPin(DigitalInput input)
	{
		
		m_digitalInput = input;
	}
	
	@Override
	public boolean get()
	{
		return (!m_digitalInput.get());
	}
	
}
