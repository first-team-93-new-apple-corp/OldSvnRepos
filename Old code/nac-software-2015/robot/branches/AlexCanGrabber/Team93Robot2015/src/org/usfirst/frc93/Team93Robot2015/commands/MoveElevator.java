package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc93.Team93Robot2015.Robot;
import org.usfirst.frc93.Team93Robot2015.RobotMap;
import org.usfirst.frc93.Team93Robot2015.subsystems.Elevator;

/**
 *
 */
public class MoveElevator extends Command {

    double m_setpoint;
    double m_maxError;

    public MoveElevator(double setpoint, double maxError) {
        m_setpoint = setpoint;
        m_maxError = maxError;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.elevator);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        RobotMap.elevatorEncoder.reset();
        RobotMap.elevatorControl.enable();
        RobotMap.elevatorControl.setSetpoint(m_setpoint);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        if (RobotMap.elevatorControl.getError() <= m_maxError) {
            return true;
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
    }
}
