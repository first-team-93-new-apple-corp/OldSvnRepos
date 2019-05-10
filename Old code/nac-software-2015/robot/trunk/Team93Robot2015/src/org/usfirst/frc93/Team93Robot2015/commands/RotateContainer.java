package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc93.Team93Robot2015.Robot;
import org.usfirst.frc93.Team93Robot2015.RobotMap;

/**
 * This command rotates the grabber based on a given set point
 */
public class RotateContainer extends Command {

    double m_setpoint;

    /**
     * 
     * @param setpoint
     *            this is the place where we want the grabber set to based off
     *            of the encoder value
     */
    public RotateContainer(double setpoint) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.grabber);
        m_setpoint = setpoint;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {

        RobotMap.rightGrabberControl.enable();
        RobotMap.rightGrabberControl.setSetpoint(m_setpoint);
        RobotMap.leftGrabberControl.enable();
        RobotMap.leftGrabberControl.setSetpoint(m_setpoint);

        // RobotMap.grabberCoordinator.setSetpoint(m_setpoint);
        // /*
        // * Obviously, this exception will never be thrown in this context, as
        // * the setpoint is passed immediately before.
        // *
        // * However, the exception still needs to exist, as it prevents a null
        // * pointer being thrown when initialize is called in inappropriate
        // * context.
        // */
        // try {
        // RobotMap.grabberCoordinator.initialize();
        // }
        // catch (nullSetpointException e) {
        // e.printStackTrace();
        // RobotMap.grabberCoordinator.setSetpoint(m_setpoint);
        // try {
        // RobotMap.grabberCoordinator.initialize();
        // }
        // catch (nullSetpointException e1) {
        // e1.printStackTrace();
        // System.out.println("nullSetpoint thrown for the second time");
        // }
        // }
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

        System.out.println("Right = " + RobotMap.RIGHT_HAND_ENCODER.getAngle()
                + "Left = " + RobotMap.LEFT_HAND_ENCODER.getAngle());

        // if (Robot.grabber.isGrabbing()) {
        // /*
        // * PID coefficients for clamped
        // */
        // RobotMap.rightGrabberControl.setPID(-0.001, 0.0, 0.0);
        // RobotMap.leftGrabberControl.setPID(-0.001, 0.0, 0.0);
        // }
        // else {
        // /*
        // * PID coefficients for unclamped
        // */
        // RobotMap.rightGrabberControl.setPID(-0.001, 0.0, 0.0);
        // RobotMap.leftGrabberControl.setPID(-0.001, 0.0, 0.0);
        // }

        // RobotMap.grabberCoordinator.update();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
        // return RobotMap.grabberCoordinator.isFinished();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        RobotMap.rightGrabberControl.disable();
        RobotMap.leftGrabberControl.disable();
        RobotMap.RIGHT_HAND_MOTOR.set(0.0);
        RobotMap.LEFT_HAND_MOTOR.set(0.0);
        // RobotMap.grabberCoordinator.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        this.end();
    }
}
