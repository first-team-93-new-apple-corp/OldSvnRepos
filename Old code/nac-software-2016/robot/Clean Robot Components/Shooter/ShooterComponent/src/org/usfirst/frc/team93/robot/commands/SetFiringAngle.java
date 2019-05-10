package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This class sets the firing angle to a certain position.
 */
public class SetFiringAngle extends Command
{

	private double m_FiringAngle;
	// double m_FiringTicks;
	double m_error;
	private int time;

	public SetFiringAngle(double FiringAngle, double error)
	{
		m_error = error;
		m_FiringAngle = FiringAngle;
		requires(Robot.linearActuator);
		time = 0;
	}

	@Override
	protected void initialize()
	{
		// TODO Auto-generated method stub
		RobotMap.firingAngleControl.enable();
		RobotMap.firingAngleControl.setSetpoint(m_FiringAngle);
	}

	@Override
	protected void execute()
	{

		// TODO Auto-generated method stub
		// System.out.println("Angle: " +
		// RobotMap.linAcqPotentiometer.getAngle() + " P: "
		// + RobotMap.firingAngleControl.getPCont() + " I: " +
		// RobotMap.firingAngleControl.getICont() + " D: "
		// + RobotMap.firingAngleControl.getDCont() + " F: " +
		// RobotMap.firingAngleControl.getFCont());
	}

	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		double currentError = Math.abs(RobotMap.firingAngleControl.getError());
		// System.out.println("shooter error = " + currentError);
		if (currentError <= m_error)
		{
			time += 20;
			if (time >= 400)
			{
				return true;
			}
		} else
		{
			time = 0;
			return false;
		}
		return false;
	}

	@Override
	protected void end()
	{
		// TODO Auto-generated method stub
		RobotMap.firingAngleControl.disable();
		RobotMap.SHOOTING_ANGLE_TALON.set(0.0);
	}

	@Override
	protected void interrupted()
	{
		// TODO Auto-generated method stub
		this.end();
	}

}
