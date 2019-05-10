package org.usfirst.frc93.Team93Robot2015.subsystems;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc93.Team93Robot2015.RobotMap;
import org.usfirst.frc93.Team93Robot2015.utilities.PIDOutput2Group;

/**
 * @author New Apple Corps Robotics Team 93
 * 
 * @codereview ColbyMcKinley: In this area you should write a brief description
 *             of the subsystem, within the comments.
 */

public class Elevator extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public static PIDOutput2Group elevatorGroup;

    public static PIDController elevatorControl;

    // These are not real. Values will need to be adjusted later.
    public static final double SET_POINT_BOTTOM = 0.0;
    public static final double SET_POINT_MIDDLE_ONE = 13.37;
    public static final double SET_POINT_MIDDLE_TWO = 42.0;
    public static final double SET_POINT_TOP = 69.0;

    private static double[] elevatorSetpoints = new double[3];

    public Elevator() {

        elevatorGroup = new PIDOutput2Group(RobotMap.LEFT_ELEVATOR_MOTOR,
                RobotMap.RIGHT_ELEVATOR_MOTOR, -1.0, 1.0);

        elevatorControl = new PIDController(0.0, 0.0, 0.0,
                RobotMap.ELEVATOR_ENCODER, elevatorGroup);

        elevatorSetpoints[0] = SET_POINT_BOTTOM;
        elevatorSetpoints[1] = SET_POINT_MIDDLE_ONE;
        elevatorSetpoints[2] = SET_POINT_MIDDLE_TWO;
        elevatorSetpoints[3] = SET_POINT_TOP;
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    /**
     * Sets the elevator motors to the given value.
     * 
     * @param value
     *            The value to set the Elevator Motors to.
     */
    public static void setElevatorMotors(double value) {
        elevatorGroup.set(value);
    }

    /**
     * Stops the elevator motors.
     */
    public static void stopElevatorMotors() {
        elevatorGroup.disable();
    }

    /**
     * Gets the value of the Reed switch.
     * 
     * @return Returns true if the switch is in the presence of a magnet.
     *         Otherwise, returns false.
     */
    public static boolean getReedSwitch() {
        return (!RobotMap.ELEVATOR_REED_SWITCH.get());
    }

    /**
     * Gets the elevator position.
     * 
     * @return Returns the Encoder value, giving the elevator position.
     */
    public static double getEncoderValue() {
        return (elevatorControl.get());
    }

    /**
     * Finds the setpoint closest to the given value.
     * 
     * @param value
     *            The value to find the closest setpoint to.
     * @return The encoder value of the closest setpoint.
     */
    public static double getClosestSetpoint(double value) {
        double closestSetpoint = 0.0;

        for (int setpointIndex = 0; setpointIndex < elevatorSetpoints.length; setpointIndex++) {

            if (Math.round(closestSetpoint - value) > Math
                    .round(elevatorSetpoints[setpointIndex] - value)) {
                closestSetpoint = elevatorSetpoints[setpointIndex];
            }
        }

        return (closestSetpoint);
    }

    /**
     * Tests whether or not the given value is a setpoint.
     * 
     * @param value
     *            The value to test.
     * @return If the value given is a setpoint, returns true. Otherwise,
     *         returns false.
     */
    public static boolean searchSetpointValue(double value) {

        boolean setpointExists = false;

        for (int setpointIndex = 0; setpointIndex < elevatorSetpoints.length; setpointIndex++) {
            if (Math.round(elevatorSetpoints[setpointIndex]) == Math
                    .round(value)) {
                setpointExists = true;
            }
        }
        return (setpointExists);
    }
}
