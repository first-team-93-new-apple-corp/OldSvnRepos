/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package RobotCode;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SimpleRobot;
//import RobotCode.liboverride.*;
import robotemulator.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotMain { //extends IterativeRobot {

    Victor motor1;
    Joystick stick1;
    
    public void RobotMain(){
    }

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        motor1 = new Victor(1);
        stick1 = new Joystick(1);
    }

    public void autonomousInit() {
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        motor1.set(0.9);
    }
    
    public void autonomousContinuous() {
        // nothing to do
    }

    public void teleopInit() {
	// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        motor1.set( stick1.getX() );
    }
    
    public void teleopContinuous() {
        // nothing to do
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {

    }
}
