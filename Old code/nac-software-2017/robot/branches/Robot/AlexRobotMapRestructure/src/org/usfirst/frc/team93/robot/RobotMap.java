package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.utilities.EncoderPin;

import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

/**
 * The new RobotMap
 * @author Evans Chen
 * It's time to make RobotMap great again.
 * 
 * In the past, RobotMap was used to cram in all of the robot's components.
 * We would cram literally every subsystem's Talons, PIDControllers, Encoders, limit switches, and pneumatics into RobotMap.
 * This led to an incredibly messy, hard to read RobotMap that was commonly infested with debilitating NullPointers.
 * This will no longer occur.
 * 
 * 1. This class no longer contains all of the components and their initializations.
 * The initializations are delegated to subsystems.
 * For example, all of the Drive Talons and Drive Encoders are now in the Drive subystem.
 * 
 * 2. This class only contains pins and ports.
 * This means that the subsystems use this class ONLY for pin mappings.
 * This also means that all of the fields of RobotMap are now essentially only integers and Ports.
 * 
 * 3. RobotMap.getMap() must be called to access RobotMap.
 * To get a pin, one would use something like RobotMap.getMap().DRIVE_LEFT_FRONT_PIN
 * This is because RobotMap now can have multiple versions, each defined in their own class.
 * For example, the Real Robot's pins are in RobotMapReal.
 * The robot simulation's pins are defined in RobotMapSimulation.
 * For things like a practice robot or a test platform, one can create even more versions of RobotMap.
 * 
 * How to create a new RobotMap pin mapping:
 * 0. See one of the examples in RobotMapReal or RobotMapSimulation.
 * 1. Create a new class in the same package as RobotMap.
 * 2. Make it extend from RobotMap.
 * 3. Copy all of the fields and make them final.
 * 4. Turn the class into a singleton by privatizing its constructor and adding a public static getInstance() method.
 * 5. Redefine the pin mapping fields to your desired pins.
 * 6. Add a case to RobotMap.getMap() that returns your instance.
 * 
 * NOTE: If a field is added to RobotMap, it will have to be added to ALL other child RobotMaps to avoid crashes.
 * The same goes the other way. If a field is added to a child, it MUST be added to this class to be accessed.
 */
public abstract class RobotMap {
	
	protected class Encoders{
		protected final EncoderPin LEFT_DRIVE;
		protected final EncoderPin RIGHT_DRIVE;
		protected Encoders(EncoderPin rightPin, EncoderPin leftPin){
			// Change all constructor arguments below later
			LEFT_DRIVE  = rightPin;
			RIGHT_DRIVE = leftPin;
		}
	}
	
	protected class DriveSPIEncoders{
		protected final Port LEFT_FRONT;  //Check these pins
		protected final Port LEFT_BACK;   //Check these pins
		protected final Port RIGHT_FRONT; //Check these pins
		protected final Port RIGHT_BACK;  //Check these pins
		protected DriveSPIEncoders(Port leftFrontPort, Port rightFrotnPort, Port leftBackPort, Port rightBackPort){
			LEFT_FRONT  = leftFrontPort;
			LEFT_BACK   = rightFrotnPort;
			RIGHT_FRONT = leftBackPort;
			RIGHT_BACK  = rightBackPort;
		}
	}
	
	protected class NAV_X{
		protected final Port MXP_PORT;
		protected NAV_X(Port mxpPort){
			MXP_PORT = mxpPort;
		}
	}
	
	protected class DriveSpeedMotors{
		protected final int LEFT_FRONT; //Check these pins
		protected final int LEFT_BACK; //Check these pins
		protected final int RIGHT_FRONT; //Check these pins
		protected final int RIGHT_BACK; //Check these pins
		protected DriveSpeedMotors(int leftFrontPin, int leftBackPin, int rightFrontPin, int rightBackPin){
			LEFT_FRONT  = leftFrontPin;
			LEFT_BACK   = leftBackPin;
			RIGHT_FRONT = rightFrontPin;
			RIGHT_BACK  = rightBackPin;
		}
	}
	
	protected class DriveDirectionMotors{
		protected final int LEFT_FRONT; //Check these pins
		protected final int LEFT_BACK; //Check these pins
		protected final int RIGHT_FRONT; //Check these pins
		protected final int RIGHT_BACK; //Check these pins
		protected DriveDirectionMotors(int leftFrontPin, int leftBackPin, int rightFrontPin, int rightBackPin){
			LEFT_FRONT  = leftFrontPin;
			LEFT_BACK   = leftBackPin;
			RIGHT_FRONT = rightFrontPin;
			RIGHT_BACK  = rightBackPin;
		}
	}
	
	//Drive Sensors
		//Encoders
		//SPI Encoders
		
		//NAV-X
		
		
		///Actuators
		//Drive Speed Motors
		
		//Wheel Direction Motors
		
		
		
	
	
	/**
	 * Use this to get the reference to the real RobotMap.
	 */
	public static RobotMap getMap()
	{
		if (Robot.isReal())
		{
			return RobotMapReal.getInstance();
		}
		else
		{
			return RobotMapSimulation.getInstance();
		}
	}
}