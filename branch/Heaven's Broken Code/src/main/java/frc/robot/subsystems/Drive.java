package frc.robot.subsystems;

import frc.robot.RobotMap;
import frc.robot.commands.DriveContinuous;
import frc.robot.other.CANVictorSPX;
import frc.robot.other.GyroPIDSource;
import frc.robot.other.SwerveMotorModule;
import frc.robot.other.SwervePIDOutput;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SPI;
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

	public static AHRS gyro;
	public static GyroPIDSource gyroPIDSource;
	
	public static PIDController rotationPID;
	public static PIDController closeRotationPID;
	public static SwervePIDOutput swervePIDOutput;

	public static Encoder BLDriveEncoder;
	public static Encoder BRDriveEncoder;
	public static Encoder FLDriveEncoder;
	public static Encoder FRDriveEncoder;

	
	public Drive()
	{
		FrontLeftAngleEncoder = new AnalogInput(RobotMap.FrontLeftAngleEncoder);
		FrontRightAngleEncoder = new AnalogInput(RobotMap.FrontRightAngleEncoder);
		BackLeftAngleEncoder = new AnalogInput(RobotMap.BackLeftAngleEncoder);
		BackRightAngleEncoder = new AnalogInput(RobotMap.BackRightAngleEncoder);

		BLDrive = new CANVictorSPX(RobotMap.BLDrive);
		BLDrive.setInverted(true);

		BLRotation = new CANVictorSPX(RobotMap.BLRotation);

		BRDrive = new CANVictorSPX(RobotMap.BRDrive);
		//BRDrive.setInverted();

		BRRotation = new CANVictorSPX(RobotMap.BRRotation);
		//BRRotation.setInverted();

		FLDrive = new CANVictorSPX(RobotMap.FLDrive);
		FLDrive.setInverted(true);

		FLRotation = new CANVictorSPX(RobotMap.FLRotation);

		FRDrive = new CANVictorSPX(RobotMap.FRDrive);
		//FRDrive.setInverted(true);

		FRRotation = new CANVictorSPX(RobotMap.FRRotation);
		
		BLModule = new SwerveMotorModule(4.4, 0.5, 13.904741446153848, BackLeftAngleEncoder, BLDrive, BLRotation, .04, 0.0004, 0, 5);
		BRModule = new SwerveMotorModule(4.4, 0.5, 183.26319766153844, BackRightAngleEncoder, BRDrive, BRRotation, .04, 0.0002, 0, 5);
		FLModule = new SwerveMotorModule(4.4, 0.5, 161.29054366153844, FrontLeftAngleEncoder, FLDrive, FLRotation, .04, 0.0004, 0, 5);
		FRModule = new SwerveMotorModule(4.4, 0.5, 242.19498249230767, FrontRightAngleEncoder, FRDrive, FRRotation, 0.04, 0.0004, 0, 5 );

		gyro = new AHRS(SPI.Port.kMXP);
		
		gyroPIDSource = new GyroPIDSource(gyro);

		swervePIDOutput = new SwervePIDOutput();
		rotationPID = new PIDController(0.015
		, .000, .03, gyroPIDSource, SwervePIDOutput.torqueOutput);
		rotationPID.setInputRange(0, 360);
		rotationPID.setContinuous();

		closeRotationPID = new PIDController(0.02, .0000, .035, gyroPIDSource, SwervePIDOutput.torqueOutput);
		closeRotationPID.setInputRange(0, 360);
		closeRotationPID.setContinuous();

		BRDriveEncoder = new Encoder(RobotMap.BREncoder1, RobotMap.BREncoder2);
		BLDriveEncoder = new Encoder(RobotMap.BLEncoder1, RobotMap.BLEncoder2);
		FRDriveEncoder = new Encoder(RobotMap.FREncoder1, RobotMap.FREncoder2);
		FLDriveEncoder = new Encoder(RobotMap.FLEncoder1, RobotMap.FLEncoder2);
	}
	

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new DriveContinuous());
    }
}