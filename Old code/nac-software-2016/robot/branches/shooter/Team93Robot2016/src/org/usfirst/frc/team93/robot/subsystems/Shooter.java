package org.usfirst.frc.team93.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Shooter extends Subsystem 
{
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	double m_position;

    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public double GetPosition()
    {	
    	return m_position;
    }
}

