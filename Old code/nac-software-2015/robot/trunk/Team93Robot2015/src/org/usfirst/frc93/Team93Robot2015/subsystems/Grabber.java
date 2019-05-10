package org.usfirst.frc93.Team93Robot2015.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc93.Team93Robot2015.RobotMap;
import org.usfirst.frc93.Team93Robot2015.commands.HandControlContinuous;

/**
 * This is the grabber subsystem
 * 
 * @author New Apple Corps Robotics Team 93
 */

public class Grabber extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void init() {

    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new HandControlContinuous(-0.5));
    }

    /**
     * Sets the speed of the grabbing motors
     * 
     * @param speed
     *            set the speed for the motors
     */
    public void setHands(double speed) {
        RobotMap.RIGHT_HAND_MOTOR.set(speed);
        RobotMap.LEFT_HAND_MOTOR.set(speed);
    }

    /**
     * opens the claw
     */
    public void openClaw() {
        RobotMap.CLAW_SOLENOID.set(DoubleSolenoid.Value.kForward);
    }

    public void closeClaw() {
        RobotMap.CLAW_SOLENOID.set(DoubleSolenoid.Value.kReverse);
    }

    /**
     * 
     * @return returns if we are currently grabbing something
     */
    public boolean isGrabbing() {
        return (RobotMap.CLAW_SOLENOID.get() == DoubleSolenoid.Value.kReverse);
    }
}
