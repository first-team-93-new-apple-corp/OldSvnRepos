package frc.robot.other;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class RollAsPIDSource implements PIDSource
{
    AHRS m_gyro;

    public RollAsPIDSource(AHRS gyro)
    {
        m_gyro = gyro;
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {

    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
    }

    @Override
    public double pidGet() {
        return m_gyro.getRoll();
	}


}