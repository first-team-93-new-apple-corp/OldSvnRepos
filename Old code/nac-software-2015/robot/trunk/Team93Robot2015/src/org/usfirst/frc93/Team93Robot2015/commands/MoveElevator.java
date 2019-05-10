package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc93.Team93Robot2015.Robot;
import org.usfirst.frc93.Team93Robot2015.RobotMap;
import org.usfirst.frc93.Team93Robot2015.subsystems.Elevator;

/**
 * This command moves the elevator to a given set point
 * 
 * @author New Apple Corps Team 93
 */
public class MoveElevator extends Command {

    double m_setpoint;
    double m_maxError;
    boolean m_absoluteSetpointExists;

    /**
     * 
     * @param setpoint
     *            The Encoder setpoint to move the Elevator to.
     * @param maxError
     *            The maximum error allowed.
     */
    public MoveElevator(double setpoint, double maxError) {
        m_setpoint = setpoint;
        m_maxError = maxError;
        // Use requires() here to declare subsystem dependencies
        requires(Robot.elevator);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        RobotMap.elevatorControl.reset();
        RobotMap.elevatorControl.setAbsoluteTolerance(10.0);
        RobotMap.elevatorControl.setSetpoint(Elevator.getOffset() + m_setpoint);
        RobotMap.elevatorControl.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        System.out.println("Elevator Encoder: " + Elevator.getEncoder());

        // System.out.println("Bot Switch: "
        // + RobotMap.ELEVATOR_LIMIT_SWITCH_BOTTOM.get());
        // System.out.println("Top Switch: "
        // + RobotMap.ELEVATOR_LIMIT_SWITCH_TOP.get());
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {

        if (Math.abs(RobotMap.elevatorControl.getError()) <= m_maxError) {
            return true;
        }

        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        RobotMap.elevatorControl.disable();
        Elevator.stopElevatorMotors();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        this.end();
    }
}