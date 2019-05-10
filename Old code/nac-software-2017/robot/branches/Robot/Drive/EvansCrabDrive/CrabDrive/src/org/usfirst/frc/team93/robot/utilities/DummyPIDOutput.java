package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.PIDOutput;

/**
 * PIDOutput that ONLY prints the output.
 * @author Evans Chen
 * This PIDOutput does nothing.
 * Use for debug purposes only.
 */
public class DummyPIDOutput implements PIDOutput {
	
	/**
	 * Constructor for the dummy PIDOutput that does nothing.
	 * @param name The name of the PIDOutput.
	 */
	public DummyPIDOutput()
	{
	}
	
	/**
	 * Prints the result of the PID Write.
	 */
	@Override
	public void pidWrite(double output) {
	}

}
