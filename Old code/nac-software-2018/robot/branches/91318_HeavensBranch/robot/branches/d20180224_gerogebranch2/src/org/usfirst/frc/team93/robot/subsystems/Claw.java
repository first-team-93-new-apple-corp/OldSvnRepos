package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.utilities.CANTalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The subsystem containing all of the components for the
 */
public class Claw extends Subsystem
{
	public final static int TOP_TUNED_ENCODER_TICKS = 1000; // Tune Tick values
	public final static int BOTTOM_TUNED_ENCODER_TICKS = 0; // Tune Tick values
	public final static double PID_TOLERANCES = 5;
	
	public static Victor leftIntake;
	public static Victor rightIntake;
	public static DigitalInput leftLimit;
	public static DigitalInput rightLimit;
	
	// public static Encoder ClawAngleEncoder;
	public static CANTalonSRX ClawAngleMotor;
	public static PIDController ClawAnglePID;
	
	public static ClawAngleChooser clawAngleChooser;
	
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public enum ClawAngleChooser
	{
		DISCRETE, CONTINUOUS
	}
	
	@Override
	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	
	public Claw()
	{
		clawAngleChooser = ClawAngleChooser.CONTINUOUS;
		leftIntake = new Victor(RobotMap.leftIntake);
		rightIntake = new Victor(RobotMap.rightIntake);
		
		leftLimit = new DigitalInput(RobotMap.LeftSwitch);
		rightLimit = new DigitalInput(RobotMap.RightSwitch);
		
		// ClawAngleEncoder = new Encoder(RobotMap.ClawAngleEncoderSourceA,
		// RobotMap.ClawAngleEncoderSourceB);
		ClawAngleMotor = new CANTalonSRX(RobotMap.TilterMotor);
		
		// ClawAnglePID = new PIDController(0.01, 0.01, 0.01, ClawAngleEncoder,
		// ClawAngleMotor);// NEEDS TO BE TUNED,
		// although so
		// do all of them...
		// ClawAnglePID.setAbsoluteTolerance(10); // tune this plz
	}
}
