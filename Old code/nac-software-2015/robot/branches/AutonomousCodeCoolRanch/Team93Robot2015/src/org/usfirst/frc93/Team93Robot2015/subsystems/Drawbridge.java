package org.usfirst.frc93.Team93Robot2015.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc93.Team93Robot2015.RobotMap;

/**
 * @author New Apple Corps Robotics Team 93
 * 
 * @codereview ColbyMcKinley: In this area you should write a brief description
 *             of the subsystem, within the comments.
 */

public class Drawbridge extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    /**
     * Gets whether or not the Drawbridge is down.
     * 
     * @return If the drawbridge is down, returns true. Otherwise, returns
     *         false.
     */
    public boolean getDrawbridgeState() {
        return (RobotMap.DRAWBRIDGE_SOLENOID.get() == DoubleSolenoid.Value.kForward);
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
}
