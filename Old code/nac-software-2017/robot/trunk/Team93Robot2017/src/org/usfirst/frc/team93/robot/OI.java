package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.commands.ToggleCompressor;
import org.usfirst.frc.team93.robot.commands.drive.CrabDriveAlign;
import org.usfirst.frc.team93.robot.commands.drive.CrabDriveDefense;
import org.usfirst.frc.team93.robot.commands.drive.CrabDriveReleaseDefaultYaw;
import org.usfirst.frc.team93.robot.commands.drive.CrabDriveResetWheels;
import org.usfirst.frc.team93.robot.commands.drive.CrabDriveSetDefaultYaw;
import org.usfirst.frc.team93.robot.commands.drive.CrabDriveSetMode;
import org.usfirst.frc.team93.robot.commands.gearmanipulator.BackAcquire;
import org.usfirst.frc.team93.robot.commands.gearmanipulator.DeflectorFlip;
import org.usfirst.frc.team93.robot.commands.gearmanipulator.FrontAcquire;
import org.usfirst.frc.team93.robot.commands.gearmanipulator.ManualControl;
import org.usfirst.frc.team93.robot.commands.gearmanipulator.MoveGearSafe;
import org.usfirst.frc.team93.robot.commands.gearmanipulator.ToggleBothClaws;
import org.usfirst.frc.team93.robot.commands.gearmanipulator.ToggleBottomClaw;
import org.usfirst.frc.team93.robot.commands.gearmanipulator.ToggleIntakeMotors;
import org.usfirst.frc.team93.robot.commands.gearmanipulator.ToggleTopClaw;
import org.usfirst.frc.team93.robot.commands.gearmanipulator.WallAcquire;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator;
import org.usfirst.frc.team93.robot.utilities.JoystickAxis;
import org.usfirst.frc.team93.robot.utilities.JoystickAxisLimited;
import org.usfirst.frc.team93.robot.utilities.JoystickExtended;
import org.usfirst.frc.team93.robot.utilities.JoystickExtendedLimited;
import org.usfirst.frc.team93.robot.utilities.crabdrive.CrabDriveCoordinator.CrabDriveMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI
{
	// driver
	public static Joystick driver;
	
	public static JoystickExtendedLimited movement;
	public static JoystickExtended movementUnlimited;
	public static JoystickAxisLimited yaw;
	public static JoystickAxis yawUnlimited;
	public static JoystickAxis slowSpeedSlider;
	
	public static int trigger = 1;
	public static int thumb = 2;
	// axis
	private final int xAxis = 0;
	private final int yAxis = 1;
	private final int zAxis = 2;
	
	private static Button fieldCentric;
	private static Button robotCentric;
	private static Button resetWheels;
	
	public static Button defenseDrive;
	public static Button realignDrive;
	
	/*
	 * Port numbers for operator controller
	 */
	private static final int a = 2;
	private static final int b = 3;
	private static final int x = 1;
	private static final int y = 4;
	private static final int lb = 5;
	private static final int rb = 6;
	private static final int ltrigger = 7;
	private static final int rtrigger = 8;
	private static final int back = 9;
	private static final int start = 10;
	// this int has to be public because it's used in ManualControl
	public static final int lstick = 11;
	// private static final int rstick = 12;
	private static final int leftYAxis = 1;
	private static final int rightYAxis = 3;
	
	// operator
	public static Joystick operator;
	public static JoystickAxis belt;
	public static JoystickAxis climber;
	
	private static Button toggleTopClaw;
	private static Button toggleBottomClaw;
	private static Button toggleBothClaws;
	private static Button toggleIntakeMotors;
	
	private static Button toggleCompressor;
	
	/**
	 * @codereview josh.hawbaker 4.6.17 Funny enough, topSetpoint and
	 *             bottomSetpoint weren't being used at all because they're
	 *             managed by ShoulderButtonReader.
	 */
	private static Button pegSetpoint;
	private static Button frontSetpoint;
	private static Button chuteSetpoint;
	private static Button backSetpoint;
	private static Button startManualControl;
	private static Button deflector;
	
	private static JoystickButton driveYaw0;
	private static JoystickButton driveYawRelease;
	private static JoystickButton driveYawPos45;
	private static JoystickButton driveYawNeg45;
	private static JoystickButton driveYawPos60;
	private static JoystickButton driveYawNeg60;
	
	public OI()
	{
		// driver
		driver = new Joystick(0);
		
		movement = new JoystickExtendedLimited(driver, xAxis, yAxis);
		movement.y().setGain(-1.0);
		movementUnlimited = new JoystickExtended(driver, xAxis, yAxis);
		movementUnlimited.y().setGain(-1.0);
		yaw = new JoystickAxisLimited(driver, zAxis);
		yaw.setGain(-1.0);
		yawUnlimited = new JoystickAxis(driver, zAxis);
		yawUnlimited.setGain(-1.0);
		
		yaw.setDeadzone(0.3);
		
		/*
		 * Note that the driver buttons don't have names unlike the operator
		 * buttons. This is because on the controller, the buttons are
		 * individually numbered, not given names like 'a' or 'start'
		 */
		fieldCentric = new JoystickButton(driver, 3);
		fieldCentric.whenPressed(new CrabDriveSetMode(CrabDriveMode.FieldCentric));
		robotCentric = new JoystickButton(driver, 4);
		robotCentric.whenPressed(new CrabDriveSetMode(CrabDriveMode.RobotCentric));
		
		resetWheels = new JoystickButton(driver, 5);
		resetWheels.whenPressed(new CrabDriveResetWheels());
		
		// drive angle setpoints
		driveYaw0 = new JoystickButton(driver, 7);
		driveYaw0.whenPressed(new CrabDriveSetDefaultYaw(0.0));
		driveYawRelease = new JoystickButton(driver, 8);
		driveYawRelease.whenPressed(new CrabDriveReleaseDefaultYaw());
		driveYawPos45 = new JoystickButton(driver, 9);
		driveYawPos45.whenPressed(new CrabDriveSetDefaultYaw(45.0));
		driveYawNeg45 = new JoystickButton(driver, 10);
		driveYawNeg45.whenPressed(new CrabDriveSetDefaultYaw(-45.0));
		driveYawPos60 = new JoystickButton(driver, 11);
		driveYawPos60.whenPressed(new CrabDriveSetDefaultYaw(60.0));
		driveYawNeg60 = new JoystickButton(driver, 12);
		driveYawNeg60.whenPressed(new CrabDriveSetDefaultYaw(-60.0));
		
		realignDrive = new JoystickButton(driver, trigger);
		realignDrive.whenPressed(new CrabDriveAlign());
		defenseDrive = new JoystickButton(driver, thumb);
		defenseDrive.whenPressed(new CrabDriveDefense());
		slowSpeedSlider = new JoystickAxis(driver, 3);
		slowSpeedSlider.setDeadzone(0);
		slowSpeedSlider.setGain(-1.0);
		
		// operator
		operator = new Joystick(1);
		belt = new JoystickAxis(operator, leftYAxis);
		belt.setGain(-1.0);
		climber = new JoystickAxis(operator, rightYAxis);
		climber.setGain(-1.0);
		
		toggleTopClaw = new JoystickButton(operator, y);
		toggleTopClaw.whenPressed(new ToggleTopClaw());
		toggleBottomClaw = new JoystickButton(operator, a);
		toggleBottomClaw.whenPressed(new ToggleBottomClaw());
		toggleBothClaws = new JoystickButton(operator, x);
		toggleBothClaws.whenPressed(new ToggleBothClaws());
		toggleIntakeMotors = new JoystickButton(operator, b);
		toggleIntakeMotors.whenPressed(new ToggleIntakeMotors());
		
		deflector = new JoystickButton(operator, back);
		deflector.whenPressed(new DeflectorFlip());
		
		pegSetpoint = new JoystickButton(operator, rb);
		pegSetpoint.whenPressed(new MoveGearSafe(GearManipulator.GearLocation.PEG));
		frontSetpoint = new JoystickButton(operator, lb);
		frontSetpoint.whenPressed(new FrontAcquire());
		chuteSetpoint = new JoystickButton(operator, rtrigger);
		chuteSetpoint.whenPressed(new WallAcquire());
		backSetpoint = new JoystickButton(operator, ltrigger);
		backSetpoint.whenPressed(new BackAcquire());
		startManualControl = new JoystickButton(operator, lstick);
		startManualControl.whenPressed(new ManualControl());
		
		toggleCompressor = new JoystickButton(operator, start);
		toggleCompressor.whenPressed(new ToggleCompressor());
	}
}
