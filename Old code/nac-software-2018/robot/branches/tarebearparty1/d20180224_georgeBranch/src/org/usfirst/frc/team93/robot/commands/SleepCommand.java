package org.usfirst.frc.team93.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * 
 * This command waits for a set amount of seconds before executing then next
 * Command.
 * 
 * @author Evans Chen
 */
public class SleepCommand extends Command
{
	
	private double m_waitTime = 0.0;
	private Timer m_timer = new Timer();
	
	public SleepCommand(double waitTime)
	{
		m_waitTime = waitTime;
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		m_timer.reset();
		m_timer.start();
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
		if (m_timer.get() >= m_waitTime)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		m_timer.stop();
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	/**
	 * @codereview josh.hawbaker 3.13.17 - Quick question - If a command doesn't
	 *             require a subsystem, is there any point in filling out the
	 *             interrupted
	 *             portion?
	 */
	{
		this.end();
	}
}