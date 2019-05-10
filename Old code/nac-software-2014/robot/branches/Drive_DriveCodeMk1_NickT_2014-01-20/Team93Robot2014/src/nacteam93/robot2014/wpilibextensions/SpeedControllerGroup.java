/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nacteam93.robot2014.wpilibextensions;

import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 * @author Controls
 */
public class SpeedControllerGroup implements SpeedController {

    private double m_LastSetValue = 0.0;
    
    private SpeedController m_motorA;
    private SpeedController m_motorB;
    private double m_gainA;
    private double m_gainB;
    
    SpeedControllerGroup(SpeedController motorA, SpeedController motorB, double gainA, double gainB)
    {
        m_motorA = motorA;
        m_motorB = motorB;
        m_gainA = gainA;
        m_gainB = gainB;
    }
    
    /**
     * Common interface for getting the current set speed of a speed controller.
     *
     * @return The current set speed.  Value is between -1.0 and 1.0.
     */
    public double get()
    {
        return m_LastSetValue;
    }

    /**
     * Common interface for setting the speed of a speed controller.
     *
     * @param speed The speed to set.  Value should be between -1.0 and 1.0.
     * @param syncGroup The update group to add this Set() to, pending UpdateSyncGroup().  If 0, update immediately.
     */
    public void set(double speed, byte syncGroup)
    {
        set(speed);
    }

    /**
     * Common interface for setting the speed of a speed controller.
     *
     * @param speed The speed to set.  Value should be between -1.0 and 1.0.
     */
    public void set(double speed)
    {
        m_LastSetValue = speed;
        // TODO: Set the speed controllers here
        m_motorA.set(speed * m_gainA);
        m_motorB.set(speed * m_gainB);
    }

    /**
     * Disable the speed controller
     */
    public void disable()
    {
        set(0.0);
    }


    /**
     * Set the output to the value calculated by PIDController
     * @param output the value calculated by PIDController
     */
    public void pidWrite(double output)
    {
       set(output); 
    }

    
}
