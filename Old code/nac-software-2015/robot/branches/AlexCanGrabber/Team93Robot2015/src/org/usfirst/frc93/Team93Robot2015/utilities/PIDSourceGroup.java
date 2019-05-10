package org.usfirst.frc93.Team93Robot2015.utilities;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

import java.util.ArrayList;

/**
 *
 */
public class PIDSourceGroup extends PIDSubsystem {

    ArrayList<PIDController> pidControllerList = new ArrayList<PIDController>();

    // Initialize your subsystem here
    public PIDSourceGroup(double p, double i, double d, double setPoint,
            PIDController pidControlerZero) {
        super(p, i, d);
        setSetpoint(setPoint);
        // Use these to get going:
        // setSetpoint() - Sets where the PID controller should move the system
        // to
        // enable() //- Enables the PID controller.
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
        return 0.0;
    }

    @Override
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);

    }
}
