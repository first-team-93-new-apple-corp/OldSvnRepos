package org.usfirst.frc93.Team93Robot2015.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc93.Team93Robot2015.RobotMap;

/**
 * @author New Apple Corps Robotics Team 93
 * 
 * @codereview ColbyMcKinley: In this area you should write a brief description
 *             of the subsystem, within the comments.
 */

public class Rake extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    private double m_encoderPosition; // Encoder Angle Value

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    /**
     * Sets the Rake Motor.
     * 
     * @param speed
     *            The speed of the rake motor.
     */
    public void setRakeMotor(double speed) {
        RobotMap.RAKE_MOTOR.set(speed); // Sets the rake speed
    }

    /**
     * Gets the Magnetic Encoder on the Rake.
     * 
     * @return The Magnetic Encoder value. Gives absolute position.
     */
    public double getRakeEncoder() {
        m_encoderPosition = (RobotMap.RAKE_CLAW_ENCODER.getVoltage() * (360 / 5));
        return m_encoderPosition;
        // Get Encoder Angle
    }

    /**
     * Whether or not the bin is in position for the rake to obtain.
     * 
     * @return If the bin limit switch is pressed, returns true. Otherwise,
     *         returns false.
     */
    public boolean rakeBinInPosition() {
        return (RobotMap.BIN_SENSOR_LIMIT_SWITCH.get());
    }

}
