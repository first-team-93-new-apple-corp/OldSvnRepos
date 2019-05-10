package org.usfirst.frc.team93.robot;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;

import org.usfirst.frc.team93.robot.utilities.LightSensor;
import org.usfirst.frc.team93.robot.utilities.PIDCoordinator;
import org.usfirst.frc.team93.robot.utilities.PIDOutput2Group;
import org.usfirst.frc.team93.robot.utilities.PIDOutput3Group;
import org.usfirst.frc.team93.robot.utilities.SteeringAndSpeedCoordinator;
import org.usfirst.frc.team93.robot.utilities.Team93Potentiometer;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap 
{
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
	
	//Switches
	public static final DigitalInput[] AutoSwiches = 
		{
				new DigitalInput(14), new DigitalInput(15), new DigitalInput(16), new DigitalInput(17)};
	
	
	//Encoders
	//public static final Encoder LEFT_DRIVE_ENCODER = new Encoder (7,8,true,EncodingType.k1X );
	//public static final Encoder RIGHT_DRIVE_ENCODER = new Encoder (9,10,true,EncodingType.k1X);
	
	//Motors
	/*
	public static final Talon LEFT_DRIVE_LEFT = new Talon(1);
	public static final Talon LEFT_DRIVE_RIGHT = new Talon(2);
	public static final Talon LEFT_DRIVE_TOP = new Talon(18);
	public static final Talon RIGHT_DRIVE_LEFT = new Talon(3);
	public static final Talon RIGHT_DRIVE_RIGHT = new Talon(4);
	public static final Talon RIGHT_DRIVE_TOP = new Talon(19);
	
	public static final Talon ARM = new Talon(6);
	public static final Talon LEFT_BACQUAITION_LEFT = new Talon(7);
	public static final Talon RIGHT_BACQUAITION_RIGHT = new Talon(8);
	*/
	public static Talon ARM_SLIDER;
	
	//Potentiometer
	//public static final Team93Potentiometer Manipulator_POT = new Team93Potentiometer(11,12,13);
	
	//PID Output Groups
	public static PIDOutput3Group leftDriveGroup;
	public static PIDOutput3Group rightDriveGroup;
	public static PIDOutput2Group driveAllMotorsGroup;
	//PID Controllers
	public static PIDController driveCoordinator;
	public static PIDController driveSpeedControl;
	public static PIDController driveEncoderSteering;
	public static PIDController armSliderPID;
	
	//Steer and Speed
	public static SteeringAndSpeedCoordinator steerAndSpeed;
	
	//Encoders
	public static LightSensor ARM_SLIDER_ENCODER;
	
	//Digital Input
	//public static DigitalInput ARM_EXTEND_LIMIT_SWITCH = new DigitalInput(9);
	
    public static DigitalInput lightSensor;
	
	public RobotMap()
	{
		ARM_SLIDER = new Talon(8);	
    	lightSensor = new DigitalInput(8);
    	ARM_SLIDER_ENCODER = new LightSensor();
    	armSliderPID = new PIDController(-0.05, 0, 0, ARM_SLIDER_ENCODER, ARM_SLIDER);
	}
}