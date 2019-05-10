package org.usfirst.frc.team93.robot.commands.gearmanipulator;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearMoveDown extends Command
{
	
	public GearMoveDown()
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.gearManipulator);
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		if (!GearManipulator.checkAllClawsClosed())
		{
			GearManipulator.closeClaws();
		}
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		GearManipulator.setBeltMotor(GearManipulator.beltManualSpeed * -1);
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
		GearManipulator.setBeltMotor(0);
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		this.end();
	}
}
