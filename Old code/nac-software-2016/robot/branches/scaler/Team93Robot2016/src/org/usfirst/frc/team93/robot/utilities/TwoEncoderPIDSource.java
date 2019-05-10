/**
 * 
 */
package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * @author Controls
 *
 */
public class TwoEncoderPIDSource implements PIDSource
{

	Encoder m_encoderRight;
	Encoder m_encoderLeft;
	double m_gainRight;
	double m_gainLeft;

	/**
	 * @codereview ColbyMcKinley Why are the encoders not zero indexed?
	 * @param encoderOne
	 * @param encoderTwo
	 * @param gainRight
	 * @param gainLeft
	 */
	public TwoEncoderPIDSource(Encoder encoderOne, Encoder encoderTwo, double gainRight, double gainLeft)
	{
		m_encoderRight = encoderOne;
		m_encoderLeft = encoderTwo;
		m_gainRight = gainRight;
		m_gainLeft = gainLeft;
	}

	/**
	 * @codereivew ColbyMcKinley: Why are these encoders not zero index.
	 * @param encoderOne
	 * @param encoderTwo
	 */
	public TwoEncoderPIDSource(Encoder encoderOne, Encoder encoderTwo)
	{
		this(encoderOne, encoderTwo, 1.0, 1.0);
	}

	public void setGains(double gainRight, double gainLeft)
	{
		m_gainRight = gainRight;
		m_gainLeft = gainLeft;
	}

	public double getRightGain()
	{
		return m_gainRight;
	}

	public double getLeftGain()
	{
		return m_gainLeft;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.wpi.first.wpilibj.PIDSource#pidGet()
	 */
	@Override
	public double pidGet()
	{
		// TODO Auto-generated method stub
		double result = m_encoderLeft.pidGet() - m_encoderRight.pidGet();
		return result;
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public PIDSourceType getPIDSourceType()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
