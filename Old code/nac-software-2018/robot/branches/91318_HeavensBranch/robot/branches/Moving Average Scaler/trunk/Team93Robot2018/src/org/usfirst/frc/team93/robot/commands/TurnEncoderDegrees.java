package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class TurnEncoderDegrees extends PIDDriveVelocity
{
	public final double TICKSTODEGS = 4;
	double m_degs;
	double m_tolerance;
	double m_timeout = 0;
	private Timer m_timer = new Timer();
	
	public TurnEncoderDegrees(double degs, double tolerance)
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.drive);
		m_degs = degs;
		m_tolerance = tolerance * TICKSTODEGS;
		
	}
	
	public TurnEncoderDegrees(double degs, double tolerance, double timeout)
	{
		m_degs = degs;
		m_tolerance = tolerance * TICKSTODEGS;
		m_timeout = timeout;
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		Drive.RightTurn.reset();
		double rightSetpoint = Drive.RightDriveEncoder.getRaw() - (m_degs * TICKSTODEGS * -1);
		Drive.RightTurn.setSetpoint(rightSetpoint);
		Drive.RightTurn.setAbsoluteTolerance(m_tolerance);
		Drive.RightTurn.enable();
		Drive.LeftTurn.reset();
		double leftSetpoint = (m_degs * TICKSTODEGS) + Drive.LeftDriveEncoder.getRaw();
		Drive.LeftTurn.setSetpoint((leftSetpoint));
		Drive.LeftTurn.setAbsoluteTolerance(m_tolerance);
		Drive.LeftTurn.enable();
		resetEncoderVelocity();
		
		m_timer.reset();
		m_timer.start();
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		updateEncoderVelocity();
	}
	
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		return (Drive.LeftTurn.onTarget() && Drive.RightTurn.onTarget() && getLEncoderVelocity() < 0.1
				&& getREncoderVelocity() < 0.1 || (m_timeout != 0 && m_timer.get() >= m_timeout));
	}
	
	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		Drive.RightTurn.disable();
		Drive.LeftTurn.disable();
		Drive.RightTurn.reset();
		Drive.LeftTurn.reset();
		Drive.left.pidWrite(0);
		Drive.right.pidWrite(0);
		Drive.left.set(0);
		Drive.right.set(0);
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		this.end();
	}
}
