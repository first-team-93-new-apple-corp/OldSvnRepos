package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.utilities.CANTalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Tilter extends Subsystem
{
	
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public static CANTalonSRX tilter;
	public static Encoder tilterEncoder;
	public static PIDController tilterController;
	
	public Tilter()
	{
		tilter = new CANTalonSRX(RobotMap.TilterMotor);
		tilterEncoder = new Encoder(RobotMap.TilterEncoderA, RobotMap.TilterEncoderB);
		tilterController = new PIDController(0.001, 0, 0, tilterEncoder, tilter);
		tilterEncoder.reset();
	}
	
	@Override
	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
