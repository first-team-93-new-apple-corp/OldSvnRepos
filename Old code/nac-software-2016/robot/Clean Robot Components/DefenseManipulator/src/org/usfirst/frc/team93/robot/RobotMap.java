package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.utilities.*;

import edu.wpi.first.wpilibj.CANTalon;

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
	public static LinAcqLimitEnforcer manipulator;
	public static CANTalon DefenseArm;
	public static Team93Potentiometer manipulatorPotentiometer;
	
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
	public RobotMap(){
		manipulatorPotentiometer = new Team93Potentiometer(1, 1.0, 0.0);
		DefenseArm = new CANTalon(2);
		manipulator = new LinAcqLimitEnforcer(DefenseArm, manipulatorPotentiometer, 1.96, 2.17, true);
		}
}
