package org.usfirst.frc.team93.robot.commands.drive;

import org.usfirst.frc.team93.robot.Robot;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * TestMotor
 * 
 * @author Evans Chen
 * 
 * This sets the motor in a given direction for a given amount of time.
 */
public class TestMotor extends Command
{
	// the speed controller reference
	PIDOutput m_output;
	
	// motor set value
	double m_value = 1.0;
	
	// amount of time to run motor, in sec
	double m_time = 2;
	
	// The timer instance.
	private Timer m_timer;
	
	/**
	 * Constructor for TestMotor command.
	 * 
	 * @param motor The motor to set.
	 * @param value The value to set it to.
	 * @param time The amount of time to run the motor.
	 */
	public TestMotor(PIDOutput motor, double value, double time)
	{
		requires(Robot.drive);
		m_timer = new Timer();
		m_time = time;
		m_output = motor;
		m_value = value;
	}
	
	/**
	 * Default constructor for TestMotor command.
	 * 
	 * @param motor The motor to set.
	 * @param value The value to set it to.
	 */
	public TestMotor(PIDOutput motor, double value)
	{
		this(motor, value, 2000);
	}
	
	@Override
	protected void initialize()
	{
		m_timer.reset();
		m_timer.start();
	}
	
	@Override
	protected void execute()
	{
		// sets the motor for a while
		m_output.pidWrite(m_value);
	}
	
	@Override
	protected boolean isFinished()
	{
		return (m_timer.get() >= m_time);
	}
	
	@Override
	protected void end()
	{
		m_output.pidWrite(0.0);
	}
	
	@Override
	protected void interrupted()
	{
		this.end();
	}
}