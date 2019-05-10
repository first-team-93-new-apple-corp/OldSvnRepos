package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.utilities.EncoderPin;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;

public class RobotMapReal extends RobotMap
{
	/**
	 * Initialize here.
	 */
	private RobotMapReal()
	{
		///Drive Sensors
		//Encoders
		LEFT_DRIVE_ENCODER_PIN = new EncoderPin(0, 1);
		RIGHT_DRIVE_ENCODER_PIN = new EncoderPin(2, 3);
		//SPI Encoders
		DRIVE_SPI_LEFT_FRONT_PORT = Port.kOnboardCS0;
		DRIVE_SPI_LEFT_BACK_PORT = Port.kOnboardCS1;
		DRIVE_SPI_RIGHT_FRONT_PORT = Port.kOnboardCS2;
		DRIVE_SPI_RIGHT_BACK_PORT = Port.kOnboardCS3;
		//NAV-X
		MXP_PORT = SPI.Port.kMXP;
		
		///Actuators
		//Drive Speed Motors
		DRIVE_LEFT_FRONT_PIN = 8;
		DRIVE_LEFT_BACK_PIN = 13;
		DRIVE_RIGHT_FRONT_PIN = 6;
		DRIVE_RIGHT_BACK_PIN = 7;
		//Wheel Direction Motors
		DRIVE_DIRECTION_LEFT_FRONT_PIN = 19;
		DRIVE_DIRECTION_LEFT_BACK_PIN = 15;
		DRIVE_DIRECTION_RIGHT_FRONT_PIN = 16;
		DRIVE_DIRECTION_RIGHT_BACK_PIN = 0;
	}
	
	//the singleton instance of RobotMapReal.
	private static RobotMap robotMap;
	
	/**
	 * Returns the singleton instance of RobotMapReal.
	 * @return
	 */
	public static RobotMap getInstance()
	{
		if (robotMap == null)
		{
			robotMap = new RobotMapReal();
		}
		return robotMap;
	}
}