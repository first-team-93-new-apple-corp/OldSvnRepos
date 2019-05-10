package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.Robot.DriveMode;
import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.utilities.AnglePIDOutput;
import org.usfirst.frc.team93.robot.utilities.CANVictorSPX;
import org.usfirst.frc.team93.robot.utilities.NavxGyroPIDSource;
import org.usfirst.frc.team93.robot.utilities.PIDOutputGroup;
import org.usfirst.frc.team93.robot.utilities.RawPIDEncoder;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The subsystem containing all of the components for Drive
 */
public class Drive extends Subsystem
{
	// Constants
	public static final int TicksPerFoot = 185;
	public static final int AbsoluteDrivePIDTolerances = 10;
	
	public static CANVictorSPX DriveFL;
	public static CANVictorSPX DriveFR;
	public static CANVictorSPX DriveBL;
	public static CANVictorSPX DriveBR;
	public static PIDOutputGroup left;
	public static PIDOutputGroup right;
	public static NavxGyroPIDSource gyro;
	public static AnglePIDOutput turn;
	public static PIDController TurnPID;
	public static Robot.DriveMode driveControlMode;
	public static PIDController LeftWheelDrive;
	public static PIDController RightWheelDrive;
	public static Encoder LeftDriveEncoder;
	public static Encoder RightDriveEncoder;
	public static PIDOutputGroup LeftInverted;
	public static PIDOutputGroup RightInverted;
	public static RawPIDEncoder relativeLeft;
	public static RawPIDEncoder relativeRight;
	
	public Drive()
	{
		driveControlMode = DriveMode.CONTROLLER_TANK;
		gyro = new NavxGyroPIDSource(new AHRS(SPI.Port.kMXP));
		
		DriveFL = new CANVictorSPX(RobotMap.DriveMotorFL);
		DriveFR = new CANVictorSPX(RobotMap.DriveMotorFR);
		DriveBL = new CANVictorSPX(RobotMap.DriveMotorBL);
		// DriveBL.setInverted(true);
		DriveBR = new CANVictorSPX(RobotMap.DriveMotorBR);
		// DriveBR.setInverted(
		
		left = new PIDOutputGroup(DriveFL, DriveBL);
		
		right = new PIDOutputGroup(DriveFR, DriveBR);
		left.setGain(-1.0);
		right.setGain(-1.0);
		
		turn = new AnglePIDOutput(left, right);
		
		TurnPID = new PIDController(0.02, 0.0007, 0.05, gyro, turn);
		
		LeftDriveEncoder = new Encoder(RobotMap.LeftDriveEncoderA, RobotMap.LeftDriveEncoderB);
		RightDriveEncoder = new Encoder(RobotMap.RightDriveEncoderA, RobotMap.RightDriveEncoderB);
		
		LeftDriveEncoder.setDistancePerPulse(1);
		RightDriveEncoder.setDistancePerPulse(1);
		
		relativeLeft = new RawPIDEncoder(LeftDriveEncoder);
		relativeRight = new RawPIDEncoder(RightDriveEncoder);
		
		LeftInverted = new PIDOutputGroup(left);
		LeftInverted.setGain(-1);
		RightInverted = new PIDOutputGroup(right);
		RightInverted.setGain(-1);
		// P I D
		LeftWheelDrive = new PIDController(0.0005, 0, 0, relativeLeft, LeftInverted);
		
		RightWheelDrive = new PIDController(0.0005, 0, 0, relativeRight, RightInverted);
	}
	
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	@Override
	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		// setDefaultCommand(new DriveContinuous());
		
	}
	
}
