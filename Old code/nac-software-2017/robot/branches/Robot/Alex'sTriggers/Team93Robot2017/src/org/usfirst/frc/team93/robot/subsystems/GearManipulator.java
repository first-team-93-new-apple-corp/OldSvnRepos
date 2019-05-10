package org.usfirst.frc.team93.robot.subsystems;

import java.util.HashMap;

import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.commands.gearmanipulator.ManualControl;
import org.usfirst.frc.team93.robot.utilities.PIDOutputExtended;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * GearManipulator subsystem.
 */
public class GearManipulator extends Subsystem
{
	// speed controllers
	private static SpeedController GEAR_BELT_SPEEDCONTROLLER;
	private static PIDOutputExtended GEAR_BELT_MOTOR;
	
	private static SpeedController GEAR_INTAKE_TOP_SPEEDCONTROLLER;
	public static PIDOutputExtended GEAR_INTAKE_TOP_MOTOR;
	
	private static SpeedController GEAR_INTAKE_BOTTOM_SPEEDCONTROLLER;
	public static PIDOutputExtended GEAR_INTAKE_BOTTOM_MOTOR;
	
	// solenoids
	private static DoubleSolenoid GEAR_TOP_CLAW;
	private static DoubleSolenoid GEAR_BOTTOM_CLAW;
	
	private static DoubleSolenoid GEAR_DEFLECTOR;
	
	// reed switch
	private static DigitalInput GEAR_BELT_REED_SWITCH;
	
	// belt encoder
	private static Encoder GEAR_BELT_ENCODER;
	
	// belt position PID Controller
	public static PIDController GEAR_BELT_PID;
	
	// constants
	public final static double beltManualSpeed = 0.5;
	
	public static final double intakeMotorValue = 1.0;
	public static final double outputMotorValue = -1.0;
	
	// location enum to number conversion
	public enum GearLocation
	{
		BOTTOM_BACK, BOTTOM_FRONT, PEG, CHUTE;
	}
	
	private static HashMap<GearLocation, Double> locations;
	
	// state trackers
	public static boolean frontClawOpen;
	public static boolean backClawOpen;
	private static boolean beltMoving;
	private static boolean beltPIDEnabled;
	private static boolean maintainPosition;
	
