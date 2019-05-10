/**
 * 
 */
package org.usfirst.frc93.Team93Robot2015.utilities;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDSource;

/**
 * @author Team93
 * @codereview ColbyMcKinley: In this area you should write a brief description
 *             of the utility, within the comments.
 *
 */
public class Team93Potentiometer extends AnalogInput implements PIDSource {

    double m_voltageOffset;
    double m_voltageScaling;

    public Team93Potentiometer(int channel, double voltageScaling,
            double voltageOffset) {
        super(channel);
        m_voltageOffset = voltageOffset;
        m_voltageScaling = voltageScaling;
    }

    @Override
    public double pidGet() {
        return getAngle();
    }

    public double getAngle() {
        double result = getVoltage();
        result *= m_voltageScaling;
        result += m_voltageOffset;
        return result;
    }

    public void setOffset(double newOffset) {
        m_voltageOffset = newOffset;
    }

    public void setScaling(double newScaling) {
        m_voltageScaling = newScaling;
    }

}
