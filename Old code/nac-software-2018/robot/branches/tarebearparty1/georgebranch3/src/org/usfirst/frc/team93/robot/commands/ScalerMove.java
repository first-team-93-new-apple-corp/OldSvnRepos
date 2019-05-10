package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Scaler;

/**
 * Sets the Scaler to go to a set location: currently programmed in is Top,
 * Bottom, and Default
 */
public class ScalerMove extends ScalerVelocity
{
	
	public ScalerMove()
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		// m_scaleLocation = scaleLocaion;
		requires(Robot.scaler);
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		Scaler.ScaleController.reset();
		// switch (m_scaleLocation)
		// {
		// case TOP:
		// Scaler.ScaleController.setSetpoint(Scaler.ScalerTop);
		// break;
		// case BOTTOM:
		// Scaler.ScaleController.setSetpoint(Scaler.ScalerBottom);
		// break;
		// default:
		// Scaler.ScaleController.setSetpoint(Scaler.ScalerDefault);
		// break;
		// }
		Scaler.ScaleController.setSetpoint(6000);
		Scaler.ScaleController.setAbsoluteTolerance(Scaler.ScalerTolerances);
		Scaler.ScaleController.enable();
		System.out.println("PID Enabled!");
		// resetEncoderVelocity();
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		// updateEncoderVelocity();
		System.out.println(Scaler.ScaleController.onTarget());
		
	}
	
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		if (Scaler.ScaleController.onTarget())
		{
			System.out.println("On Target");
		}
		return false;
		// return (((Math.abs(getVelocity()) <= Scaler.ScalerMinSpeed) &&
		// Scaler.ScaleController.onTarget())
		// || (m_scaleLocation == Scaler.ScalerLocation.TOP && Scaler.topLimit.get())
		// || (m_scaleLocation == Scaler.ScalerLocation.BOTTOM &&
		// Scaler.bottomLimit.get()));
		
	}
	
	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		Scaler.ScaleController.disable();
		Scaler.scalerMotor.set(0);
		
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		this.end();
	}
}
