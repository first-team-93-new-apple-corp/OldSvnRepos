package org.usfirst.frc.team93.robot.Utilities;

import edu.wpi.first.wpilibj.CANTalon;

/**
 * This utility writes to a talon
 */

public class Team93CANTalon extends CANTalon 
{

    double m_gain;

    /**
     * 
     * @param deviceNumber
     *            yes
     * @param gain
     *            Gain for the CAN Talon
     */
    public Team93CANTalon(int deviceNumber, double gain) 
    {
        super(deviceNumber);
        m_gain = gain;
    }

    public Team93CANTalon(int deviceNumber) 
    {
        super(deviceNumber);
        m_gain = 1.0;
    }

    /**
     * Sets the gain of the SpeedControllerExtension.
     * 
     * @param gain
     *            The value to set the gain to.
     */
    public void setGain(double gain) 
    {
        m_gain = gain;
    }

    /**
     * Sets the value, multiplied by the gain.
     * 
     * @param Value
     *            The value to multiply by the gain and then set the
     *            SpeedController to.
     */
    @Override
    public void set(double value) 
    {
        super.set(value * m_gain);
    }

    @Override
    public void pidWrite(double value) 
    {
        set(value);
    }
}
