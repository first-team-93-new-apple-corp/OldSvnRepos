package org.usfirst.frc.team93.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Talon;
import org.usfirst.frc.team93.robot.utilities.AveragePIDSource;
import org.usfirst.frc.team93.robot.utilities.GyroPIDSource;
import org.usfirst.frc.team93.robot.utilities.PIDOutputExtended;
import org.usfirst.frc.team93.robot.utilities.PIDOutputGroup;
import org.usfirst.frc.team93.robot.utilities.SteeringAndSpeedCoordinator;
import org.usfirst.frc.team93.robot.utilities.Team93PIDController;

import com.kauailabs.navx.frc.AHRS;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	///Sensors
	//Encoders
	public static Encoder LEFT_DRIVE_ENCODER;
	public static Encoder RIGHT_DRIVE_ENCODER;
	//NAV-X
	public static AHRS MXP;
	//Gyro
	public static GyroPIDSource GYRO;
	
	///PID Source Groups
	public static AveragePIDSource DRIVE_ENCODERS;
	
	///Actuators
	//Motors
	public static PIDOutputExtended DRIVE_LEFT_FRONT;
	public static PIDOutputExtended DRIVE_LEFT_BACK;
	public static PIDOutputExtended DRIVE_RIGHT_FRONT;
	public static PIDOutputExtended DRIVE_RIGHT_BACK;
	
	///PID Output Groups
	//Drive
	public static PIDOutputGroup DRIVE_ALL;
	public static PIDOutputGroup DRIVE_LEFT;
	public static PIDOutputGroup DRIVE_RIGHT;
	
	///PID Controllers
	public static Team93PIDController DRIVE_CONTROL_STEERING;
	public static Team93PIDController DRIVE_CONTROL_SPEED;
	
	///PID Coordinators
	public static SteeringAndSpeedCoordinator DRIVE_COORDINATOR;
	
	public RobotMap()
	{
		///Sensors
		//Encoders
		LEFT_DRIVE_ENCODER = new Encoder(8,9); //Check these pins //there good for practice bot
		RIGHT_DRIVE_ENCODER = new Encoder(6,7); //Check these pins //there good for practice bot
		//Gyro
		MXP = new AHRS(SPI.Port.kMXP);
		GYRO = new GyroPIDSource(MXP);
		
		///PID Source Groups
		DRIVE_ENCODERS = new AveragePIDSource(LEFT_DRIVE_ENCODER, RIGHT_DRIVE_ENCODER);
		
		///Actuators
		//Motors
		DRIVE_LEFT_FRONT = new PIDOutputExtended(new Talon(0)); //Check these pins
		DRIVE_LEFT_BACK = new PIDOutputExtended(new Talon(1)); //Check these pins
		DRIVE_RIGHT_FRONT =  new PIDOutputExtended(new Talon(2)); //Check these pins
		DRIVE_RIGHT_BACK = new PIDOutputExtended(new Talon(3)); //Check these pins
		
		///PID Output Groups
		//Drive
		DRIVE_LEFT = new PIDOutputGroup(DRIVE_LEFT_FRONT, DRIVE_LEFT_BACK);
		DRIVE_LEFT.setGain(-1.0);
		DRIVE_RIGHT = new PIDOutputGroup(DRIVE_RIGHT_FRONT, DRIVE_RIGHT_BACK);
		DRIVE_ALL = new PIDOutputGroup(DRIVE_LEFT, DRIVE_RIGHT);
		
		///PID Coordinators
		DRIVE_COORDINATOR = new SteeringAndSpeedCoordinator(DRIVE_LEFT, DRIVE_RIGHT);
		DRIVE_COORDINATOR.setSteeringGain(0.5); //Requires tuning
		
		///PID Controllers
		DRIVE_CONTROL_STEERING = new Team93PIDController(-0.001, 0, 0, 0, GYRO, DRIVE_COORDINATOR.SteeringReceiver); //Requires tuning
		DRIVE_CONTROL_SPEED = new Team93PIDController(0.001, 0, 0, 0, DRIVE_ENCODERS, DRIVE_COORDINATOR.SpeedReceiver); //Requires tuning
	}
}
