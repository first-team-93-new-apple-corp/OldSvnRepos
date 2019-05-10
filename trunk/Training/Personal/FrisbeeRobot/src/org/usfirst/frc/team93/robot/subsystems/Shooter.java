package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Shooter extends Subsystem {
	public static SpeedController Intake;
	public static SpeedController SubShooter;
	public static SpeedController MainShooter;
	
	public static DoubleSolenoid piston;
	public static DoubleSolenoid piston1;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.new SpeedController

    public void initDefaultCommand() {
    	Intake = new Talon(RobotMap.Intake);
    	MainShooter = new Talon(RobotMap.MainShooter);
    	SubShooter = new Talon(RobotMap.SubShooter);
    	
		piston = new DoubleSolenoid(RobotMap.piston, RobotMap.piston1);

        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

