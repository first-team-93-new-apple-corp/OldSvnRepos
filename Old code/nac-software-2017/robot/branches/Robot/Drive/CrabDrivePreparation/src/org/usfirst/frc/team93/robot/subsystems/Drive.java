package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.commands.CrabDriveContinuous;
import org.usfirst.frc.team93.robot.commands.CrabDriveContinuousDirectionControl;
import org.usfirst.frc.team93.robot.commands.CrabDriveContinuousJoystickYaw;
import org.usfirst.frc.team93.robot.commands.CrabDriveContinuousTank;
import org.usfirst.frc.team93.robot.commands.CrabDriveContinuousYawControl;
import org.usfirst.frc.team93.robot.commands.SPIEncoderPrinter;
import org.usfirst.frc.team93.robot.utilities.GyroPIDSource;
import org.usfirst.frc.team93.robot.utilities.MedianPIDSource;
import org.usfirst.frc.team93.robot.utilities.PIDOutputExtended;
import org.usfirst.frc.team93.robot.utilities.PIDOutputGroup;
import org.usfirst.frc.team93.robot.utilities.SPIEncoderPIDSource;
import org.usfirst.frc.team93.robot.utilities.crabdrive.CrabDriveAngleAdjuster;
import org.usfirst.frc.team93.robot.utilities.crabdrive.CrabDriveDirectionPIDSource;
import org.usfirst.frc.team93.robot.utilities.crabdrive.CrabDriveSpeedMotor;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Drive extends Subsystem {

	//the orientation of the robot.
	//1.0 for front facing, -1.0 for backwards facing.
	private static double m_orientation = 1.0;
	
	///Sensors
	//Encoders
	public static Encoder LEFT_DRIVE_ENCODER;
	public static Encoder RIGHT_DRIVE_ENCODER;
	//SPI Encoders
	public static SPI DRIVE_SPI_LEFT_FRONT;
	public static SPI DRIVE_SPI_LEFT_BACK;
	public static SPI DRIVE_SPI_RIGHT_FRONT;
	public static SPI DRIVE_SPI_RIGHT_BACK;
	//NAV-X
	public static AHRS MXP;
	//Gyro
	public static GyroPIDSource GYRO;
	
	///PID Sources
	//Crab Drive Speed PIDSource
	public static MedianPIDSource DRIVE_ENCODERS;
	//Crab Drive Direction PIDSources
	public static SPIEncoderPIDSource DRIVE_DIRECTION_SPI_LEFT_FRONT;
	public static SPIEncoderPIDSource DRIVE_DIRECTION_SPI_LEFT_BACK;
	public static SPIEncoderPIDSource DRIVE_DIRECTION_SPI_RIGHT_FRONT;
	public static SPIEncoderPIDSource DRIVE_DIRECTION_SPI_RIGHT_BACK;
	//Aggregation of the 4 SPIEncoderPIDSources
	public static MedianPIDSource DRIVE_DIRECTION_SPI;
	
	///Actuators
	//Drive Talons
	public static CANTalon DRIVE_LEFT_FRONT_TALON;
	public static CANTalon DRIVE_LEFT_BACK_TALON;
	public static CANTalon DRIVE_RIGHT_FRONT_TALON;
	public static CANTalon DRIVE_RIGHT_BACK_TALON;
	//Drive Talons
	public static CANTalon DRIVE_DIRECTION_LEFT_FRONT_TALON;
	public static CANTalon DRIVE_DIRECTION_LEFT_BACK_TALON;
	public static CANTalon DRIVE_DIRECTION_RIGHT_FRONT_TALON;
	public static CANTalon DRIVE_DIRECTION_RIGHT_BACK_TALON;
	//Motors
	public static PIDOutputExtended DRIVE_LEFT_FRONT;
	public static PIDOutputExtended DRIVE_LEFT_BACK;
	public static PIDOutputExtended DRIVE_RIGHT_FRONT;
	public static PIDOutputExtended DRIVE_RIGHT_BACK;
	//Crab Drive Wheel Direction motors
	public static PIDOutputExtended DRIVE_DIRECTION_LEFT_FRONT;
	public static PIDOutputExtended DRIVE_DIRECTION_LEFT_BACK;
	public static PIDOutputExtended DRIVE_DIRECTION_RIGHT_FRONT;
	public static PIDOutputExtended DRIVE_DIRECTION_RIGHT_BACK;
	
	///PID Outputs
	//Drive
	public static PIDOutputGroup DRIVE_ALL;
	public static PIDOutputGroup DRIVE_LEFT;
	public static PIDOutputGroup DRIVE_RIGHT;
	
	public static PIDOutputGroup DRIVE_DIRECTION;
	
	//Crab Drive Motors
	public static CrabDriveSpeedMotor CRAB_DRIVE_LEFT;
	public static CrabDriveSpeedMotor CRAB_DRIVE_RIGHT;
	
	//Crab Drive Angle Adjuster
	public static CrabDriveAngleAdjuster CRAB_DRIVE_YAW;
	
	///PIDControllers
	public static PIDController YawController;
	public static PIDController DirectionController;
	
	public static Timer timer = new Timer();
	
	public Drive()
	{
		///Sensors
		//Encoders
		LEFT_DRIVE_ENCODER = new Encoder(RobotMap.getMap().LEFT_DRIVE_ENCODER_PIN.A(), RobotMap.getMap().LEFT_DRIVE_ENCODER_PIN.B());
		RIGHT_DRIVE_ENCODER = new Encoder(RobotMap.getMap().RIGHT_DRIVE_ENCODER_PIN.A(), RobotMap.getMap().RIGHT_DRIVE_ENCODER_PIN.B());
		//SPI Encoders
		DRIVE_SPI_LEFT_FRONT = new SPI(RobotMap.getMap().DRIVE_SPI_LEFT_FRONT_PORT);
		DRIVE_SPI_LEFT_BACK = new SPI(RobotMap.getMap().DRIVE_SPI_LEFT_BACK_PORT);
		DRIVE_SPI_RIGHT_FRONT = new SPI(RobotMap.getMap().DRIVE_SPI_RIGHT_FRONT_PORT);
		DRIVE_SPI_RIGHT_BACK = new SPI(RobotMap.getMap().DRIVE_SPI_RIGHT_BACK_PORT);
		//NAV-X
		MXP = new AHRS(RobotMap.getMap().MXP_PORT);
		//Gyro
		GYRO = new GyroPIDSource(MXP);
		
		///PID Sources
		//Crab Drive Speed PIDSource
		DRIVE_ENCODERS = new MedianPIDSource(LEFT_DRIVE_ENCODER, RIGHT_DRIVE_ENCODER);
		//Crab Drive Direction PIDSources
		DRIVE_DIRECTION_SPI_LEFT_FRONT = new SPIEncoderPIDSource(DRIVE_SPI_LEFT_FRONT);
		DRIVE_DIRECTION_SPI_LEFT_FRONT.setGain(360.0 / 8636);
		DRIVE_DIRECTION_SPI_LEFT_FRONT.reset();
		DRIVE_DIRECTION_SPI_LEFT_BACK = new SPIEncoderPIDSource(DRIVE_SPI_LEFT_BACK);
		DRIVE_DIRECTION_SPI_RIGHT_FRONT = new SPIEncoderPIDSource(DRIVE_SPI_RIGHT_FRONT);
		DRIVE_DIRECTION_SPI_RIGHT_BACK = new SPIEncoderPIDSource(DRIVE_SPI_RIGHT_BACK);
		//Aggregation of the 4 SPIEncoderPIDSources
		DRIVE_DIRECTION_SPI = new MedianPIDSource(DRIVE_DIRECTION_SPI_LEFT_FRONT);
		
		///Actuators
		//Drive Talons
		DRIVE_LEFT_FRONT_TALON = new CANTalon(RobotMap.getMap().DRIVE_LEFT_FRONT_PIN);
		DRIVE_LEFT_BACK_TALON = new CANTalon(RobotMap.getMap().DRIVE_LEFT_BACK_PIN);
		DRIVE_RIGHT_FRONT_TALON = new CANTalon(RobotMap.getMap().DRIVE_RIGHT_FRONT_PIN);
		DRIVE_RIGHT_BACK_TALON = new CANTalon(RobotMap.getMap().DRIVE_RIGHT_BACK_PIN);
		//Drive Direction Talons
		DRIVE_DIRECTION_LEFT_FRONT_TALON = new CANTalon(RobotMap.getMap().DRIVE_DIRECTION_LEFT_FRONT_PIN);
		DRIVE_DIRECTION_LEFT_BACK_TALON = new CANTalon(RobotMap.getMap().DRIVE_DIRECTION_LEFT_BACK_PIN);
		DRIVE_DIRECTION_RIGHT_FRONT_TALON = new CANTalon(RobotMap.getMap().DRIVE_DIRECTION_RIGHT_FRONT_PIN);
		DRIVE_DIRECTION_RIGHT_BACK_TALON = new CANTalon(RobotMap.getMap().DRIVE_DIRECTION_RIGHT_BACK_PIN);
		//Drive Motors
		DRIVE_LEFT_FRONT = new PIDOutputExtended(DRIVE_LEFT_FRONT_TALON);
		DRIVE_LEFT_BACK = new PIDOutputExtended(DRIVE_LEFT_BACK_TALON);
		DRIVE_RIGHT_FRONT =  new PIDOutputExtended(DRIVE_RIGHT_FRONT_TALON);
		DRIVE_RIGHT_FRONT.setGain(-1.0);
		DRIVE_RIGHT_BACK = new PIDOutputExtended(DRIVE_RIGHT_BACK_TALON);
		DRIVE_RIGHT_BACK.setGain(-1.0);
		//Drive Wheel Direction Motors
		DRIVE_DIRECTION_LEFT_FRONT = new PIDOutputExtended(DRIVE_DIRECTION_LEFT_FRONT_TALON);
		DRIVE_DIRECTION_LEFT_BACK = new PIDOutputExtended(DRIVE_DIRECTION_LEFT_BACK_TALON);
		DRIVE_DIRECTION_RIGHT_FRONT = new PIDOutputExtended(DRIVE_DIRECTION_RIGHT_FRONT_TALON);
		DRIVE_DIRECTION_RIGHT_BACK = new PIDOutputExtended(DRIVE_DIRECTION_RIGHT_BACK_TALON);
		
		///PID Output Groups
		//Drive
		DRIVE_LEFT = new PIDOutputGroup(DRIVE_LEFT_FRONT, DRIVE_LEFT_BACK);
		DRIVE_RIGHT = new PIDOutputGroup(DRIVE_RIGHT_FRONT, DRIVE_RIGHT_BACK);
		DRIVE_ALL = new PIDOutputGroup(DRIVE_LEFT, DRIVE_RIGHT);
		//Drive Wheel Direction
		DRIVE_DIRECTION = new PIDOutputGroup(DRIVE_DIRECTION_LEFT_FRONT, DRIVE_DIRECTION_LEFT_BACK, DRIVE_DIRECTION_RIGHT_FRONT, DRIVE_DIRECTION_RIGHT_BACK);
		
		
		//Crab Drive Outputs
		//Crab Drive Motors
		CRAB_DRIVE_LEFT = new CrabDriveSpeedMotor(DRIVE_LEFT);
		CRAB_DRIVE_RIGHT = new CrabDriveSpeedMotor(DRIVE_RIGHT);
		//Crab Drive Angle Adjuster
		CRAB_DRIVE_YAW = new CrabDriveAngleAdjuster(CRAB_DRIVE_LEFT, CRAB_DRIVE_RIGHT);
		
		///PIDControllers
		YawController = new PIDController(-0.05, 0, 0, GYRO, CRAB_DRIVE_YAW);
		DirectionController = new PIDController(0.02, 0, 0, DRIVE_DIRECTION_SPI, DRIVE_DIRECTION);
	}
	
    public void initDefaultCommand() {
        // Crab Drive Command
        //setDefaultCommand(new CrabDriveContinuous());
    }
    
    /**
     * Set left drive motors to a speed.
     * @param speed
     */
    public static void setLeftMotors(double speed)
    {
    	DRIVE_LEFT.set(speed);
    }
    
    /**
     * Set right drive motors to a speed.
     * @param speed
     */
    public static void setRightMotors(double speed)
    {
    	DRIVE_RIGHT.set(speed);
    }
    
    /**
     * Reset encoders and gyro.
     */
    public static void resetSensors()
    {
    	GYRO.reset();
    	LEFT_DRIVE_ENCODER.reset();
    	RIGHT_DRIVE_ENCODER.reset();
    }
    
    /**
     * Set all motors to a speed.
     * Commonly used with 0.0 to stop the robot.
     * @param speed
     */
    public static void setAllMotors(double speed)
    {
    	setLeftMotors(speed);
    	setRightMotors(speed);
    }
    
    /**
     * Returns the orientatino of the robot.
     * 1.0 for front facing, -1.0 for backwards facing.
     * @return
     */
    public static double getOrientation()
    {
    	return m_orientation;
    }
    
    /**
     * Sets the orientation of the robot.
     * 1.0 for front facing, -1.0 for backwards facing.
     * @param orientation
     */
    public static void setOrientation(double orientation)
    {
    	m_orientation = orientation;
    }
    
    /**
     * Toggles the robot orientation, whether front or back.
     */
    public static void toggleOrientation()
    {
    	m_orientation = -m_orientation;
    }
}

