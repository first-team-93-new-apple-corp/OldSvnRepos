package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.commands.drivecontinuous;
import org.usfirst.frc.team93.robot.utilities.CANVictorSPX;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Drive extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public static CANVictorSPX frontRight;
	public static CANVictorSPX backRight;
	public static CANVictorSPX frontLeft;
	public static CANVictorSPX backLeft;
	
	public static DigitalInput limitSwitch;
	
	public static DoubleSolenoid piston;
	
	
	public Drive() {
		frontRight=new CANVictorSPX(RobotMap.frontRight);
		backRight=new CANVictorSPX(RobotMap.backRight);
		frontLeft=new CANVictorSPX(RobotMap.frontLeft);
		backLeft=new CANVictorSPX(RobotMap.backLeft);
		
		limitSwitch = new DigitalInput(RobotMap.limitSwitch);
		limitSwitch.get();
		
		piston = new DoubleSolenoid(6, 9);
		
	}
    public void initDefaultCommand() {
    	
    	setDefaultCommand(new drivecontinuous());
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

