package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.commands.DriveContinuous;
import org.usfirst.frc.team93.robot.utilities.AnglePIDOutput;
import org.usfirst.frc.team93.robot.utilities.CANTalonSRX;
import org.usfirst.frc.team93.robot.utilities.NavxGyroPIDSource;
import org.usfirst.frc.team93.robot.utilities.PIDOutputGroup;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Drive extends Subsystem
{
	public static CANTalonSRX DriveFL;
	public static CANTalonSRX DriveFR;
	public static CANTalonSRX DriveBL;
	public static CANTalonSRX DriveBR;
	public static PIDOutputGroup left;
	public static PIDOutputGroup right;
	public static NavxGyroPIDSource gyro;
	public static AnglePIDOutput turn;
	public static PIDController TurnPID;
	
	public static int driveControlMode; // 1 for one controller tank, 2 for two controller tank, 3 for split axis
	
	public Drive()
	{
		
		driveControlMode = 1;
		gyro = new NavxGyroPIDSource(new AHRS(SPI.Port.kMXP));
		
		DriveFL = new CANTalonSRX(RobotMap.DriveMotorFL);
		DriveFR = new CANTalonSRX(RobotMap.DriveMotorFR);
		DriveBL = new CANTalonSRX(RobotMap.DriveMotorBL);
		DriveBR = new CANTalonSRX(RobotMap.DriveMotorBR);
		
		left = new PIDOutputGroup(DriveFL, DriveBL);
		left.setGain(-1.0);
		right = new PIDOutputGroup(DriveFR, DriveBR);
		
		turn = new AnglePIDOutput(left, right);
		
		TurnPID = new PIDController(0.02, 0.0007, 0.05, Drive.gyro, Drive.turn);
		
	}
	
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	@Override
	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new DriveContinuous());
		
	}
	
	/**
	 * To be run 1 cycle before
	 * 
	 * @return
	 */
	
}
