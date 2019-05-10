package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Claw;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Determines where to go using the variable Claw.clawAngleChooser
 */
public class ClawAngleContinuous extends Command
{
	
	public ClawAngleContinuous()
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.claw);
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		Claw.ClawAnglePID.reset();
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		if (Claw.clawAngleChooser == Claw.ClawAngleChooser.BOTTOM)
		{
			if (!(Claw.ClawAnglePID.getSetpoint() == Claw.BOTTOMTUNEDPOTVAL))
			{
				Claw.ClawAnglePID.setSetpoint(Claw.BOTTOMTUNEDPOTVAL);
			}
			if (!Claw.ClawAngleBottom.get())
			{
				Claw.ClawAnglePID.enable();
			}
			else
			{
				Claw.ClawAnglePID.disable();
				Claw.ClawAngleMotor.set(0);
			}
		}
		else
		{
			if (!(Claw.ClawAnglePID.getSetpoint() == Claw.TOPTUNEDPOTVAL))
			{
				Claw.ClawAnglePID.setSetpoint(Claw.TOPTUNEDPOTVAL);
			}
			if (!Claw.ClawAngleTop.get())
			{
				Claw.ClawAnglePID.enable();
			}
			else
			{
				Claw.ClawAnglePID.disable();
				Claw.ClawAngleMotor.set(0);
			}
		}
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
		this.end();
	}
}
