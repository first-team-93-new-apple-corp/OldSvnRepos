package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.LeftRamp;
import org.usfirst.frc.team93.robot.utilities.DriveChooser;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LeftRampContinuous extends Command
{
	
	public LeftRampContinuous()
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.leftRamp);
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
		// LeftRamp.leftRampMotor.set(OI.operator.getRawAxis(3));
		// LeftRamp.leftRampMotor.set(DriveChooser.getDriverR());
		LeftRamp.leftRampMotor.set(DriveChooser.deadzone(OI.operator.getRawAxis(3), 0.1));
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
		LeftRamp.leftRampMotor.set(0);
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		this.end();
	}
}
