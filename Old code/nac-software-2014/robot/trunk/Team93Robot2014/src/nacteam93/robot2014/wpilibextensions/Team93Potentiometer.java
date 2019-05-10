/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nacteam93.robot2014.wpilibextensions;

import edu.wpi.first.wpilibj.*;

/**
 *
 * @author N.E.W. Apple Corps Team 93 Controls
 */

/**
 * @EvansChen: CONOR MARES! PLEASE SALVAGE THIS CODE PLEASE!
 * Make it look beautiful with whitespace and comments everywhere.
 * Make it so that even an idiot who has never seen code before can understand.
 */
public class Team93Potentiometer extends AnalogChannel {
    
    double m_voltageOffset;
    double m_voltageScaling;
    
    public Team93Potentiometer(int moduleNumber, int channel, double voltageOffset, double voltageScaling) {
        super(moduleNumber, channel);
        this.m_voltageOffset = voltageOffset;
        this.m_voltageScaling = voltageScaling;
    }
    
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