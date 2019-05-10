package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.commands.ClimberContinuous;
import org.usfirst.frc.team93.robot.utilities.PIDOutputExtended;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This class contains a command that prints and writes to SmartDashboard.
 */
public class Climber extends Subsystem
{
	
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	private static SpeedController CLIMBER_SPEEDCONTROLLER_A;
	public static PIDOutputExtended CLIMBER_MOTOR_A;
	
	private static SpeedController CLIMBER_SPEEDCONTROLLER_B;
	public static PIDOutputExtended CLIMBER_MOTOR_B;
	
	public Climber()
	{
		CLIMBER_SPEEDCONTROLLER_A = new CANTalon(RobotMap.getMap().CLIMBER_MOTOR_A_PIN);
		CLIMBER_MOTOR_A = new PIDOutputExtended(CLIMBER_SPEEDCONTROLLER_A);
		
		CLIMBER_SPEEDCONTROLLER_B = new CANTalon(RobotMap.getMap().CLIMBER_MOTOR_B_PIN);
		CLIMBER_MOTOR_B = new PIDOutputExtended(CLIMBER_SPEEDCONTROLLER_B);
		CLIMBER_MOTOR_B.setGain(-1.0);
	}
	
	@Override
	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here
		setDefaultCommand(new ClimberContinuous());
	}
}