package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.utilities.EncoderPin;
import org.usfirst.frc.team93.robot.utilities.SolenoidPin;

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
		LEFT_FRONT_DRIVE_ENCODER_PIN = new EncoderPin(0, 1);
		LEFT_BACK_DRIVE_ENCODER_PIN = new EncoderPin(2, 3);
		RIGHT_FRONT_DRIVE_ENCODER_PIN = new EncoderPin(4, 5);
		RIGHT_BACK_DRIVE_ENCODER_PIN = new EncoderPin(6, 7);
		// SPI Encoders
		DRIVE_SPI_LEFT_FRONT_PORT = Port.kOnboardCS0;
		DRIVE_SPI_LEFT_BACK_PORT = Port.kOnboardCS1;
		DRIVE_SPI_RIGHT_FRONT_PORT = Port.kOnboardCS2;
		DRIVE_SPI_RIGHT_BACK_PORT = Port.kOnboardCS3;
		DRIVE_SPI_LEFT_FRONT_CHIPSELECT = 21;
		DRIVE_SPI_LEFT_BACK_CHIPSELECT = 22;
		DRIVE_SPI_RIGHT_FRONT_CHIPSELECT = 20;
		DRIVE_SPI_RIGHT_BACK_CHIPSELECT = 23;
		// NAV-X
		MXP_PORT = SPI.Port.kMXP;
		
		/// Actuators
		// Drive Speed Motors
		DRIVE_LEFT_FRONT_PIN = 3;
		DRIVE_LEFT_BACK_PIN = 1;
		DRIVE_RIGHT_FRONT_PIN = 0;
		DRIVE_RIGHT_BACK_PIN = 2;
		// Wheel Direction Motors
		DRIVE_DIRECTION_LEFT_FRONT_PIN = 0;
		DRIVE_DIRECTION_LEFT_BACK_PIN = 1;
		DRIVE_DIRECTION_RIGHT_FRONT_PIN = 2;
		DRIVE_DIRECTION_RIGHT_BACK_PIN = 3;
		
		// Gear Manipulator Motors
		// check these pins!
		GEAR_BELT_MOTOR_PIN = 6;
		GEAR_INTAKE_FRONT_MOTOR_PIN = 4;
		GEAR_INTAKE_BACK_MOTOR_PIN = 5;
		// Gear Manipulator Pneumatics
		GEAR_DEFLECTOR_SOLENOID_PIN = new SolenoidPin(6, 7);
		GEAR_FRONT_SOLENOID_PIN = new SolenoidPin(0, 1);
		GEAR_BACK_SOLENOID_PIN = new SolenoidPin(2, 3);
		// Gear Manipulator Sensors
		GEAR_BELT_REED_SWITCH_PIN = 19;
		GEAR_BELT_ENCODER_PIN = new EncoderPin(8, 9);
		// Climber pins on 13 and 18
		CLIMBER_MOTOR_A_PIN = 5;
		CLIMBER_MOTOR_B_PIN = 6;
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