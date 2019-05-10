package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Gears extends Subsystem {
	public static double BottomPIDLocation;
	public static double MidPIDLocation;
	public static double TopPIDLocation;
	public static Talon GearLocation;
	public static DigitalInput HighSwitch;
	public static DigitalInput LowSwitch;
	public static Encoder ClawEncoder;
	public static int gearManipLoc;
	public static PIDController GearLocGet;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public Gears()
	{
		GearLocation = new Talon(RobotMap.GearLocMotor);
		HighSwitch = new DigitalInput(RobotMap.GearHighSwitch);
		LowSwitch = new DigitalInput(RobotMap.GearLowSwitch);
		ClawEncoder = new Encoder(RobotMap.ClawEncoderInOne, RobotMap.ClawEncoderInTwo);
		GearLocGet = new PIDController(1, 1, 1, ClawEncoder, GearLocation);
		BottomPIDLocation = 0;
		MidPIDLocation = 700;
		TopPIDLocation = 1300;
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

