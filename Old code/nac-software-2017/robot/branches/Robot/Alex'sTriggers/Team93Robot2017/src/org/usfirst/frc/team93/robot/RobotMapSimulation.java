package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.utilities.EncoderPin;
import org.usfirst.frc.team93.robot.utilities.SolenoidPin;

public class RobotMapSimulation extends RobotMap
{
	/**
	 * Initialize here.
	 */
	private RobotMapSimulation()
	{
		/// Drive Sensors
		// Encoders
		LEFT_FRONT_DRIVE_ENCODER_PIN = new EncoderPin(9, 10);
		LEFT_BACK_DRIVE_ENCODER_PIN = new EncoderPin(11, 12);
		RIGHT_FRONT_DRIVE_ENCODER_PIN = new EncoderPin(7, 8);
		RIGHT_BACK_DRIVE_ENCODER_PIN = new EncoderPin(5, 6);
		// SPI Encoders
		
		/// Actuators
		// Drive Speed Motors
		DRIVE_LEFT_FRONT_PIN = 6;
		DRIVE_LEFT_BACK_PIN = 7;
		DRIVE_RIGHT_FRONT_PIN = 5;
		DRIVE_RIGHT_BACK_PIN = 8;
		// Wheel Direction Motors
		DRIVE_DIRECTION_LEFT_FRONT_PIN = 2;
		DRIVE_DIRECTION_LEFT_BACK_PIN = 3;
		DRIVE_DIRECTION_RIGHT_FRONT_PIN = 1;
		DRIVE_DIRECTION_RIGHT_BACK_PIN = 4;
		
		// Gear Manipulator Motors
		// check these pins!
		GEAR_BELT_MOTOR_PIN = 6;
		GEAR_INTAKE_FRONT_MOTOR_PIN = 4;
		GEAR_INTAKE_BACK_MOTOR_PIN = 5;
		// Gear Manipulator Pneumatics
		GEAR_DEFLECTOR_SOLENOID_PIN = new SolenoidPin(6, 7);
		GEAR_FRONT_SOLENOID_PIN = new SolenoidPin(0, 3);
		GEAR_BACK_SOLENOID_PIN = new SolenoidPin(1, 2);
		// Gear Manipulator Sensors
		GEAR_BELT_REED_SWITCH_PIN = 19;
		GEAR_BELT_ENCODER_PIN = new EncoderPin(8, 9);
		// Climber pins on 13 and 18
	}
	
	// the singleton instance of RobotMapSimulation.
	private static RobotMap robotMap;
	
	/**
	 * Returns the singleton instance of RobotMapSimulation.
	 * 
	 * @return
	 */
	public static RobotMap getInstance()
	{
		if (robotMap == null)
		{
			robotMap = new RobotMapSimulation();
		}
		return robotMap;
	}
}