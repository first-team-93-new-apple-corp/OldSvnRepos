package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.utilities.EncoderPin;
import org.usfirst.frc.team93.robot.utilities.SolenoidPin;

import edu.wpi.first.wpilibj.SPI.Port;

/**
 * The new RobotMap
 * 
 * @author Evans Chen It's time to make RobotMap great again.
 * 
 * In the past, RobotMap was used to cram in all of the robot's components. We
 * would cram literally every subsystem's Talons, PIDControllers, Encoders,
 * limit switches, and pneumatics into RobotMap. This led to an incredibly
 * messy, hard to read RobotMap that was commonly infested with debilitating
 * NullPointers. This will no longer occur.
 * 
 * 1. This class no longer contains all of the components and their
 * initializations. The initializations are delegated to subsystems. For
 * example, all of the Drive Talons and Drive Encoders are now in the Drive
 * subystem.
 * 
 * 2. This class only contains pins and ports. This means that the subsystems
 * use this class ONLY for pin mappings. This also means that all of the fields
 * of RobotMap are now essentially only integers and Ports.
 * 
 * 3. RobotMap.getMap() must be called to access RobotMap. To get a pin, one
 * would use something like RobotMap.getMap().DRIVE_LEFT_FRONT_PIN This is
 * because RobotMap now can have multiple versions, each defined in their own
 * class. For example, the Real Robot's pins are in RobotMapReal. The robot
 * simulation's pins are defined in RobotMapSimulation. For things like a
 * practice robot or a test platform, one can create even more versions of
 * RobotMap.
 * 
 * How to create a new RobotMap pin mapping: 0. See one of the examples in
 * RobotMapReal or RobotMapSimulation. 1. Create a new class in the same package
 * as RobotMap. 2. Make it extend from RobotMap. 3. Copy all of the fields and
 * make them final. 4. Turn the class into a singleton by privatizing its
 * constructor and adding a public static getInstance() method. 5. Redefine the
 * pin mapping fields to your desired pins. 6. Add a case to RobotMap.getMap()
 * that returns your instance.
 * 
 * NOTE: If a field is added to RobotMap, it will have to be added to ALL other
 * child RobotMaps to avoid crashes. The same goes the other way. If a field is
 * added to a child, it MUST be added to this class to be accessed.
 */
public abstract class RobotMap
{
	
	public enum Platform
	{
		PRACTICE, COMPETITION;
	}
	
	// which Robot this is
	public static Platform platform = Platform.COMPETITION;
	
	/// Drive Sensors
	// Encoders
	public EncoderPin LEFT_FRONT_DRIVE_ENCODER_PIN;
	public EncoderPin LEFT_BACK_DRIVE_ENCODER_PIN;
	public EncoderPin RIGHT_FRONT_DRIVE_ENCODER_PIN;
	public EncoderPin RIGHT_BACK_DRIVE_ENCODER_PIN;
	// SPI Encoders
	public Port DRIVE_SPI_LEFT_FRONT_PORT; // Check these pins
	public int DRIVE_SPI_LEFT_FRONT_CHIPSELECT;
	public Port DRIVE_SPI_LEFT_BACK_PORT; // Check these pins
	public int DRIVE_SPI_LEFT_BACK_CHIPSELECT;
	public Port DRIVE_SPI_RIGHT_FRONT_PORT; // Check these pins
	public int DRIVE_SPI_RIGHT_FRONT_CHIPSELECT;
	public Port DRIVE_SPI_RIGHT_BACK_PORT; // Check these pins
	public int DRIVE_SPI_RIGHT_BACK_CHIPSELECT;
	// NAV-X
	public Port MXP_PORT;
	
	/// Actuators
	// Drive Speed Motors
	public int DRIVE_LEFT_FRONT_PIN; // Check these pins
	public int DRIVE_LEFT_BACK_PIN; // Check these pins
	public int DRIVE_RIGHT_FRONT_PIN; // Check these pins
	public int DRIVE_RIGHT_BACK_PIN; // Check these pins
	// Wheel Direction Motors
	public int DRIVE_DIRECTION_LEFT_FRONT_PIN; // Check these pins
	public int DRIVE_DIRECTION_LEFT_BACK_PIN; // Check these pins
	public int DRIVE_DIRECTION_RIGHT_FRONT_PIN; // Check these pins
	public int DRIVE_DIRECTION_RIGHT_BACK_PIN; // Check these pins
	
	// Gear Manipulator Motors
	public int GEAR_BELT_MOTOR_PIN;
	public int GEAR_INTAKE_FRONT_MOTOR_PIN;
	public int GEAR_INTAKE_BACK_MOTOR_PIN;
	// Gear Manipulator Pneumatics
	public SolenoidPin GEAR_DEFLECTOR_SOLENOID_PIN;
	public SolenoidPin GEAR_FRONT_SOLENOID_PIN;
	public SolenoidPin GEAR_BACK_SOLENOID_PIN;
	// Gear Manipulator Sensors
	public int GEAR_BELT_REED_SWITCH_PIN;
	public EncoderPin GEAR_BELT_ENCODER_PIN;
	
	// Climber
	public int CLIMBER_MOTOR_A_PIN;
	public int CLIMBER_MOTOR_B_PIN;
	public int CLIMBER_MOTOR_C_PIN;
	
	/**
	 * Use this to get the reference to the real RobotMap.
	 */
	public static RobotMap getMap()
	{
		if (Robot.isReal())
		{
			switch (platform)
			{
			case COMPETITION:
				return RobotMapReal.getInstance();
			case PRACTICE:
				return RobotMapDrivePlatform.getInstance();
			}
			return RobotMapReal.getInstance();
		}
		else
		{
			return RobotMapSimulation.getInstance();
		}
	}
}