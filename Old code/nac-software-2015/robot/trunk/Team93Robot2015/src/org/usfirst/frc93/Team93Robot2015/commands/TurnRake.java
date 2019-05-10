// RobotBuilder Version: 1.5
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc93.Team93Robot2015.RobotMap;

/**
 * This command turns the rake
 */
public class TurnRake extends Command {

    private double m_RakeDistance;
    private double m_maxError;

    double m_p = -0.5;
    double m_i = 0.0;
    double m_d = 0.0;

    /**
     * Turns the rake to a setpoint based off an encoder value
     * 
     * @param RakeDistance
     *            The setpoint to turn the rake to.
     * @param maxError
     *            The maximum error allowed.
     * 
     */
    public TurnRake(double RakeDistance, double maxError) {
        m_RakeDistance = RakeDistance;
        m_maxError = maxError;
    }

    /**
     * Turns the rake to a setpoint based off of encoder value
     * 
     * @param RakeDistance
     *            The setpoint to turn the rake to based off of encoder value
     * @param maxError
     *            The maximum error allowed.
     * @param p
     *            The P value to set the PIDController to.
     * @param i
     *            The I value to set the PIDController to.
     * @param d
     *            The D value to set the PIDController to.
     */
    public TurnRake(double RakeDistance, double maxError, double p, double i,
            double d) {
        m_RakeDistance = RakeDistance;
        m_maxError = maxError;
        m_p = p;
        m_i = i;
        m_d = d;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        RobotMap.rakeControl.reset();
        RobotMap.rakeControl.enable();
        RobotMap.rakeControl.setSetpoint(m_RakeDistance);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        RobotMap.rakeControl.setPID(m_p, m_i, m_d);
        // PID Controller is moving the motors.
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    /**
     * This function determines if we have reached our goal, and if we should continue driving or not
     * @return if the command has completed its goal
     */
    protected boolean isFinished() {
        double currentError = Math.abs(RobotMap.rakeControl.getError());
        if (currentError <= m_maxError) {
            return true;
        }
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        RobotMap.rakeControl.disable();
        RobotMap.RAKE_MOTOR.set(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        this.end();
    }
}
