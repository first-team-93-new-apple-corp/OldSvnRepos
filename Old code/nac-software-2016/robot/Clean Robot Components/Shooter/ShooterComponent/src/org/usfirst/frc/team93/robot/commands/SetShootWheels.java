
package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class sets the shooter wheels based off a speed and acceptable error.
 * 
 * @author NAC Controls
 *
 */
public class SetShootWheels extends Command
{
	double m_speed;
	double m_MaxError;
	double errorTimer;
	boolean m_willFinish;

	public SetShootWheels(double speed, double error)
	{
		m_MaxError = error;
		m_speed = speed;
		m_willFinish = true;
		requires(Robot.shooter);
	}

	public SetShootWheels(double speed, double error, boolean willFinish)
	{
		m_MaxError = error;
		m_speed = speed;
		m_willFinish = willFinish;
		requires(Robot.shooter);
	}

	@Override
	protected void initialize()
	{
		// TODO Auto-generated method stub
		RobotMap.leftFiringSpeedControl.reset();
		// RobotMap.leftFiringSpeedControl.setOutputRange(-1.0, 0.0);
		RobotMap.leftFiringSpeedControl.setOutputRange(0.0, 1.0);
		RobotMap.leftFiringSpeedControl.enable();
		RobotMap.rightFiringSpeedControl.reset();
		RobotMap.rightFiringSpeedControl.setOutputRange(0.0, 1.0);
		RobotMap.rightFiringSpeedControl.enable();
		errorTimer = 0;

		double currentErrorR = Math.abs(RobotMap.rightFiringSpeedControl.getError());
		double currentErrorL = Math.abs(RobotMap.leftFiringSpeedControl.getError());
		if (currentErrorR > m_MaxError && currentErrorL > m_MaxError)
		{
			Shooter.setStableError(false);
		}
	}

	@Override
	protected void execute()
	{
		// change back
		// RobotMap.leftFiringSpeedControl.setSetpoint(m_speed);

		RobotMap.leftFiringSpeedControl.setSetpoint(m_speed);
		RobotMap.rightFiringSpeedControl.setSetpoint(m_speed);
	}

	@Override
	protected boolean isFinished()
	{

		SmartDashboard.putBoolean("Shooter up to speed", Shooter.getStableError());

		if (Shooter.getStableError())
		{
			return m_willFinish;
		}

		double currentErrorR = Math.abs(RobotMap.rightFiringSpeedControl.getError());
		double currentErrorL = Math.abs(RobotMap.leftFiringSpeedControl.getError());
		// System.out.print("Right Error = " + currentErrorR);
		// System.out.print(" Left Error = " + currentErrorL);
		// System.out.println(" Right Sensor Reading = " +
		// RobotMap.EncoderVelocityShooterRight.pidGet());
		if (currentErrorR <= m_MaxError && currentErrorL <= m_MaxError)
		{
			errorTimer += 20;
			if (errorTimer >= 260)
			{
				Shooter.setStableError(true);
				return m_willFinish;
			}
		} else
		{
			errorTimer = 0;
			SmartDashboard.putBoolean("Shooter up to speed", false);
			Shooter.setStableError(false);
		}
		return false;
	}

	@Override
	protected void end()
	{
		RobotMap.leftFiringSpeedControl.disable();
		RobotMap.rightFiringSpeedControl.disable();
		RobotMap.RIGHT_SHOOTER.set(0.0);
		RobotMap.LEFT_SHOOTER.set(0.0);
		Shooter.setStableError(false);
		SmartDashboard.putBoolean("Shooter up to speed", Shooter.getStableError());
	}

	@Override
	protected void interrupted()
	{
		RobotMap.leftFiringSpeedControl.disable();
		RobotMap.rightFiringSpeedControl.disable();
		RobotMap.RIGHT_SHOOTER.set(0.0);
		RobotMap.LEFT_SHOOTER.set(0.0);
		if (m_willFinish)
		{
			Shooter.setStableError(false);
		}
		SmartDashboard.putBoolean("Shooter up to speed", Shooter.getStableError());
	}

}
