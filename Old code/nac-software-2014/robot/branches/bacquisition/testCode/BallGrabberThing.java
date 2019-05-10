/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package nacteam93.robot2014tests;


import edu.wpi.first.wpilibj.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class BallGrabberThing extends IterativeRobot {
    Victor motorArmL;
    Victor motorArmR;
    Joystick stick1;
    double GrabMotorDirection;
    double GrabMotorScale;
    boolean Directional = false;
    boolean LoadSensor = false;
    DigitalInput limitSwitchR; 
    DigitalInput limitSwitchL;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        motorArmL = new Victor(1);
        motorArmR = new Victor(2);
        stick1 = new Joystick(1);
        limitSwitchL = new DigitalInput(1);
        limitSwitchR = new DigitalInput(2);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
               //System.out.println(stick1.getRawButton(3));
        
    	int GrabState = 0;
    	//This changes which way the grabber is facing
    	if(stick1.getRawButton(6)== true)
    	{
    		Directional = true;   		
    	}
    	if(stick1.getRawButton(5) == true)
    	{
    		Directional = false;
    	}
    	//This changes Whether or not a ball is in the grabber.
//    	if(LimitSwitchL.get()== true)
//    	{
//    		LoadSensor = true;   		
//    	}
//    	if(LimitSwitchL.get() == true)
//    	{
//    		LoadSensor = false;
//    	}
//    	 These change the state.   
    	if((stick1.getRawAxis(4) < 0.0) && (stick1.getRawButton(3) == false) && (limitSwitchL.get() == false)&& (limitSwitchR.get() == false))
    	{
   	    GrabState = 1; // Intake
    	}
   	else
   	{
   	    GrabState = 0; // Doing Nothing
   	}
        if((stick1.getRawAxis(4) > 0.0) && (stick1.getRawButton(3) == false)) 
        {
   	    GrabState = 2; // Pass 
   	}
   	if(stick1.getRawButton(3)== true)
        {
            GrabState = 3; // Emergency Eject works no matter what.
   	}
       // These are the states:
   	if(GrabState == 0)
   	{
            motorArmL.set(0);
            motorArmR.set(0);
        }
        if(GrabState == 1)
        {
            motorArmL.set(1);
            motorArmR.set(-1);
        }
        if(GrabState == 2)
        {
    	   motorArmL.set(-1);    	   
    	   motorArmR.set(1);
           
           
        }
   	if(GrabState == 3)
   	{
   	    motorArmL.set(-1);
            motorArmR.set(1);
        }
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