	public GearManipulator()
	{
		// speed controllers
		GEAR_BELT_SPEEDCONTROLLER = new VictorSP(RobotMap.getMap().GEAR_BELT_MOTOR_PIN);
		GEAR_BELT_MOTOR = new PIDOutputExtended(GEAR_BELT_SPEEDCONTROLLER);
		GEAR_BELT_MOTOR.setGain(-1.0);
		
		GEAR_INTAKE_TOP_SPEEDCONTROLLER = new VictorSP(RobotMap.getMap().GEAR_INTAKE_FRONT_MOTOR_PIN);
		GEAR_INTAKE_BOTTOM_SPEEDCONTROLLER = new VictorSP(RobotMap.getMap().GEAR_INTAKE_BACK_MOTOR_PIN);
		
		GEAR_INTAKE_TOP_MOTOR = new PIDOutputExtended(GEAR_INTAKE_TOP_SPEEDCONTROLLER);
		GEAR_INTAKE_TOP_MOTOR.setGain(-1.0);
		GEAR_INTAKE_BOTTOM_MOTOR = new PIDOutputExtended(GEAR_INTAKE_BOTTOM_SPEEDCONTROLLER);
		GEAR_INTAKE_BOTTOM_MOTOR.setGain(-1.0);
		
		// solenoids
		GEAR_TOP_CLAW = new DoubleSolenoid(RobotMap.getMap().GEAR_FRONT_SOLENOID_PIN.A(), RobotMap.getMap().GEAR_FRONT_SOLENOID_PIN.B());
		GEAR_BOTTOM_CLAW = new DoubleSolenoid(RobotMap.getMap().GEAR_BACK_SOLENOID_PIN.A(), RobotMap.getMap().GEAR_BACK_SOLENOID_PIN.B());
		GEAR_DEFLECTOR = new DoubleSolenoid(RobotMap.getMap().GEAR_DEFLECTOR_SOLENOID_PIN.A(), RobotMap.getMap().GEAR_DEFLECTOR_SOLENOID_PIN.B());
		
		// reed switch
		GEAR_BELT_REED_SWITCH = new DigitalInput(RobotMap.getMap().GEAR_BELT_REED_SWITCH_PIN);
		
		// belt encoder
		GEAR_BELT_ENCODER = new Encoder(RobotMap.getMap().GEAR_BELT_ENCODER_PIN.A(), RobotMap.getMap().GEAR_BELT_ENCODER_PIN.B());
		
		// belt position PID Controller
		GEAR_BELT_PID = new PIDController(0.005, 0, 0, GEAR_BELT_ENCODER, GEAR_BELT_MOTOR);
		
		// location enum to number conversion
		locations = new HashMap<GearLocation, Double>();
		// these locations need to be tuned
		locations.put(GearLocation.BOTTOM_BACK, -5784.0 + 5784.0);
		locations.put(GearLocation.BOTTOM_FRONT, -5784.0 + 2700.0);
		locations.put(GearLocation.PEG, -5784.0 + 167.0);
		locations.put(GearLocation.CHUTE, -5784.0 + 0.0);
		
		// state trackers
		// belt is initially not moving
		beltMoving = false;
		maintainPosition = false;
		// claws start out closed
		closeClaws();
		frontClawOpen = false;
		backClawOpen = false;
	}
	
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	@Override
	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		setDefaultCommand(new ManualControl());
	}
	
	/**
	 * Closes both front and back claw.
	 */
	public static void closeClaws()
	{
		closeFrontClaw();
		closeBackClaw();
	}
	
	/**
	 * Opens both front and back claws.
	 * 
	 * Keep in mind that this will NOT work if the belt is moving.
	 */
	public static void openClaws()
	{
		openFrontClaw();
		openBackClaw();
	}
	
	/**
	 * Opens the front claw.
	 * 
	 * Keep in mind that this will NOT work if the belt is moving.
	 */
	public static void openFrontClaw()
	{
		// if the claws are not allowed to open...
		if (!clawsOpenable())
		{
			// do not open the front claw
			System.out.println("Front claw could not open: Belt is moving");
			return;
		}
		// otherwise, open the front claw
		GEAR_TOP_CLAW.set(Value.kReverse);
		frontClawOpen = true;
	}
	
	/**
	 * Closes the front claw.
	 */
	public static void closeFrontClaw()
	{
		GEAR_TOP_CLAW.set(Value.kForward);
		frontClawOpen = false;
	}
	
	/**
	 * Opens the back claw.
	 * 
	 * Keep in mind that this will NOT work if the belt is moving.
	 */
	public static void openBackClaw()
	{
		// if the claws are not allowed to open...
		if (!clawsOpenable())
		{
			// do not open the back claw
			System.out.println("Back claw could not open: Belt is moving");
			return;
		}
		// otherwise, open the back claw
		GEAR_BOTTOM_CLAW.set(Value.kReverse);
		backClawOpen = true;
	}
	
	/**
	 * Closes the back claw.
	 */
	public static void closeBackClaw()
	{
		GEAR_BOTTOM_CLAW.set(Value.kForward);
		backClawOpen = false;
	}
	
	/*
	 * Toggles the front claw to be at whatever state it isn't currently at
	 */
	public static void toggleFrontClaw()
	{
		if (frontClawOpen)
		{
			closeFrontClaw();
		}
		else if (clawsOpenable())
		{
			openFrontClaw();
		}
	}
	
	/*
	 * Toggles the back claw to be at whatever state it isn't currently at
	 */
	public static void toggleBackClaw()
	{
		if (backClawOpen)
		{
			closeBackClaw();
		}
		else if (clawsOpenable())
		{
			openBackClaw();
		}
	}
	
	/*
	 * Toggles both claws to be at whichever state they aren't at
	 */
	public static void toggleGrabber()
	{
		toggleFrontClaw();
		toggleBackClaw();
	}
	
	/**
	 * Checks of all claws are closed.
	 * 
	 * @return
	 */
	public static boolean checkAllClawsClosed()
	{
		// if they're not both closed, then return fales
		if (backClawOpen || frontClawOpen)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	/**
	 * Returns the numeric encoder tick value of a location enum.
	 * 
	 * @param loc
	 * @return
	 */
	public static double getLocation(GearLocation loc)
	{
		return locations.get(loc);
	}
	
	/**
	 * Gets the reed switch. This returns TRUE if the belt is at either end.
	 * 
	 * @return
	 */
	public static boolean getReedSwitch()
	{
		// Note that the Reed Switch is active low, so return the opposite.
		return !GearManipulator.GEAR_BELT_REED_SWITCH.get();
	}
	
	/**
	 * Enables the belt PID.
	 * 
	 * This will not allow any attempts to open the claw UNTIL the PID is
	 * disabled again, since the belt is moving.
	 * 
	 * End this with disableBeltPID()
	 */
	public static void enableBeltPID()
	{
		beltMoving = true;
		// close the claws
		closeClaws();
		beltPIDEnabled = true;
		maintainPosition = false;
		GEAR_BELT_PID.reset();
		GEAR_BELT_PID.enable();
	}
	
	/**
	 * Tells the Belt PID to maintain the current position.
	 * 
	 * This does NOT block the operator from opening the claw, since it is not
	 * moving
	 * 
	 * End this with disableBeltPID()
	 */
	public static void maintainBeltPID()
	{
		beltMoving = false;
		beltPIDEnabled = false;
		maintainPosition = true;
		GEAR_BELT_PID.reset();
		GEAR_BELT_PID.enable();
		setBeltPIDSetpoint(GEAR_BELT_ENCODER.pidGet());
	}
	
	/**
	 * Disables the belt PID.
	 * 
	 * This allows the claw to open again.
	 */
	public static void disableBeltPID()
	{
		beltMoving = false;
		beltPIDEnabled = false;
		maintainPosition = false;
		GEAR_BELT_PID.disable();
	}
	
	/**
	 * Sets the setpoint of the belt PID.
	 * 
	 * if ignoreMaintain is false, this will simply fail if the PID is already
	 * maintaining the current setpoint.
	 * 
	 * if ignoreMaintain is true, this will stop maintaining the setpoint and
	 * force the PID to go to its new position.
	 * 
	 * @param setpoint
	 * @param ignoreMaintain
	 */
	public static void setBeltPIDSetpoint(double setpoint, boolean ignoreMaintain)
	{
		// if we are maintaining and ignoreMaintain is true
		if (maintainPosition && ignoreMaintain)
		{
			// stop maintaining
			disableBeltPID();
			// enable the PID, now beltMoving is true
			enableBeltPID();
		}
		// set the setpoint
		GEAR_BELT_PID.setSetpoint(setpoint);
	}
	
	/**
	 * Default of setBeltPIDSetpoint(double setpoint, boolean ignoreMaintain)
	 * with ignoreMaintain as false.
	 * 
	 * @param setpoint
	 */
	public static void setBeltPIDSetpoint(double setpoint)
	{
		setBeltPIDSetpoint(setpoint, false);
	}
	
	/**
	 * Sets the max error tolerance of the beltPID.
	 * 
	 * @param tolerance
	 */
	public static void setBeltPIDTolerance(double tolerance)
	{
		GEAR_BELT_PID.setAbsoluteTolerance(tolerance);
	}
	
	/**
	 * Checks if the belt position is within the error tolerance.
	 * 
	 * @return
	 */
	public static boolean getBeltOnTarget()
	{
		return GEAR_BELT_PID.onTarget();
	}
	
	/**
	 * Sets the belt motor manually.
	 */
	public static void setBeltMotor(double speed)
	{
		// this will stop the claw from opening if it is not 0.
		if (speed == 0)
		{
			if (!beltPIDEnabled)
			{
				beltMoving = false;
			}
		}
		else
		{
			beltMoving = true;
			closeClaws();
		}
		GEAR_BELT_MOTOR.set(speed);
	}
	
	/**
	 * Checks if the claws are allowed to open or not.
	 * 
	 * This returns false if the belt is moving.
	 */
	public static boolean clawsOpenable()
	{
		return !beltMoving;
	}
	
	/**
	 * Sets the top gear intake motor.
	 * 
	 * @param speed
	 */
	public static void setTopIntakeMotors(double speed)
	{
		GEAR_INTAKE_TOP_MOTOR.set(speed);
	}
	
	/**
	 * Sets the bottom gear intake motor.
	 * 
	 * @param speed
	 */
	public static void setBottomIntakeMotors(double speed)
	{
		GEAR_INTAKE_BOTTOM_MOTOR.set(speed);
	}
	
	/**
	 * Sets the gear intake motors.
	 * 
	 * @param speed
	 */
	public static void setIntakeMotors(double speed)
	{
		setTopIntakeMotors(speed);
		setBottomIntakeMotors(speed);
	}
	
	/*
	 * Sets both intake motors to run if they are off and turn both off if
	 * either is running
	 */
	public static void toggleIntakeMotors()
	{
		// if either of the motors is on
		if (GEAR_INTAKE_BOTTOM_MOTOR.get() != 0 || GEAR_INTAKE_TOP_MOTOR.get() != 0)
		{
			// stop both motors
			setIntakeMotors(0);
		}
		// if both motors are off
		else
		{
			// start both motors
			setIntakeMotors(intakeMotorValue);
		}
	}
	
	/**
	 * Sets the deflector up.
	 */
	public static void setDeflectorUp()
	{
		GEAR_DEFLECTOR.set(Value.kForward);
	}
	
	/**
	 * Sets the deflector down.
	 */
	public static void setDeflectorDown()
	{
		GEAR_DEFLECTOR.set(Value.kReverse);
	}
	
	/**
	 * Returns the current belt position.
	 * 
	 * @return
	 */
	public static double getBeltPosition()
	{
		return GEAR_BELT_ENCODER.pidGet();
	}
	
	/**
	 * Sets the current encoder position as the zero point.
	 * 
	 * @return
	 */
	public static void resetBeltPosition()
	{
		GEAR_BELT_ENCODER.reset();
	}
}
