package frc.robot.subsystems;

import frc.robot.RobotMap;
import frc.robot.commands.DriveContinuous;
import frc.robot.other.CANVictorSPX;
import frc.robot.other.SwerveMotorModule;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Drive extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public static AnalogInput FrontLeftAngleEncoder;
	public static AnalogInput FrontRightAngleEncoder;
	public static AnalogInput BackLeftAngleEncoder;
	public static AnalogInput BackRightAngleEncoder;
	
	public static SwerveMotorModule BLModule;
	public static SwerveMotorModule BRModule;
	public static SwerveMotorModule FLModule;
	public static SwerveMotorModule FRModule;

	public static CANVictorSPX BLDrive;
	public static CANVictorSPX BLRotation;
	public static CANVictorSPX BRDrive;
	public static CANVictorSPX BRRotation;
	public static CANVictorSPX FLDrive;
	public static CANVictorSPX FLRotation;
	public static CANVictorSPX FRDrive;
	public static CANVictorSPX FRRotation;
	
	public Drive()
	{
		FrontLeftAngleEncoder = new AnalogInput(RobotMap.FrontLeftAngleEncoder);
		FrontRightAngleEncoder = new AnalogInput(RobotMap.FrontRightAngleEncoder);
		BackLeftAngleEncoder = new AnalogInput(RobotMap.BackLeftAngleEncoder);
		BackRightAngleEncoder = new AnalogInput(RobotMap.BackRightAngleEncoder);

		BLDrive = new CANVictorSPX(RobotMap.BLDrive);
		//BLDrive.setInverted(true);
		BLRotation = new CANVictorSPX(RobotMap.BLRotation);
		BLRotation.setInverted(true);
		BRDrive = new CANVictorSPX(RobotMap.BRDrive);
		BRDrive.setInverted(true);
		BRRotation = new CANVictorSPX(RobotMap.BRRotation);
		BRRotation.setInverted(true);
		FLDrive = new CANVictorSPX(RobotMap.FLDrive);
		FLRotation = new CANVictorSPX(RobotMap.FLRotation);
		FLRotation.setInverted(true);
		FRDrive = new CANVictorSPX(RobotMap.FRDrive);
		FRDrive.setInverted(true);
		FRRotation = new CANVictorSPX(RobotMap.FRRotation);
		FRRotation.setInverted(true);
		
		BLModule = new SwerveMotorModule(4.4, 0.5, 91.31609169230768 , BackLeftAngleEncoder, BLDrive, BLRotation, .04, 0.0004, 0, 5);
		BRModule = new SwerveMotorModule(4.4, 0.5, 91.65413252307691, BackRightAngleEncoder, BRDrive, BRRotation, .04, 0.0002, 0, 5);
		FLModule = new SwerveMotorModule(4.4, 0.5, 84.44259479999998, FrontLeftAngleEncoder, FLDrive, FLRotation, .04, 0.0004, 0, 5);
		FRModule = new SwerveMotorModule(4.4, 0.5, 96.83742526153844, FrontRightAngleEncoder, FRDrive, FRRotation, 0.04, 0.0004, 0, 5 );
	}
	

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new DriveContinuous());
    }
}