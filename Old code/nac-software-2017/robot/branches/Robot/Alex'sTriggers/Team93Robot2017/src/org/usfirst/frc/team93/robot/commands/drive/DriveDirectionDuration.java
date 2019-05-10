package org.usfirst.frc.team93.robot.commands.drive;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;
import org.usfirst.frc.team93.robot.utilities.crabdrive.CrabDriveCoordinator.CrabDriveMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveDirectionDuration extends Command
{
	
	private double m_direction;
	private double m_time;
	private double m_power;
	
	private Timer timer;
	
	/**
	 * Drive forward a direction for a time.
	 * 
	 * @param time
	 * @param direction
	 */
	public DriveDirectionDuration(double time, double power, double direction)
	{
		requires(Robot.drive);
		m_direction = direction;
		m_time = time;
		m_power = power;
		timer = new Timer();
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		timer.reset();
		timer.start();
		Drive.enableCrabDrive();
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		System.out.println("DOING THE DRIVE");
		Drive.CRAB_DRIVE_COORDINATOR.setMode(CrabDriveMode.FieldCentric);
		Drive.CRAB_DRIVE_COORDINATOR.speedReceiver.set(m_power);
		Drive.CRAB_DRIVE_COORDINATOR.directionReceiver.set(m_direction);
		Drive.CRAB_DRIVE_COORDINATOR.yawReceiver.set(Drive.GYRO.get());
	}
	
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		return (timer.get() >= m_time);
	}
	
	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		Drive.CRAB_DRIVE_COORDINATOR.request(0, m_direction, Drive.GYRO.get());
		Drive.disableCrabDrive();
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		this.end();
	}
}
