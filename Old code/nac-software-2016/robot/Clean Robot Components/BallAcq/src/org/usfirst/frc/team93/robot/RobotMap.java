package org.usfirst.frc.team93.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//Here is where we initialize all of the physical components of the robot.  They aren't used here.
    public static Victor LEFT_BALL_ACQ;
    public static Victor RIGHT_BALL_ACQ;
    public static Victor BALL_ACQ_BELT;
    
    public static DigitalInput BALL_CHECK;
    
    public RobotMap()
    {
        //Here is where all of our physical components are linked to a port on the RoboRIO.
    	LEFT_BALL_ACQ = new Victor(0);//The left motor on the acquisition tubing
    	RIGHT_BALL_ACQ = new Victor(8);//The right motor on the acquisition tubing
    	BALL_ACQ_BELT = new Victor(1);//The central motor on the belt for acquisition
    	
    	BALL_CHECK = new DigitalInput(9);;//Sensors that detect when something blocks their path; IE a ball is loaded
    }
}