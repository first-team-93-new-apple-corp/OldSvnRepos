package org.usfirst.frc93.Team93Robot2015.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc93.Team93Robot2015.RobotMap;

/**
 * This is the subsystem code for drawbridge
 * 
 * @author New Apple Corps Robotics Team 93
 * 
 */
public class Drawbridge extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    /**
     * Extends the drawbridge.
     */
    public void extendDrawbridge() {
        RobotMap.DRAWBRIDGE_SOLENOID.set(DoubleSolenoid.Value.kForward);
    }

    /**
     * Retracts the drawbridge.
     */
    public void retractDrawbridge() {
        RobotMap.DRAWBRIDGE_SOLENOID.set(DoubleSolenoid.Value.kReverse);
    }

    /**
     * @return Whether or not the drawbridge is extended.
     */
    public boolean getDrawbridgeState() {
        if (DoubleSolenoid.Value.kReverse == RobotMap.DRAWBRIDGE_SOLENOID.get()) {
            return false;
        }
        return true;
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
}
