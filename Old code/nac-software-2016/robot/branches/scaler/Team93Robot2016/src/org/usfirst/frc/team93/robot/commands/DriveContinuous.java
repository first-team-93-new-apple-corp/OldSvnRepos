package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Figures out how fast we should drive.
 */
public class DriveContinuous extends Command
{

	double m_scalingFactor;

	/**
	 * @param scalingFactor
	 *            scalingFactor is how fast we wish to speed up or slow down
	 */
	public DriveContinuous(double scalingFactor)
	{
		// Use requires() here to declare subsystem dependencies
		requires(Robot.drive);
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
		// Drive.setLeftMotors(m_scalingFactor
		// * OI.getDriverLY(0.05 * m_scalingFactor));
		// Drive.setRightMotors(-m_scalingFactor
		// * OI.getDriverRY(0.05 * m_scalingFactor));

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
		Drive.setAllMotors(0.0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		this.end();
	}
}
