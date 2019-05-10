/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nacteam93.robot2014.wpilibextensions;

import edu.wpi.first.wpilibj.*;

/**
 *
 * @author NAC Controls
 */
public class Team93Potentiometer extends AnalogChannel {
    
    double voltageOffset;
    double voltageScaling;
    
    public Team93Potentiometer(int moduleNumber, int channel, double voltageOffset, double voltageScaling){
        super(moduleNumber, channel);
        this.voltageOffset = voltageOffset;
        this.voltageScaling = voltageScaling;
    }
    
    public double pidGet(){
        double result = getVoltage();
        result *= voltageScaling;
        result += voltageOffset;
        return result;        
    }
    
    public double getAngle(){
        double result = getVoltage();
        result *= voltageScaling;
        result += voltageOffset;
        return result;
    }
}