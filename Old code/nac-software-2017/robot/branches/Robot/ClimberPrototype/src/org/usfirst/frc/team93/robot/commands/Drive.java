package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.DriveSub;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Drive extends Command
{

	double m_scalingFactor;

	/**
	 * @param scalingFactor
	 *            scalingFactor is how fast we wish to speed up or slow down
	 */
	public Drive(double scalingFactor)
	{
		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveSub);
		m_scalingFactor = scalingFactor;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	/**
	 * This function reads the joystick values to determine how fast we should
	 * drive
	 */
	protected void execute()
	{
		DriveSub.setLeftMotors(m_scalingFactor * OI.getLeftValue());
		DriveSub.setRightMotors(-m_scalingFactor * OI.getRightValue());

		// System.out.println("Drive Left Encoders:"
		// + RobotMap.LEFT_MOTOR_ENCODER.getDistance());
		// System.out.println("Drive Right Encoders:"
		// + RobotMap.RIGHT_MOTOR_ENCODER.getDistance());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		DriveSub.setAllMotors(0.0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		this.end();
	}
}
