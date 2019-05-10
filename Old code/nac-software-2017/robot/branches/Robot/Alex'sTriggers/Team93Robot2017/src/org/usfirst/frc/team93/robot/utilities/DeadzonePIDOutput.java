package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.PIDOutput;

public class DeadzonePIDOutput implements PIDOutput{
	
	PIDOutput m_output;
	public DeadzonePIDOutput(PIDOutput pidOutput)
	{
		m_output = pidOutput;
	}
	
	@Override
	public void pidWrite(double output) {
		if (Math.abs(output) > 0.1)
		{
			m_output.pidWrite(output);
		}
		else
		{
			m_output.pidWrite(0);
		}
	}

}
