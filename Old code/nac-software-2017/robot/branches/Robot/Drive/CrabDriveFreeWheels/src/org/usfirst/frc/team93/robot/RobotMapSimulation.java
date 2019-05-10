package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.utilities.EncoderPin;

public class RobotMapSimulation extends RobotMap
{
	/**
	 * Initialize here.
	 */
	
	public EncoderPin LEFT_FRONT_DIRECTION_ENCODER_PIN;
	public EncoderPin LEFT_BACK_DIRECTION_ENCODER_PIN;
	public EncoderPin RIGHT_FRONT_DIRECTION_ENCODER_PIN;
	public EncoderPin RIGHT_BACK_DIRECTION_ENCODER_PIN;
	
	private RobotMapSimulation()
	{
		/// Drive Sensors
		// Drive Encoders
		LEFT_FRONT_DRIVE_ENCODER_PIN = new EncoderPin(11, 12);
		LEFT_BACK_DRIVE_ENCODER_PIN = new EncoderPin(13, 14);
		RIGHT_FRONT_DRIVE_ENCODER_PIN = new EncoderPin(9, 10);
		RIGHT_BACK_DRIVE_ENCODER_PIN = new EncoderPin(15, 16);
		
		// Direction Encoders
		LEFT_FRONT_DIRECTION_ENCODER_PIN = new EncoderPin(3, 4);
		LEFT_BACK_DIRECTION_ENCODER_PIN = new EncoderPin(5, 6);
		RIGHT_FRONT_DIRECTION_ENCODER_PIN = new EncoderPin(1, 2);
		RIGHT_BACK_DIRECTION_ENCODER_PIN = new EncoderPin(7, 8);
		
		/// Actuators
		// Drive Speed Motors
		DRIVE_LEFT_FRONT_PIN = 2;
		DRIVE_LEFT_BACK_PIN = 3;
		DRIVE_RIGHT_FRONT_PIN = 1;
		DRIVE_RIGHT_BACK_PIN = 4;
		// Wheel Direction Motors
		DRIVE_DIRECTION_LEFT_FRONT_PIN = 6;
		DRIVE_DIRECTION_LEFT_BACK_PIN = 7;
		DRIVE_DIRECTION_RIGHT_FRONT_PIN = 5;
		DRIVE_DIRECTION_RIGHT_BACK_PIN = 8;
		// Gear Manipulator Motors
		// Give these actual values when they are built on the robot
		GEAR_GRABBER_TOP_MOTOR = 9;
		GEAR_GRABBER_BOTTOM_MOTOR = 10;
		// Gear Manipulator Pneumatics
		GEAR_TOP_SOLENOID_PIN_A = 0;
		GEAR_TOP_SOLENOID_PIN_B = 1;
		GEAR_BOTTOM_SOLENOID_PIN_A = 2;
		GEAR_BOTTOM_SOLENOID_PIN_B = 3;
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