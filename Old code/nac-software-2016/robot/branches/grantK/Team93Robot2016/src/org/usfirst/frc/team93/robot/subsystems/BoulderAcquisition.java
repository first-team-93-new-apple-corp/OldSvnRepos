package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class BoulderAcquisition extends Subsystem
{

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	private final double ARM_POSITION_GROUND = 10.0;

	@Override
	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		// setDefaultCommand(new RecoverBall());
	}

	public static void ExtendArm()
	{
		RobotMap.leftShooterSolenoid.set(DoubleSolenoid.Value.kForward);
		RobotMap.rightShooterSolenoid.set(DoubleSolenoid.Value.kForward);
	}

	public static void RetractArm()
	{
		RobotMap.leftShooterSolenoid.set(DoubleSolenoid.Value.kReverse);
		RobotMap.rightShooterSolenoid.set(DoubleSolenoid.Value.kReverse);
	}

}
