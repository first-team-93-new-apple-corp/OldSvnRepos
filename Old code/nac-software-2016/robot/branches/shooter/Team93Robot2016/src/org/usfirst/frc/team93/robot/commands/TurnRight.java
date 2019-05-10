// RobotBuilder Version: 1.5
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package  org.usfirst.frc.team93.robot.commands;


import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.subsystems.Drive;

/**
 * This command turns the robot right
 */
public class TurnRight extends Command 
{

    private double m_driveDistance;
    private double m_maxError;
    private int m_errorTimer;

    /**
     * 
     * @param driveDistance
     *            how to drive based off of encoder values
     * @param maxError
     *            Acceptable range of this goal
     */
    public TurnRight(double driveDistance, double maxError) 
    {
        // Use requires() here to declare subsystem dependencies
        m_driveDistance = driveDistance;
        m_maxError = maxError;
        m_errorTimer = 0;

        requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    @Override
    /**
     * This function drives the robot based off of the encoder value
     */
    protected void initialize() 
    {
        Drive.resetSensors();
        /*
        RobotMap.driveSpeedControl.reset();
        RobotMap.driveSpeedControl.enable();
        RobotMap.driveSpeedControl.setSetpoint(m_driveDistance);
        RobotMap.driveAllMotorsGroup.setGains(1.0, 1.0);
        */
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() 
    {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() 
    {
        // returns true when error is low enough for 0.5s
    	/*
        double currentError = RobotMap.driveSpeedControl.getError();
        if (currentError <= m_maxError) 
        {
            m_errorTimer += 20;
            if (m_errorTimer >= 500) 
            {
                return true;
            }
        }
        else {
            m_errorTimer = 0;
        }
        */
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() 
    {
        //RobotMap.driveAllMotorsGroup.setGains(-1.0, 1.0);
        Drive.setAllMotors(0.0);
        //RobotMap.driveSpeedControl.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() 
    {
        this.end();
    }
}
