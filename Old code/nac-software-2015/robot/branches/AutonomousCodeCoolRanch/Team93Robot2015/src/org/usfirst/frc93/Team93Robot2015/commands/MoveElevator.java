package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc93.Team93Robot2015.Robot;
import org.usfirst.frc93.Team93Robot2015.RobotMap;
import org.usfirst.frc93.Team93Robot2015.subsystems.Elevator;

/**
 * @codereview ColbyMcKinley: In this area you should write a brief description
 *             of the command, within the comments.
 */
public class MoveElevator extends Command {

    /**
     * @codereview ColbyMcKinley: absoluteSetpointExists should be renamed to
     *             m_absoluteSetpointExists
     */
    double m_setpoint;
    double m_maxError;
    boolean absoluteSetpointExists;

    public MoveElevator(double setpoint, double maxError) {
        m_setpoint = setpoint;

        absoluteSetpointExists = Elevator.searchSetpointValue(m_setpoint);

        m_maxError = maxError;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.ELEVATOR);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        RobotMap.ELEVATOR_ENCODER.reset();
        Elevator.elevatorControl.enable();
        Elevator.elevatorControl.setSetpoint(m_setpoint);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        if (absoluteSetpointExists) {
            if ((Elevator.getReedSwitch())
                    && (Elevator.getClosestSetpoint(Elevator.getEncoderValue()) == m_setpoint)) {
                return true;
            }
        }

        else {
            if (Elevator.elevatorControl.getError() <= m_maxError) {
                return true;
            }
        }
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Elevator.stopElevatorMotors();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        this.end();
    }
}
