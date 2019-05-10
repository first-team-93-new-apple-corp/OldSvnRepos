package frc.robot.other;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class GyroPIDSource implements PIDSource {

    AHRS m_gyro;
    /**
     * Allows use of gyro as a PID source
     * @param gyro
     * the gyro(AHRS)that is being used
     */
    public GyroPIDSource(AHRS gyro)
    {
        m_gyro = gyro;
    }
    @Override
    public void setPIDSourceType(PIDSourceType pidSource) 
    {
        
    }

    @Override
    public PIDSourceType getPIDSourceType() 
    {
        return PIDSourceType.kDisplacement;
    }

    @Override
    public double pidGet() {
        return modulo(-m_gyro.getAngle(), 360);
    }

    private double modulo(double a, double b)
	{
		return (a < 0 ? b + (a % b) : a % b);
    }
    




}