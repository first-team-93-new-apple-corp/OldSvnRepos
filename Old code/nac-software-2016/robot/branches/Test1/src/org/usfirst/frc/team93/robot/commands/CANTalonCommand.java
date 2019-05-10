package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class CANTalonCommand extends Command
{

	double m_speed;

	public CANTalonCommand(double speed)
	{
		m_speed = speed;
		requires(Robot.shootingWheels);
	}

	@Override
	protected void initialize()
	{
		// TODO Auto-generated method stub
		RobotMap.joshsPID.reset();
		RobotMap.joshsPID.enable();
		RobotMap.joshsPID.setOutputRange(-1.0, 0.0);
		// RobotMap.joshsPID.setIContributionRange(-0.1, 0.1);
	}

	@Override
	protected void execute()
	{
		// TODO Auto-generated method stub
		// RobotMap.TestingMotor.set(m_speed);
		// if (RobotMap.TestingMotor.getEncVelocity() != 0)
		// {
		// System.out.println(RobotMap.TestingMotor.getEncVelocity());
		// }
		System.out.println("Velocity: " + RobotMap.TestingMotor.getEncVelocity() + " P: " + RobotMap.joshsPID.getPCont()
				+ " I: " + RobotMap.joshsPID.getICont() + " D: " + RobotMap.joshsPID.getDCont() + " F: "
				+ RobotMap.joshsPID.getFCont());
		RobotMap.joshsPID.setSetpoint(m_speed);
	}

	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end()
	{
		// TODO Auto-generated method stub
		RobotMap.joshsPID.disable();
		RobotMap.TestingMotor.set(0.0);
	}

	@Override
	protected void interrupted()
	{
		// TODO Auto-generated method stub

	}

}
