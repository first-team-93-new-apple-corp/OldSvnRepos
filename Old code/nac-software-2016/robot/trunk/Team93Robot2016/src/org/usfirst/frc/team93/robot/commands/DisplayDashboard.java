package org.usfirst.frc.team93.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This class displays information we need onto smart dash board
 */
public class DisplayDashboard extends Command
{
	/**
	 * @codereview ColbyMcKinley: What is the units of m_maxSpeed and how was
	 *             this number determined
	 */
	double m_maxSpeed = 100;
	double m_oldPosition = 0;

	public DisplayDashboard()
	{
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{

		// RobotMap.autonomousModeControl.addDefault("Nothing", new
		// ChooseAutonomous(ChooseAutonomous.Type.Mode, 0));
		// RobotMap.autonomousModeControl.addObject("Low Bar", new
		// ChooseAutonomous(ChooseAutonomous.Type.Mode, 1));
		// RobotMap.autonomousModeControl.addObject("Moat", new
		// ChooseAutonomous(ChooseAutonomous.Type.Mode, 2));
		// RobotMap.autonomousModeControl.addObject("Ramparts", new
		// ChooseAutonomous(ChooseAutonomous.Type.Mode, 3));
		// RobotMap.autonomousModeControl.addObject("Rock Wall", new
		// ChooseAutonomous(ChooseAutonomous.Type.Mode, 4));
		// RobotMap.autonomousModeControl.addObject("Rough Terrain", new
		// ChooseAutonomous(ChooseAutonomous.Type.Mode, 5));
		// RobotMap.autonomousBallControl.addDefault("One Ball", new
		// ChooseAutonomous(ChooseAutonomous.Type.Balls, 1));
		// RobotMap.autonomousBallControl.addObject("Two Balls", new
		// ChooseAutonomous(ChooseAutonomous.Type.Balls, 2));
		// RobotMap.autonomousDefenseControl.addDefault("Defense One",
		// new ChooseAutonomous(ChooseAutonomous.Type.Number, 1));
		// RobotMap.autonomousDefenseControl.addObject("Defense Two",
		// new ChooseAutonomous(ChooseAutonomous.Type.Number, 2));
		// RobotMap.autonomousDefenseControl.addObject("Defense Three",
		// new ChooseAutonomous(ChooseAutonomous.Type.Number, 3));
		// RobotMap.autonomousDefenseControl.addObject("Defense Four",
		// new ChooseAutonomous(ChooseAutonomous.Type.Number, 4));
		// RobotMap.autonomousDefenseControl.addObject("Defense Five",
		// new ChooseAutonomous(ChooseAutonomous.Type.Number, 5));
		// SmartDashboard.putData("Autonomous Mode",
		// RobotMap.autonomousModeControl);
		// SmartDashboard.putData("Autonomous Ball Number",
		// RobotMap.autonomousBallControl);
		// SmartDashboard.putData("Autonomous Defense Number",
		// RobotMap.autonomousDefenseControl);

	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{

		// SmartDashboard.putBoolean("Ball Acquired",
		// RobotMap.DetectBall.get());
		// SmartDashboard.putBoolean("Gear", GearshiftSub.getGear());
		// SmartDashboard.putNumber("Shooter Speed",
		// RobotMap.EncoderVelocityShooterLeft.pidGet() - m_oldPosition);
		// m_oldPosition = RobotMap.EncoderVelocityShooterLeft.pidGet();

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
	}
}
