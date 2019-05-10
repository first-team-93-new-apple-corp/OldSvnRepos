package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.utilities.CANVictorSPX;
import org.usfirst.frc.team93.robot.utilities.Team93PIDController;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class SwerveControl extends Subsystem 
{

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public static final double Kp = 0.002;
	public static final double Ki = 0.0001;
	public static final double Kd = 0.00015;
	
	public static final double ticksToDegrees = 2;
	public static final double pidTolerances = 2;
	
	public static CANVictorSPX driveFLRot;
	public static CANVictorSPX driveFRRot;
	public static CANVictorSPX driveBLRot;
	public static CANVictorSPX driveBRRot;
	
	public static Encoder flRotEnc;
	public static Encoder frRotEnc;
	public static Encoder blRotEnc;
	public static Encoder brRotEnc;
	
	public static Team93PIDController flRotController;
	public static Team93PIDController frRotController;
	public static Team93PIDController blRotController;
	public static Team93PIDController brRotController;
	
	public static double flPIDTargetDeg = 0;
	public static double frPIDTargetDeg = 0;
	public static double blPIDTargetDeg = 0;
	public static double brPIDTargetDeg = 0;
	
	public static double flPIDTargetTicks = 0;
	public static double frPIDTargetTicks = 0;
	public static double blPIDTargetTicks = 0;
	public static double brPIDTargetTicks = 0;
	
	public SwerveControl() 
	{
		driveFLRot = new CANVictorSPX(RobotMap.driveFLRot);
		driveFRRot = new CANVictorSPX(RobotMap.driveFRRot);
		driveBLRot = new CANVictorSPX(RobotMap.driveBLRot);
		driveBRRot = new CANVictorSPX(RobotMap.driveBRRot);
		
		flRotEnc = new Encoder(RobotMap.flRotEncOne, RobotMap.flRotEncTwo);
		frRotEnc = new Encoder(RobotMap.frRotEncOne, RobotMap.frRotEncTwo);
		blRotEnc = new Encoder(RobotMap.blRotEncOne, RobotMap.blRotEncTwo);
		brRotEnc = new Encoder(RobotMap.brRotEncOne, RobotMap.brRotEncTwo);
		
		flRotController = new Team93PIDController(Kp, Ki, Kd, flRotEnc, driveFLRot);
		frRotController = new Team93PIDController(Kp, Ki, Kd, frRotEnc, driveFRRot);
		blRotController = new Team93PIDController(Kp, Ki, Kd, blRotEnc, driveBLRot);
		brRotController = new Team93PIDController(Kp, Ki, Kd, brRotEnc, driveBRRot);
	}
	
	
    public void initDefaultCommand() 
    {
    	
    }
}

