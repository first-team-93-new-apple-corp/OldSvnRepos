/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nacteam93.robot2014.wpilibextensions;

import edu.wpi.first.wpilibj.PIDSource;
/**
 *
 * @author Controls
 */
public class PIDSourceGroup implements PIDSource {
    
    private PIDSource m_PIDSourceA;
    private PIDSource m_PIDSourceB;
    private double m_gainA;
    private double m_gainB;
    
    public PIDSourceGroup(PIDSource PIDSourceA, PIDSource PIDSourceB, double gainA, double gainB)
    {
        m_PIDSourceA = PIDSourceA;
        m_PIDSourceB = PIDSourceB;
        m_gainA = gainA;
        m_gainB = gainB;
    }
    
    
    /**
     * A description for the type of output value to provide to a PIDController
     */
    public static class PIDSourceParameter {
        public final int value;
        static final int kDistance_val = 0;
        static final int kRate_val = 1;
        static final int kAngle_val = 2;
        public static final PIDSourceParameter kDistance = new PIDSourceParameter(kDistance_val);
        public static final PIDSourceParameter kRate = new PIDSourceParameter(kRate_val);
        public static final PIDSourceParameter kAngle = new PIDSourceParameter(kAngle_val);

        private PIDSourceParameter(int value) {
            this.value = value;
        }
    }
    
    /**
     * Get the result to use in PIDController
     * @return the result to use in PIDController
     */
    public double pidGet()
    {
        return (m_PIDSourceA.pidGet()*m_gainA)+(m_PIDSourceB.pidGet()*m_gainB);
    }
}
//