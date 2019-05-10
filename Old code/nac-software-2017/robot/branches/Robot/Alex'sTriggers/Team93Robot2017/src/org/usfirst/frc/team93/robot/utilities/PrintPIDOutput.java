package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.PIDOutput;

/**
 * PIDOutput that ONLY prints the output.
 * @author Evans Chen
 * Adding this as a PIDOutput to a PID will cause it to ONLY print the PID's output.
 * Use for debug purposes only.
 */
public class PrintPIDOutput implements PIDOutput {

	String m_name = "";
	
	/**
	 * Constructor for the dummy PIDOutput that only prints.
	 * @param name The name of the PIDOutput.
	 */
	public PrintPIDOutput(String name)
	{
		m_name = name;
	}
	
	/**
	 * Prints the result of the PID Write.
	 */
	@Override
	public void pidWrite(double output) {
		System.out.println(m_name + ": " + output);
	}

}
