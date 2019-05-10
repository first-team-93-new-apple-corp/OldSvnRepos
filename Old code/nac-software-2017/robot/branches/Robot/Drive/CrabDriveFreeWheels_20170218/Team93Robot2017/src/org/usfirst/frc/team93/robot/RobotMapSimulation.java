package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.utilities.EncoderPin;

public class RobotMapSimulation extends RobotMap
{
	/**
	 * Initialize here.
	 */
	private RobotMapSimulation()
	{
		///Drive Sensors
		//Encoders
		LEFT_DRIVE_ENCODER_PIN = new EncoderPin(9,10);
		RIGHT_DRIVE_ENCODER_PIN = new EncoderPin(11, 12);
		//SPI Encoders
		
		
		///Actuators
		//Drive Speed Motors
		DRIVE_LEFT_FRONT_PIN = 6;
		DRIVE_LEFT_BACK_PIN = 7;
		DRIVE_RIGHT_FRONT_PIN = 5;
		DRIVE_RIGHT_BACK_PIN = 8;
		//Wheel Direction Motors
		DRIVE_DIRECTION_LEFT_FRONT_PIN = 2;
		DRIVE_DIRECTION_LEFT_BACK_PIN = 3;
		DRIVE_DIRECTION_RIGHT_FRONT_PIN = 1;
		DRIVE_DIRECTION_RIGHT_BACK_PIN = 4;
	}
	
	//the singleton instance of RobotMapSimulation.
	private static RobotMap robotMap;
	
	/**
	 * Returns the singleton instance of RobotMapSimulation.
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