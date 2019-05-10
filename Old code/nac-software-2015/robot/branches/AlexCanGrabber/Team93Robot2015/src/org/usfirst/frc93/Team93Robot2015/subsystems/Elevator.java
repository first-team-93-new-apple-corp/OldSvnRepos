package org.usfirst.frc93.Team93Robot2015.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc93.Team93Robot2015.RobotMap;

/**
 *
 */
public class Elevator extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    private static void setLeftElevatorMotor(double value) {
        RobotMap.leftElevatorMotor.set(value);
    }

    private static void setRightElevatorMotor(double value) {
        RobotMap.rightElevatorMotor.set(value);
    }

    public static void setElevatorMotors(double value) {
        setLeftElevatorMotor(-value);
        setRightElevatorMotor(value);
    }

    public static void stopElevatorMotors() {
        setElevatorMotors(0);
    }
}
