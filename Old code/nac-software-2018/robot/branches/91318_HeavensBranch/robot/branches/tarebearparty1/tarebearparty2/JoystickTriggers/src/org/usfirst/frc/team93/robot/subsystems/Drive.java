package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.commands.DriveContinuous;
import org.usfirst.frc.team93.robot.utilities.CANTalonSRX;
import org.usfirst.frc.team93.robot.utilities.PIDOutputGroup;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Drive extends Subsystem {
	public static CANTalonSRX DriveFL;
	public static CANTalonSRX DriveFR;
	public static CANTalonSRX DriveBL;
	public static CANTalonSRX DriveBR;
	public static PIDOutputGroup left;
	public static PIDOutputGroup right;
	
	public static int driveControlMode; //1 for one controller tank, 2 for two controller tank, 3 for split axis
	
	public Drive(){
		
		driveControlMode = 1;
		
		DriveFL = new CANTalonSRX(RobotMap.DriveMotorFL);
		DriveFR = new CANTalonSRX(RobotMap.DriveMotorFR);
		DriveBL = new CANTalonSRX(RobotMap.DriveMotorBL);
		DriveBR = new CANTalonSRX(RobotMap.DriveMotorBR);
		
		left = new PIDOutputGroup(DriveFL, DriveBL);
		right = new PIDOutputGroup(DriveFR, DriveBR);
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new DriveContinuous());
    	
    }
}

