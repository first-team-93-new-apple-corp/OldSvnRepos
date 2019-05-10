package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * 
 * @author ben.fager
 *         Used to use an encoder with getRaw for the PID source so that it
 *         works and stuff
 */
public class RawPIDEncoder implements PIDSource
{
	private Encoder m_encoder;
	private PIDSourceType m_sourceType;
	
	public RawPIDEncoder(Encoder encoder)
	{
		m_encoder = encoder;
		m_sourceType = PIDSourceType.kDisplacement;
	}
	
	@Override
	public void setPIDSourceType(PIDSourceType pidSource)
	{
		// TODO Auto-generated method stub
		m_sourceType = pidSource;
	}
	
	@Override
	public PIDSourceType getPIDSourceType()
	{
		// TODO Auto-generated method stub
		return m_sourceType;
	}
	
	@Override
	public double pidGet()
	{
		// TODO Auto-generated method stub
		return m_encoder.getRaw();
	}
	
}
