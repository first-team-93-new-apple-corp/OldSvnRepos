package org.usfirst.frc.team93.robot.commands.drive;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;
import org.usfirst.frc.team93.robot.utilities.crabdrive.CrabDriveCoordinator.CrabDriveMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Align mode: All wheels at 45 degree angles.
 */
public class CrabDriveDefense extends Command
{
	CrabDriveMode m_oldMode;
	double m_direction;
	
	public CrabDriveDefense()
	{
		// Use requires() here to declare subsystem dependencies
		requires(Robot.drive);
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		Drive.CRAB_DRIVE_COORDINATOR.enable();
		m_oldMode = Drive.CRAB_DRIVE_COORDINATOR.getMode();
		m_direction = OI.movementUnlimited.direction().get();
		Drive.CRAB_DRIVE_COORDINATOR.setMode(CrabDriveMode.Defense);
		Drive.CRAB_DRIVE_COORDINATOR.m_altMode = m_oldMode;
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		// use dot product to find magnitude
		double joystickdir = OI.movementUnlimited.direction().get();
		double dotproduct = (Math.cos(Math.toRadians(joystickdir)) * Math.cos(Math.toRadians(m_direction))) + (Math.sin(Math.toRadians(joystickdir)) * Math.sin(Math.toRadians(m_direction)));
		
		// joystick input to align mode.
		Drive.CRAB_DRIVE_COORDINATOR.request(OI.movementUnlimited.magnitude().get() * dotproduct, m_direction, 0.0);
	}
	
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		// stop alignment mode when button is released
		return !OI.driver.getRawButton(OI.thumb);
	}
	
	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		Drive.CRAB_DRIVE_COORDINATOR.setMode(m_oldMode);
		Drive.CRAB_DRIVE_COORDINATOR.disable();
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		this.end();
	}
}
