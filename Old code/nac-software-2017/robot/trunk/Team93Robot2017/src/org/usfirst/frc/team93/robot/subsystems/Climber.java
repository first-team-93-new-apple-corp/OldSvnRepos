package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.commands.ClimberContinuous;
import org.usfirst.frc.team93.robot.utilities.PIDOutputExtended;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This class contains all the components necessary for the climber to operate
 */
public class Climber extends Subsystem
{
	/**
	 * @codereview josh.hawbaker 3.10.17 Changed javadoc from "This class
	 * contains a command that prints and writs to SmartDashboard." Don't copy
	 * paste.
	 */
	
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	private static SpeedController CLIMBER_SPEEDCONTROLLER_A;
	public static PIDOutputExtended CLIMBER_MOTOR_A;
	
	private static SpeedController CLIMBER_SPEEDCONTROLLER_B;
	public static PIDOutputExtended CLIMBER_MOTOR_B;
	
	private static SpeedController CLIMBER_SPEEDCONTROLLER_C;
	public static PIDOutputExtended CLIMBER_MOTOR_C;
	
	public Climber()
	{
		CLIMBER_SPEEDCONTROLLER_A = new CANTalon(RobotMap.getMap().CLIMBER_MOTOR_A_PIN);
		CLIMBER_MOTOR_A = new PIDOutputExtended(CLIMBER_SPEEDCONTROLLER_A);
		
		CLIMBER_SPEEDCONTROLLER_B = new CANTalon(RobotMap.getMap().CLIMBER_MOTOR_B_PIN);
		CLIMBER_MOTOR_B = new PIDOutputExtended(CLIMBER_SPEEDCONTROLLER_B);
		CLIMBER_MOTOR_B.setGain(-1.0);
		
		CLIMBER_SPEEDCONTROLLER_C = new Victor(RobotMap.getMap().CLIMBER_MOTOR_C_PIN);
		CLIMBER_MOTOR_C = new PIDOutputExtended(CLIMBER_SPEEDCONTROLLER_C);
		CLIMBER_MOTOR_C.setGain(-1.0);
		
	}
	
	@Override
	public void initDefaultCommand()
	{
		// we call the ClimberContinuous command to run. It happens to be the
		// only command for this subsystem and it needs to run for all of teleop
		setDefaultCommand(new ClimberContinuous());
	}
}