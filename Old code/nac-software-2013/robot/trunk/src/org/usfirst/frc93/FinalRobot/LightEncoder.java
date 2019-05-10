/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc93.FinalRobot;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author NAC Controls
 */
public class LightEncoder extends SensorBase implements PIDSource{
    private Counter m_counter;
    private DigitalInput m_dig;
    public LightEncoder(int slot, int channel){
        m_dig = new DigitalInput(slot,channel);
        m_counter = new Counter(m_dig);
        start();    //Right now this starts itself, but later on we might want to use the start method instead
    }
    public double getDistance(){
        return m_counter.get();
    }
    
    public double getPeriod(){
        return 1.0f/m_counter.getPeriod();
    }
    public void start(){
        m_counter.start();
    }
    public double pidGet() {
        return getRpms();
    }
    public void reset(){
        m_counter.reset();
    }
    public double getRpms(){
        SmartDashboard.putBoolean("Dig Input "+m_dig.getChannel(),m_dig.get());
        double deltaMS = getPeriod();
        double rpm =deltaMS*60.0f;
        return rpm;
    }
    
}
