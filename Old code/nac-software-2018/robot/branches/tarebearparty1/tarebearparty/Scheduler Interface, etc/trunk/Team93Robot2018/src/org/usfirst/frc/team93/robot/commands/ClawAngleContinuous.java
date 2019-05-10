package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Claw;
import org.usfirst.frc.team93.robot.utilities.DriveChooser;

import edu.wpi.first.wpilibj.GenericHID.Hand;
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
		double yPosition = DriveChooser.deadzone(OI.operator.getY(Hand.kRight), OI.ControllerDeadzone);
		yPosition = DriveChooser.getModifiedAxis(yPosition);
		
		if (Claw.clawAngleChooser == Claw.ClawAngleChooser.CONTINUOUS)
		{
			Claw.ClawAnglePID.disable();
			// if (Claw.ClawAngleEncoder.getDistance() <= Claw.BOTTOM_TUNED_ENCODER_TICKS &&
			// yPosition < 0)
			// {
			// yPosition = 0;
			// }
			// else if (Claw.ClawAngleEncoder.getDistance() >= Claw.TOP_TUNED_ENCODER_TICKS
			// && yPosition > 0)
			// {
			// yPosition = 0;
			// }
			
			Claw.ClawAngleMotor.set(yPosition);
		}
		else
		{
			if (yPosition > 0)
			{
				Claw.ClawAnglePID.setSetpoint(Claw.TOP_TUNED_ENCODER_TICKS);
				Claw.ClawAnglePID.enable();
			}
			else if (yPosition < 0)
			{
				Claw.ClawAnglePID.setSetpoint(Claw.BOTTOM_TUNED_ENCODER_TICKS);
				Claw.ClawAnglePID.enable();
			}
			else
			{
				if (Claw.ClawAnglePID.onTarget())
				{
					Claw.ClawAnglePID.disable();
					Claw.ClawAngleMotor.set(0);
				}
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
