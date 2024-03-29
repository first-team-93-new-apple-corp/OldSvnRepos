package org.usfirst.frc.team93.robot.commands.gearmanipulator;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ResetGear extends Command
{
	
	public ResetGear()
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.gearManipulator);
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
		GearManipulator.setBeltMotor(-0.5);
	}
	
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		return GearManipulator.getReedSwitch();
	}
	
	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		GearManipulator.setBeltMotor(0.0);
		GearManipulator.resetBeltPosition();
		GearManipulator.disableBeltPID();
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
	}
}
