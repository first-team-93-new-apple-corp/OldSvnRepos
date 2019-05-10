package frc.robot.subsystems;

import frc.robot.RobotMap;
import frc.robot.commands.DriveContinuous;
import frc.robot.other.CANTalonSRX;
import frc.robot.other.CANVictorSPX;
import frc.robot.other.GyroPIDSource;
import frc.robot.other.SwerveMotorModule;
import frc.robot.other.SwervePIDOutput;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Drive extends Subsystem {

    // Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	//Hall-effect sensors used for the rotation of the wheels
	public static AnalogInput FrontLeftAngleEncoder;
	public static AnalogInput FrontRightAngleEncoder;
	public static AnalogInput BackLeftAngleEncoder;
	public static AnalogInput BackRightAngleEncoder;
	
	//The SwerveMotorModules for all the wheel modules
	public static SwerveMotorModule BLModule;
	public static SwerveMotorModule BRModule;
	public static SwerveMotorModule FLModule;
	public static SwerveMotorModule FRModule;

	//The speedControllers for driving
	public static CANTalonSRX BLDrive;
	public static CANVictorSPX BLRotation;
	public static CANTalonSRX BRDrive;
	public static CANVictorSPX BRRotation;
	public static CANTalonSRX FLDrive;
	public static CANVictorSPX FLRotation;
	public static CANTalonSRX FRDrive;
	public static CANVictorSPX FRRotation;

	//The gyro and the pid source from the gyro
	public static AHRS gyro;
	public static GyroPIDSource gyroPIDSource;
	
	//The PID controller for close rotation and far rotation
	public static PIDController rotationPID;
	public static PIDController closeRotationPID;

	//contains all the swerve pid outputs
	public static SwervePIDOutput swervePIDOutput;

	//The PID controller for cameracode
	public static PIDController lockedXCamDrive;

	
	public Drive()
	{
		FrontLeftAngleEncoder = new AnalogInput(RobotMap.FrontLeftAngleEncoder);
		FrontRightAngleEncoder = new AnalogInput(RobotMap.FrontRightAngleEncoder);
		BackLeftAngleEncoder = new AnalogInput(RobotMap.BackLeftAngleEncoder);
		BackRightAngleEncoder = new AnalogInput(RobotMap.BackRightAngleEncoder);

		BLDrive = new CANTalonSRX(RobotMap.BLDrive);
		BLDrive.setInverted(true);

		BLRotation = new CANVictorSPX(RobotMap.BLRotation);

		BRDrive = new CANTalonSRX(RobotMap.BRDrive);
		//BRDrive.setInverted();

		BRRotation = new CANVictorSPX(RobotMap.BRRotation);
		//BRRotation.setInverted();

		FLDrive = new CANTalonSRX(RobotMap.FLDrive);
		FLDrive.setInverted(true);

		FLRotation = new CANVictorSPX(RobotMap.FLRotation);
		//FLRotation.setInverted(true);
		FRDrive = new CANTalonSRX(RobotMap.FRDrive);
		//FRDrive.setInverted(true);

		FRRotation = new CANVictorSPX(RobotMap.FRRotation);
		//RRotation.setInverted(true);
		//                               maxvolt,                                                          p value         offset
		//                                    minvolt,                                         rotation motor           d value
		//                                         Angle at 0,  angle encoder,        displacement motor        i value
		BLModule = new SwerveMotorModule(4.4, 0.5, 216.8419202, BackLeftAngleEncoder, BLDrive, BLRotation, .05, 0.0004, 0, 5);
		BRModule = new SwerveMotorModule(4.4, 0.5, 235.2088053, BackRightAngleEncoder, BRDrive, BRRotation, .05, 0.0002, 0, 5);



		FLModule = new SwerveMotorModule(4.4, 0.5, 263.9422759, FrontLeftAngleEncoder, FLDrive, FLRotation, .05, 0.0004, 0, 5);

		
		
		FRModule = new SwerveMotorModule(4.4, 0.5, 25.84885080, FrontRightAngleEncoder, FRDrive, FRRotation, .05, 0.0004, 0, 5 );

		gyro = new AHRS(SPI.Port.kMXP);
		
		gyroPIDSource = new GyroPIDSource(gyro);

		swervePIDOutput = new SwervePIDOutput();
		rotationPID = new PIDController(0.001, .000, 0.003, gyroPIDSource, SwervePIDOutput.torqueOutput);
		rotationPID.setInputRange(0, 360);
		rotationPID.setContinuous();

		closeRotationPID = new PIDController(0.0005, .0000, 0.001, gyroPIDSource, SwervePIDOutput.torqueOutput);
		closeRotationPID.setInputRange(0, 360);
		closeRotationPID.setContinuous();

		//lockedXCamDrive = new PIDController(0.03, 0, 0.11, CameraCodeAsPIDSource.cameraXSource, SwervePIDOutput.xOutput);
	}
	

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new DriveContinuous());
    }
}