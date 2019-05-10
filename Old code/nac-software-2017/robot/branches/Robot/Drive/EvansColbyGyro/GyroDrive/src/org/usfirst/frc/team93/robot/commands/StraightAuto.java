package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
/**
 *
 */
public class StraightAuto extends Command {
	
	//setpoint to drive to
	private double m_driveDistance = 0;
	
	//heading setpoint 
	private double m_headingSetpoint = 0;
	
	//error tolerance
	private double m_maxError = 0;
	
	//Timer
	private Timer time;
	
	//The conversion from microseconds to seconds.
	final double microsecond = 1000000.0;
	
    public StraightAuto(double driveDistance, double maxError) {
    	
    	//setpoint to drive to
    	m_driveDistance = driveDistance;
    	
    	//starting heading postion
    	m_headingSetpoint = 0;
    	//error tolerance
    	m_maxError = maxError;
    	
    	//timer
    	time = new Timer();
    	
    	//requires drive
    	requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	//resets sensors.
    	Drive.resetSensors();
    	
    	//Resets the timer.
    	time.reset();
    	time.start();
    	
    	//Resets sensors.
    	Drive.resetSensors();
    	
    	//Resets PIDControllers.
    	RobotMap.DRIVE_CONTROL_SPEED.reset();
    	RobotMap.DRIVE_CONTROL_STEERING.reset();
    	
    	//RobotMap.DRIVE_CONTROL_STEERING.setPID(0.01, 0, 0);
    	RobotMap.DRIVE_CONTROL_SPEED.setOutputRange(0, 0.25);
    	//Enables PIDControllers.
    	RobotMap.DRIVE_CONTROL_SPEED.enable();
    	RobotMap.DRIVE_CONTROL_STEERING.enable();
    	
    	RobotMap.GYRO.reset();
    	
    	//should drive straight and go to drive setpoint
    	RobotMap.DRIVE_CONTROL_STEERING.setSetpoint(m_headingSetpoint);
    	RobotMap.DRIVE_CONTROL_SPEED.setSetpoint(m_driveDistance);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
    	//get error
    	double currentError = Math.abs(RobotMap.DRIVE_CONTROL_SPEED.getError());
    	
    	if (currentError <= m_maxError)
		{
    		//end command only if command has been within error tolerance for over one second
    		if (time.get() > microsecond * 1)
    		{
    			return true;
    		}
		}
    	//if error is not within tolerance, keep running.
    	else
		{
			time.reset();
		}
		return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    	//disables PIDs
    	RobotMap.DRIVE_CONTROL_SPEED.disable();
		RobotMap.DRIVE_CONTROL_STEERING.disable();
		
		//sets drive motors to 0.0
		Drive.setAllMotors(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
}
