package org.usfirst.frc.team93.robot;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;

import org.usfirst.frc.team93.robot.utilities.PIDOutput2Group;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1
	public static Talon rightMotorOne;
	public static Talon rightMotorTwo;
	public static Talon leftMotorOne;
	public static Talon leftMotorTwo;
	
	public static PIDController pid1;
	
	public static PIDOutput2Group moveLeftMotors;
	public static PIDOutput2Group moveRightMotors;
	public static PIDOutput2Group moveAllMotors;
	public static DigitalInput limitSwitchOne;
	public static DigitalInput limitSwitchTwo;
	//true or falso
	public static Encoder driveAllMotorsEncoders;
	
	public static DoubleSolenoid somename;
	
	public RobotMap()
	{
		rightMotorOne = new Talon(1);
		rightMotorTwo = new Talon(2);
		leftMotorOne = new Talon(3);
		leftMotorTwo = new Talon(4);
		
		moveRightMotors = new PIDOutput2Group(rightMotorOne, rightMotorTwo);
		moveLeftMotors = new PIDOutput2Group(leftMotorOne, leftMotorTwo);
		moveAllMotors = new PIDOutput2Group(moveLeftMotors, moveRightMotors);
		driveAllMotorsEncoders = new Encoder(1,2);
		limitSwitchOne = new DigitalInput(0);
		limitSwitchTwo = new DigitalInput(5);
		pid1 = new PIDController(0.01, 0, 0, driveAllMotorsEncoders, moveAllMotors); 
		
		somename = new DoubleSolenoid(0, 1);  
		
		
		
		
		
		
		
		
	}
}