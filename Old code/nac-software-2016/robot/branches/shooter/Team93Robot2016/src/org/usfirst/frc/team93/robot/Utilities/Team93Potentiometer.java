/**
 * 
 */
package org.usfirst.frc.team93.robot.Utilities;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDSource;

/**
 * This utility writes to a potentiometer
 * 
 * @author conor.mares
 * @version 2.0
 * @since 02-04-2015
 */
public class Team93Potentiometer extends AnalogInput implements PIDSource
{

    private double m_voltageOffset;
    private double m_voltageScaling;

    /**
     * @param channel
     *            the channel on the RoboRio where the Potentiometer is located
     * 
     * @param voltageScaling
     *            the scaling factor (slope) of the linear equation used to
     *            convert a voltage to an angle
     * 
     * @param voltageOffset
     *            the offset (y-intercept) of the linear equation used to
     *            convert a voltage to an angle
     */
    public Team93Potentiometer(int channel, double voltageScaling,
            double voltageOffset) 
    {
        super(channel);
        m_voltageOffset = voltageOffset;
        m_voltageScaling = voltageScaling;
    }

    /**
     * @see edu.wpi.first.wpilibj.AnalogInput#pidGet()
     * 
     * @return the angle of the potentiometer through the PID Source interface
     */
    @Override
    public double pidGet() 
    {
        return getAngle();
    }

    /**
     * @return the angle of the potentiometer
     */
    public double getAngle() 
    {
        double result = getVoltage();
        result *= m_voltageScaling;
        result += m_voltageOffset;
        return result;
    }

    /**
     * @param newScaling
     *            the scaling (slope) of the linear equation used to convert a
     *            voltage to an angle
     */
    public void setVoltageScaling(double newScaling) 
    {
        m_voltageScaling = newScaling;
    }

    /**
     * @return the scaling (slope) of the linear equation used to convert a
     *         voltage to an angle
     */
    public double getVoltageScaling() 
    {
        return m_voltageScaling;
    }

    /**
     * @param newOffset
     *            the offset (y-intercept) of the linear equation used to
     *            convert a voltage to an angle
     */
    public void setVoltageOffset(double newOffset)
    {
        m_voltageOffset = newOffset;
    }

    /**
     * @return the offset (y-intercept) of the linear equation used to convert a
     *         voltage to an angle
     */
    public double getVoltageOffset() 
    {
        return m_voltageOffset;
    }

}
