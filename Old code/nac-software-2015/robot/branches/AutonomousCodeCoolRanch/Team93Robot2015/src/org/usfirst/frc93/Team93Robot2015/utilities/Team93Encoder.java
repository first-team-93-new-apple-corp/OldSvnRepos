package org.usfirst.frc93.Team93Robot2015.utilities;

import edu.wpi.first.wpilibj.PIDSource;

/**
 * @codereview ColbyMcKinley: In this area you should write a brief description
 *             of the utility, within the comments.
 */
public class Team93Encoder implements PIDSource {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    private double m_pidValue;// = getPIDValue();

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.wpi.first.wpilibj.PIDSource#pidGet()
     */
    @Override
    public double pidGet() {
        // TODO Auto-generated method stub
        return m_pidValue;
    }
}
