package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.utilities.EncoderPin;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;

public class RobotMapReal extends RobotMap
{
	///Drive Sensors
	//Encoders
	public final EncoderPin LEFT_DRIVE_ENCODER_PIN = new EncoderPin(0, 1);
	public final EncoderPin RIGHT_DRIVE_ENCODER_PIN = new EncoderPin(2, 3);
	//SPI Encoders
	public final Port DRIVE_SPI_LEFT_FRONT_PORT = Port.kOnboardCS0; //Check these pins
	public final Port DRIVE_SPI_LEFT_BACK_PORT = Port.kOnboardCS1; //Check these pins
	public final Port DRIVE_SPI_RIGHT_FRONT_PORT = Port.kOnboardCS2; //Check these pins
	public final Port DRIVE_SPI_RIGHT_BACK_PORT = Port.kOnboardCS3; //Check these pins
	//NAV-X
	public final Port MXP_PORT = SPI.Port.kMXP;
	
	///Actuators
	//Drive Speed Motors
	public final int DRIVE_LEFT_FRONT_PIN = 0; //Check these pins
	public final int DRIVE_LEFT_BACK_PIN = 1; //Check these pins
	public final int DRIVE_RIGHT_FRONT_PIN = 2; //Check these pins
	public final int DRIVE_RIGHT_BACK_PIN = 3; //Check these pins
	//Wheel Direction Motors
	public final int DRIVE_DIRECTION_LEFT_FRONT_PIN = 4; //Check these pins
	public final int DRIVE_DIRECTION_LEFT_BACK_PIN = 5; //Check these pins
	public final int DRIVE_DIRECTION_RIGHT_FRONT_PIN = 6; //Check these pins
	public final int DRIVE_DIRECTION_RIGHT_BACK_PIN = 7; //Check these pins
	
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
	
	/**
	 * Private constructor for singleton
	 */
	private RobotMapReal()
	{
	}
}