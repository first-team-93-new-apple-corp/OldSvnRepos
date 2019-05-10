package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Scaler;
import org.usfirst.frc.team93.robot.utilities.DriveChooser;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Controls the Scaler continuously with a joystick
 */
public class ScalerContinuous extends Command
{
	double m_motorMinVal;
	double m_PercentDecrease;
	
	public ScalerContinuous(double motorMinVal, double percentDecrease)
	{
		
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.scaler);
		m_motorMinVal = motorMinVal;
		m_PercentDecrease = percentDecrease;
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
		if (OI.operator.getPOV() == -1)
		{
			double motorMultipler;
			double JoyInput = (DriveChooser.deadzone(OI.operator.getRawAxis(1), OI.ControllerDeadzone));
			if (JoyInput > 0)
			{
				motorMultipler = Scaler.upwardsMultiplier(Scaler.GetLocationPercentage(), 100 - m_PercentDecrease,
						m_motorMinVal);
			}
			else if (JoyInput < 0)
			{
				motorMultipler = Scaler.downwardsMultiplier(Scaler.GetLocationPercentage(), m_PercentDecrease,
						m_motorMinVal);
			}
			else
			{
				motorMultipler = 0;
			}
			if (JoyInput > 0 && Scaler.topLimit.get())
			{
				Scaler.scalerMotor.set(0);
				Scaler.topEncoderTick = Scaler.ScalerEncoder.getRaw();
			}
			else if (JoyInput < 0 && Scaler.bottomLimit.get())
			{
				Scaler.scalerMotor.set(0);
				Scaler.bottomEncoderTick = Scaler.ScalerEncoder.getRaw();
			}
			else
			{
				Scaler.scalerMotor.set(motorMultipler * JoyInput);
			}
		}
		else
		{
			Scaler.scalerMotor.set(m_motorMinVal);
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
