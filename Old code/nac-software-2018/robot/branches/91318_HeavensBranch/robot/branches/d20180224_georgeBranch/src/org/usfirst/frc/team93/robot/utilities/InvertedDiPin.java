package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.DigitalInput;

public class InvertedDiPin
{
	DigitalInput m_digitalInput;
	
	public InvertedDiPin(DigitalInput input)
	{
		
		m_digitalInput = input;
	}
	
	public Boolean get()
	{
		return (!m_digitalInput.get());
	}
	
}
