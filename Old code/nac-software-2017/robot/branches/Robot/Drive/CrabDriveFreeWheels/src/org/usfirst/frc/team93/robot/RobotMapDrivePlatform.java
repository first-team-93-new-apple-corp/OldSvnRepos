package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.utilities.EncoderPin;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;

public class RobotMapDrivePlatform extends RobotMap
{
	/**
	 * Initialize here.
	 */
	private RobotMapDrivePlatform()
	{
		/// Drive Sensors
		// Encoders
		LEFT_FRONT_DRIVE_ENCODER_PIN = new EncoderPin(2, 3);
		LEFT_BACK_DRIVE_ENCODER_PIN = new EncoderPin(4, 5);
		RIGHT_FRONT_DRIVE_ENCODER_PIN = new EncoderPin(6, 7);
		RIGHT_BACK_DRIVE_ENCODER_PIN = new EncoderPin(8, 9);
		// SPI Encoders
		DRIVE_SPI_LEFT_FRONT_PORT = Port.kOnboardCS0;
		DRIVE_SPI_LEFT_BACK_PORT = Port.kOnboardCS1;
		DRIVE_SPI_RIGHT_FRONT_PORT = Port.kOnboardCS2;
		DRIVE_SPI_RIGHT_BACK_PORT = Port.kOnboardCS3;
		DRIVE_SPI_LEFT_FRONT_CHIPSELECT = 12;
		DRIVE_SPI_LEFT_BACK_CHIPSELECT = 10;
		DRIVE_SPI_RIGHT_FRONT_CHIPSELECT = 13;
		DRIVE_SPI_RIGHT_BACK_CHIPSELECT = 11;
		// NAV-X
		MXP_PORT = SPI.Port.kMXP;
		
		/// Actuators
		// Drive Speed Motors
		DRIVE_LEFT_FRONT_PIN = 8;
		DRIVE_LEFT_BACK_PIN = 13;
		DRIVE_RIGHT_FRONT_PIN = 6;
		DRIVE_RIGHT_BACK_PIN = 7;
		// Wheel Direction Motors
		DRIVE_DIRECTION_LEFT_FRONT_PIN = 19;
		DRIVE_DIRECTION_LEFT_BACK_PIN = 15;
		DRIVE_DIRECTION_RIGHT_FRONT_PIN = 16;
		DRIVE_DIRECTION_RIGHT_BACK_PIN = 9;
		// Gear Manipulator Motors
		// Give these actual values when they are built on the robot
		GEAR_GRABBER_TOP_MOTOR = 0;
		GEAR_GRABBER_BOTTOM_MOTOR = 1;
		// Gear Manipulator Pneumatics
		GEAR_TOP_SOLENOID_PIN_A = 0;
		GEAR_TOP_SOLENOID_PIN_B = 1;
		GEAR_BOTTOM_SOLENOID_PIN_A = 2;
		GEAR_BOTTOM_SOLENOID_PIN_B = 3;
	}
	
	// the singleton instance of RobotMapReal.
	private static RobotMap robotMap;
	
	/**
	 * Returns the singleton instance of RobotMapReal.
	 * 
	 * @return
	 */
	public static RobotMap getInstance()
	{
		if (robotMap == null)
		{
			robotMap = new RobotMapDrivePlatform();
		}
		return robotMap;
	}
}