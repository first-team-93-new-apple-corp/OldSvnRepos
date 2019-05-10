package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.subsystems.Scaler;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ExtendArm extends Command
{

	Joystick m_joystick;
	int m_button;
	boolean end;
	boolean m_buttonCheck;

	public ExtendArm(Joystick joystick, int button)
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);

		end = false;
		m_buttonCheck = true;

		m_joystick = joystick;
		m_button = button;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		m_buttonCheck = true;
		end = false;
		if (Scaler.getScalerOut())
		{
			end = true;
			System.out.println("Ending ExtendArm.");
		} else
		{
			Scaler.setScalerOut(true);
		}
		RobotMap.releaseScaler.set(DoubleSolenoid.Value.kReverse);
		System.out.println("Disconnecting dog gear...");
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		if (end)
		{
			return true;
		}
		if (!m_joystick.getRawButton(m_button))
		{
			m_buttonCheck = false;
		}
		if ((!m_buttonCheck) && (m_joystick.getRawButton(m_button)))
		{
			return true;
		}

		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		RobotMap.releaseScaler.set(DoubleSolenoid.Value.kForward);
		if (!end)
		{
			System.out.println("Re-attaching dog gear...");
		} else
		{
			System.out.println("ExtendArm Ended.");
		}
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
	}
}
