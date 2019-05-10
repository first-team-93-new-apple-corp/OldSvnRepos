package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.Talon;

/**
 * This utility writes to a talon
 */

public class SpeedControllerExtension extends Talon 
{

    double m_gain;

    /**
     * @param channel
     */
    public SpeedControllerExtension(int channel, double gain) 
    {
        super(channel);
        m_gain = gain;
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
     * @param The
     *            value to multiply by the gain and then set the SpeedController
     *            to.
     */
    @Override
    public void set(double value) 
    {
        super.set(value * m_gain);
    }

}
