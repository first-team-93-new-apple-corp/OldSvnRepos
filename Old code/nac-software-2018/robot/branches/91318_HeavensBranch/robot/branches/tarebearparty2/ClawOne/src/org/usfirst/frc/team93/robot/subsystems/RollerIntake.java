package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.utilities.CANTalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class RollerIntake extends Subsystem
{
	
	public static CANTalonSRX leftIntake;
	public static CANTalonSRX rightIntake;
	public static DigitalInput cubeLimit;
	
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	@Override
	public void initDefaultCommand()
	{
		leftIntake = new CANTalonSRX(RobotMap.leftIntake);
		rightIntake = new CANTalonSRX(RobotMap.rightIntake);
		cubeLimit = new DigitalInput(RobotMap.RollerIntakeLimitSwitch);
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
