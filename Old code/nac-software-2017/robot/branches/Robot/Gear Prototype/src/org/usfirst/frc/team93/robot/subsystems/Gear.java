package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Gear extends Subsystem {
	public static Talon MoveGearAcq;
	public static DigitalInput UpperSwitch;
	public static DigitalInput MidSwitch;
	public static DigitalInput LowerSwitch;
	public static int AcqLoc;
	public static Encoder GearLoc;
	public static int GearLocLoc;
	private PIDController pid;
	
	public static PIDController GearPID;
	public Gear()
	{
		MoveGearAcq = new Talon(RobotMap.MoveGearAcq);
		UpperSwitch = new DigitalInput(RobotMap.UpperSwitch);
		MidSwitch = new DigitalInput(RobotMap.MidSwitch);
		UpperSwitch = new DigitalInput(RobotMap.LowerSwitch);
		GearLoc = new Encoder(RobotMap.GearLocEncA, RobotMap.GearLocEncB);
		GearPID = new PIDController(.1, .1, .1, .1, GearLoc, MoveGearAcq);
		
//		ResetLocation();
		pid = new PIDController(4, 0, 0, new PIDSource() {
			PIDSourceType m_sourceType = PIDSourceType.kDisplacement;

			@Override
			public double pidGet() {
				return GearLoc.getDistance();
			}

			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				m_sourceType = pidSource;
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return m_sourceType;
			}
		}, new PIDOutput() {
			@Override
			public void pidWrite(double d) {
				 Gear.MoveGearAcq.set(d);
			}
		});
		pid.setAbsoluteTolerance(0.01);
		pid.setSetpoint(0);
	}
	

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
//    public void ResetLocation()
//    {
//    	if(!UpperSwitch.get())
//    	{
//    		MoveGearAcq.set(0.5);
//    	}
//    	AcqLoc = 3;
//    }
}

