package frc.robot.other;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class ZeroableEncoder implements PIDSource {
    Encoder m_encoder;
    DigitalOutput m_resetSwitch;
    int zeroVal = 0;

    public ZeroableEncoder(Encoder encoder, DigitalOutput resetSwitch)
    {
        m_encoder = encoder;
        m_resetSwitch = resetSwitch;
        zeroVal = m_encoder.get();
    }
    
    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {}

    @Override
    public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
    }

    @Override
    public double pidGet() {
        if(m_resetSwitch.get())
        {
            reset();
        }
        return m_encoder.get() - zeroVal;
    }
    public void reset()
    {
        zeroVal = m_encoder.get();
    }

}