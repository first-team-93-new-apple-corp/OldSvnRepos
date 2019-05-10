package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This class shoots balls into the goal, this is a manual Shooter. This
 * commands the shooter to shoot the ball when ready, if not, sleep would run.
 * 
 * @author NAC Controls
 *
 */
public class ShootBoulder extends Command
{
	int timer;
	double m_speed;

	public ShootBoulder(double Speed)
	{
		requires(Robot.shooter);
		m_speed = Speed;
		timer = 0;
	}

	@Override
	protected void initialize()
	{
		RobotMap.LEFT_BACQUASITION.set(m_speed);
		RobotMap.RIGHT_BACQUASITION.set(-m_speed);
		RobotMap.BallGuideMotor.set(-0.5 * m_speed);
	}

	@Override
	protected void execute()
	{
		timer += 20;
	}

	@Override
	protected boolean isFinished()
	{
		return (timer >= 1500);
	}

	@Override
	protected void end()
	{
		Shooter.DoneShooting();
		timer = 0;
		Shooter.setBallPulled(false);
	}

	@Override
	protected void interrupted()
	{
		Shooter.DoneShooting();
		timer = 0;
	}

}
