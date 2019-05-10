package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Scaler;
import org.usfirst.frc.team93.robot.utilities.TeleopScaler;

/**
 * Sets the Scaler to go to a set location: currently programmed in is Top,
 * Bottom, and Default
 */
public class ScalerMove extends ScalerVelocity
{
	Scaler.ScalerLocation m_scaleLocation;
	int Setpoint;
	boolean isUp;
	public static TeleopScaler teleopControl;
	
	public ScalerMove(Scaler.ScalerLocation scaleLocation)
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		m_scaleLocation = scaleLocation;
		requires(Robot.scaler);
		isUp = false;
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		Scaler.ScaleControllerUp.setAbsoluteTolerance(Scaler.ScalerTolerances);
		Scaler.ScaleControllerDown.setAbsoluteTolerance(Scaler.ScalerTolerances);
		
		Scaler.ScaleControllerUp.reset();
		switch (m_scaleLocation)
		{
			case TOP:
				Setpoint = (int) (Scaler.ScalerTop);
				break;
			case BOTTOM:
				Setpoint = (int) (Scaler.ScalerBottom);
				break;
			case MIDDLE:
				Setpoint = (int) (Scaler.ScalerSwitch);
				break;
		}
		if (Setpoint > Scaler.ScalerEncoder.getRaw())
		{
			Scaler.ScaleControllerUp.setSetpoint(Setpoint);
			Scaler.ScaleControllerUp.enable();
			// System.out.println("Goin' up!");
			isUp = true;
		}
		else
		{
			Scaler.ScaleControllerDown.setSetpoint(Setpoint);
			Scaler.ScaleControllerDown.enable();
			//// System.out.println("Goin' down!");
			isUp = false;
		}
		
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		// updateEncoderVelocity();
		// System.out.println(Scaler.ScaleControllerUp.onTarget());
		if (Scaler.ScaleControllerDown.onTarget() && (m_scaleLocation == Scaler.ScalerLocation.BOTTOM))
		{
			
		}
	}
	
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		// if (Scaler.ScaleControllerUp.onTarget())
		// {
		// System.out.println("On Target");
		// }
		// /System.out.println(Scaler.ScaleControllerDown.onTarget() ||
		// Scaler.ScaleControllerUp.onTarget());
		return (Scaler.ScaleControllerDown.onTarget() && !isUp) || (Scaler.ScaleControllerUp.onTarget() && isUp);
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
		Scaler.ScaleControllerUp.disable();
		Scaler.ScaleControllerDown.disable();
		Scaler.offsetMotorOutput.pidWrite(0);
		// System.out.println("Ended");
		
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		this.end();
	}
}
