/*
 * N.E.W. Apple Corps
 * FIRST Robotics Competition Team 93
 * Shared FRC Robot Code
 * http://www.nacteam93.com/
 * 
 * Copyright (C) 2014 N.E.W. Apple Corps & Appleton Area School District
 * Worldwide, non-exclusive, royalty free license is granted gratis for
 * use in all FIRST programs and educational uses.
 */

package nacteam93.robot2014.wpilibextensions;

import edu.wpi.first.wpilibj.PIDSource;
/**
 * @author NEW Apple Corps Team 93 Controls
 */

/**
 * Encoders implement the PIDSource interface. This class provides a 
 * mechanism to combine two encoders or other PIDSource implementations
 * into a single object which itself implements the PIDSource interface.
 * If one wants to do arithmetic with encoders, then one can merge them
 * into one PIDSourceGroup.
 */
public class PIDSourceGroup implements PIDSource {
    
    //Takes the fist PIDSource to "merge" into a single PIDSourceGroup.
    private PIDSource m_PIDSourceA;
    //Takes the second PIDSource to "merge" into a single PIDSourceGroup.
    private PIDSource m_PIDSourceB;
    
    //Sets a value for the value of the First PID to be multiplied by.
    private double m_gainA;
    //Sets a value for the value of the Second PID to be multiplied by.
    private double m_gainB;
    
    
    /**
     * Constructs a PIDSourceGroup
     * 
     * @param PIDSourceA The First PIDSource to merge into the PIDSourceGroup.
     * @param PIDSourceB The Second PIDSource to merge into the PIDSourceGroup.
     * @param gainA The First PIDSource's multiplier.
     * @param gainB The Second PIDSource's multiplier.
     * 
     * The gain (also called multiplier) structure allows the PIDSource to accomplish more.
     * This structure allows for flexibility, such as: 
     * One can use this to add, by setting both gains to 1,
     * One can subtract, by setting one gain to 1 and another gain to -1,
     * One can average, by setting both gains to 0.5,
     * and one can multiply, divide, and do other arithmetic, although these are much less important.
     */
    public PIDSourceGroup(PIDSource PIDSourceA, PIDSource PIDSourceB, double gainA, double gainB)
    {
        //Constructs the PIDSourceGroup by setting values to the m_PIDSource's and m_gain's above.
        m_PIDSourceA = PIDSourceA;
        m_PIDSourceB = PIDSourceB;
        m_gainA = gainA;
        m_gainB = gainB;
    }
    
    /**
     * Get the result to use in PIDController or anywhere else the PIDSource
     * interface is required.  The pidGet() method is part of the PIDSource
     * interface.
     * 
     * @return The value of the PIDSources multiplied by their set gains, then added.
     */
    public double pidGet()
    {
        double valueA = (m_PIDSourceA.pidGet() * m_gainA);
        double valueB = (m_PIDSourceB.pidGet() * m_gainB);
        return (valueA + valueB);
    }
}