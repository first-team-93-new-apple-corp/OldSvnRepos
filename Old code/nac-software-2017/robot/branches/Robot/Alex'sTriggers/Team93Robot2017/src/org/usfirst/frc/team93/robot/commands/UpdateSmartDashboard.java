package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Put SmartDashboard updating logic here.
 */
public class UpdateSmartDashboard extends Command
{
	
	public UpdateSmartDashboard()
	{
		// Use requires() here to declare subsystem dependencies
		requires(Robot.smartDashboardWriter);
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		// SmartDashboard.putNumber("LEFT FRONT DRIVE ENCODER",
		// Drive.DRIVE_ENCODER_LEFT_FRONT.get());
		// SmartDashboard.putNumber("LEFT BACK DRIVE ENCODER",
		// Drive.DRIVE_ENCODER_LEFT_BACK.get());
		// SmartDashboard.putNumber("RIGHT FRONT DRIVE ENCODER",
		// Drive.DRIVE_ENCODER_RIGHT_FRONT.get());
		// SmartDashboard.putNumber("RIGHT BACK DRIVE ENCODER",
		// Drive.DRIVE_ENCODER_RIGHT_BACK.get());
		
		SmartDashboard.putNumber("LB SPI", Drive.LBSPI_dummy.get());
		
		SmartDashboard.putBoolean("TOP CLAW", GearManipulator.frontClawOpen);
		SmartDashboard.putBoolean("BOTTOM CLAW", GearManipulator.backClawOpen);
		
		SmartDashboard.putNumber("BELT ENCODER", GearManipulator.getBeltPosition());
		
		SmartDashboard.putBoolean("TOP INTAKE", GearManipulator.GEAR_INTAKE_TOP_MOTOR.get() != 0);
		SmartDashboard.putBoolean("BOTTOM INTAKE", GearManipulator.GEAR_INTAKE_BOTTOM_MOTOR.get() != 0);
		
		SmartDashboard.putBoolean("DRIVE ORIENTATION", Drive.getOrientation() > 0);
		
		// SmartDashboard.putNumber("CAMERA FORWARD",
		// VisionProcessor.forwardSource().pidGet());
		// SmartDashboard.putNumber("CAMERA LEFT RIGHT",
		// VisionProcessor.centerSource().pidGet());
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
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
	}
}