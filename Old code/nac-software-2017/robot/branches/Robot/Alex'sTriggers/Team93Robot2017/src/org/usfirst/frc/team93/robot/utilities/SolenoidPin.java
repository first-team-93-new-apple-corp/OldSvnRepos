package org.usfirst.frc.team93.robot.utilities;

/**
 * EncoderPin
 * 
 * @author Evans Chen This class stores the two pins of a solenoid. Since each
 * Solenoid requires 2 pins, they are stored as A and B in this class.
 */
public class SolenoidPin
{
	// pin A for the Solenoid
	int m_a;
	// pin B for the Solenoid
	int m_b;
	
	/**
	 * The constructor for the SolenoidPin pair.
	 * 
	 * @param A The first pin.
	 * @param B The second pin.
	 */
	public SolenoidPin(int A, int B)
	{
		m_a = A;
		m_b = B;
	}
	
	/**
	 * Get the first pin.
	 * 
	 * @return
	 */
	public int A()
	{
		return m_a;
	}
	
	/**
	 * Get the second pin.
	 * 
	 * @return
	 */
	public int B()
	{
		return m_b;
	}
}