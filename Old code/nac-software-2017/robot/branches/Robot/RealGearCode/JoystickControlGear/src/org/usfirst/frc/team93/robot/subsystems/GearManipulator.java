package org.usfirst.frc.team93.robot.subsystems;

import java.util.HashMap;

import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearManipulator extends Subsystem {

	public final static double ManualSpeed = 0.5;
	/**
	 * this is the motor that slides the carriage along the track
	 */
	public static Talon GearMotor;
	public static DigitalInput TopLimitSwitch;
	public static DigitalInput BottomLimitSwitch;
	public static Encoder GearLocationEncoder;
	public static PIDController MoveGearPID;
	private static DoubleSolenoid FrontClaw;
	private static DoubleSolenoid BackClaw;
	public static DoubleSolenoid Deflector;
	public static Talon FrontIntake;
	public static Talon BackIntake;
	public static boolean FClawClosed;
	public static boolean BClawClosed;

	public enum GearLocation {
		BOTTOM, BOTTOM_MIDDLE, MIDDLE, TOP;
	}

	public static HashMap<GearLocation, Double> locations;

	public GearManipulator() {
		GearMotor = new Talon(RobotMap.GearTalon);
		TopLimitSwitch = new DigitalInput(RobotMap.TopGearSwitch);
		BottomLimitSwitch = new DigitalInput(RobotMap.BottomGearSwitch);
		GearLocationEncoder = new Encoder(RobotMap.GearLocOne, RobotMap.GearLocTwo);
		MoveGearPID = new PIDController(1, 1, 1, GearLocationEncoder, GearMotor);

		locations = new HashMap<GearLocation, Double>();
		// these locations need to be tuned
		locations.put(GearLocation.BOTTOM, 0.0);
		locations.put(GearLocation.BOTTOM_MIDDLE, 400.0);
		locations.put(GearLocation.MIDDLE, 900.0);
		locations.put(GearLocation.TOP, 1500.0);

		FrontClaw = new DoubleSolenoid(RobotMap.GearSolenoidFrontA, RobotMap.GearSolenoidFrontB);
		BackClaw = new DoubleSolenoid(RobotMap.GearSolenoidBackA, RobotMap.GearSolenoidBackB);
		Deflector = new DoubleSolenoid(RobotMap.DeflectorSolenoidA, RobotMap.DeflectorSolenoidB);
		FrontIntake = new Talon(RobotMap.GearFrontIntake);
		BackIntake = new Talon(RobotMap.GearBackIntake);
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public static void CloseClaws() {
		CloseFrontClaw();
		CloseBackClaw();
	}

	public static void OpenClaws() {
		OpenFrontClaw();
		OpenBackClaw();
	}

	public static void OpenFrontClaw() {
		FrontClaw.set(DoubleSolenoid.Value.kReverse);
		FClawClosed = false;
	}

	public static void CloseFrontClaw() {
		FrontClaw.set(DoubleSolenoid.Value.kForward);
		FClawClosed = true;
	}

	public static void OpenBackClaw() {
		BackClaw.set(DoubleSolenoid.Value.kReverse);
		BClawClosed = false;
	}

	public static void CloseBackClaw() {
		BackClaw.set(DoubleSolenoid.Value.kForward);
		BClawClosed = true;
	}

	public static boolean GetAllClawState() {
		if (!BClawClosed || !FClawClosed) {
			return false;
		} else {
			return true;
		}
	}

	public static double getLocation(GearLocation loc) {
		return locations.get(loc);
	}
}
