package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.utilities.CANVictorSPX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The subsystem containing all of the components for the
 */
public class Claw extends Subsystem
{
	public final static double TOPTUNEDPOTVAL = 1;
	public final static double BOTTOMTUNEDPOTVAL = 0;
	
	public static CANVictorSPX leftIntake;
	public static CANVictorSPX rightIntake;
	public static DigitalInput leftLimit;
	public static DigitalInput RightLimit;
	
	public static AnalogPotentiometer ClawAngle;
	public static DigitalInput ClawAngleTop;
	public static DigitalInput ClawAngleBottom;
	public static CANVictorSPX ClawAngleMotor;
	public static PIDController ClawAnglePID;
	
	public static ClawAngleChooser clawAngleChooser;
	
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public enum ClawAngleChooser
	{
		TOP, BOTTOM
	}
	
	@Override
	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	
	public Claw()
	{
		clawAngleChooser = ClawAngleChooser.BOTTOM;
		leftIntake = new CANVictorSPX(RobotMap.leftIntake);
		rightIntake = new CANVictorSPX(RobotMap.rightIntake);
		
		leftLimit = new DigitalInput(RobotMap.LeftSwitch);
		RightLimit = new DigitalInput(RobotMap.RightSwitch);
		
		ClawAngle = new AnalogPotentiometer(RobotMap.ClawAnglePot);
		ClawAngleTop = new DigitalInput(RobotMap.ClawAngleTopLimit);
		ClawAngleBottom = new DigitalInput(RobotMap.ClawAngleBottomLimit);
		ClawAngleMotor = new CANVictorSPX(RobotMap.ClawAngleMotor);
		
		ClawAnglePID = new PIDController(0.01, 0.01, 0.01, ClawAngle, ClawAngleMotor);// NEEDS TO BE TUNED, although so
																						// do all of them...
	}
}
