package org.usfirst.frc93.Team93Robot2015.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc93.Team93Robot2015.RobotMap;
import org.usfirst.frc93.Team93Robot2015.commands.ElevatorHoldPosition;

/**
 * This is the subsystem for elevator
 * 
 * @author New Apple Corps Robotics Team 93
 */
public class Elevator extends Subsystem {

    private static final double ENCODER_LIMIT_BOTTOM = -50.0;
    private static final double ENCODER_LIMIT_TOP = 1294.0;
    private static double m_offset = 0.0;
    private static double m_setpoint = 0.0;

    public Elevator() {

    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new ElevatorHoldPosition());
    }

    public static boolean topStop() {
        return (RobotMap.ELEVATOR_ENCODER.pidGet() >= ENCODER_LIMIT_TOP);
    }

    public static boolean bottomStop() {
        return (RobotMap.ELEVATOR_ENCODER.pidGet() <= ENCODER_LIMIT_BOTTOM);
    }

    /**
     * Sets the elevator motors to the given value.
     * 
     * @param value
     *            The value to set the Elevator Motors to.
     */
    public static void setElevatorMotors(double value) {
        RobotMap.elevatorGroup.set(value);
        m_setpoint = value;
    }

    /**
     * Stops the elevator motors.
     */
    public static void stopElevatorMotors() {
        RobotMap.elevatorGroup.disable();
    }

    /**
     * Gets the elevator position.
     * 
     * @return Returns the Encoder value, giving the elevator position.
     */
    public static double getEncoder() {
        return RobotMap.ELEVATOR_ENCODER.getDistance();
    }

    public static void setPIDSetpoint(double setpoint) {
        RobotMap.elevatorControl.setSetpoint(setpoint - m_offset);
    }

    /**
     * @param newOffset
     *            The new offset we want the elevator set to.
     */
    public static void setOffset(double newOffset) {
        m_offset = newOffset;
    }

    /**
     * @return returns the offset
     */
    public static double getOffset() {
        return m_offset;
    }

    /**
     * 
     * @return returns the setPoint
     */
    public static double getSetpoint() {
        return (m_setpoint);

    }
}