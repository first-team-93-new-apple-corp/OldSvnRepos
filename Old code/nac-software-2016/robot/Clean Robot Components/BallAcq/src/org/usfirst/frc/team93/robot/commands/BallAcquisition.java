package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class BallAcquisition extends Command{
	
	private double m_speed;

	public BallAcquisition(double speed)
	{
		//We are getting the value from OI to set the motors to
		m_speed = speed;
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() {
		//We are setting all of the motors to the value passed in from OI
		RobotMap.LEFT_BALL_ACQ.set(m_speed);
		RobotMap.RIGHT_BALL_ACQ.set(-m_speed);
		RobotMap.BALL_ACQ_BELT.set(-m_speed);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return !RobotMap.BALL_CHECK.get();
	}

	@Override
	protected void end() {
		//When we are done acquiring a ball, we turn off all of the motors
		RobotMap.LEFT_BALL_ACQ.set(0.0);
		RobotMap.RIGHT_BALL_ACQ.set(0.0);
		RobotMap.BALL_ACQ_BELT.set(0.0);
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		this.end();
	}

}
