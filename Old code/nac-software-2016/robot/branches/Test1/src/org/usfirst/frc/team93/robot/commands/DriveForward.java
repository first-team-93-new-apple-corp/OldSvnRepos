package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveForward extends Command
{

	private double m_driveDistance;
	private double m_maxError;
	private int m_errorTimer;

	private static Timer totalTime;

	/**
	 * 
	 * @param driveDistance
	 *            How far to drive
	 * @param maxError
	 *            Acceptable range of this position
	 */
	public DriveForward(double driveDistance, double maxError)
	{

		m_driveDistance = driveDistance;
		m_maxError = maxError;
		// Use requires() here to declare subsystem dependencies

		totalTime = new Timer();
	}

	// Called just before this Command runs the first time
	@Override
	/**
	 * This function sets the motors up to run to a certain distance
	 */
	protected void initialize()
	{
		RobotMap.joshsPID.reset();
		RobotMap.joshsPID.enable();
		RobotMap.joshsPID.setSetpoint(m_driveDistance);
		RobotMap.joshsPID.setOutputRange(-0.4, 0.4);

		totalTime.start();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		System.out.println(RobotMap.joshsPID.getError());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		// returns true when error is low enough for 0.5s
		double currentError = Math.abs(RobotMap.joshsPID.getError());
		System.out.println("Drive Error = " + currentError);
		if (currentError <= m_maxError)
		{
			m_errorTimer += 20;
			if (m_errorTimer >= 500)
			{
				return true;
			}
		} else
		{
			m_errorTimer = 0;
		}
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		RobotMap.poorMansEncoder.reset();
		RobotMap.joshsPID.disable();
		RobotMap.PIDMotor.set(0.0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		this.end();
	}
}
