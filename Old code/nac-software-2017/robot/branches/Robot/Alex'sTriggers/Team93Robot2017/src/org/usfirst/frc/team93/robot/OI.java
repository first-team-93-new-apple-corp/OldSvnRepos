package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.commands.drive.CrabDriveResetWheels;
import org.usfirst.frc.team93.robot.commands.drive.CrabDriveSetMode;
import org.usfirst.frc.team93.robot.commands.drive.CrabDriveSwitch;
import org.usfirst.frc.team93.robot.commands.gearmanipulator.DeflectorFlip;
import org.usfirst.frc.team93.robot.commands.gearmanipulator.FrontAcquireA;
import org.usfirst.frc.team93.robot.commands.gearmanipulator.ManualControl;
import org.usfirst.frc.team93.robot.commands.gearmanipulator.MoveGearSafe;
import org.usfirst.frc.team93.robot.commands.gearmanipulator.ReleaseGearB;
import org.usfirst.frc.team93.robot.commands.gearmanipulator.ToggleBothClaws;
import org.usfirst.frc.team93.robot.commands.gearmanipulator.ToggleBottomClaw;
import org.usfirst.frc.team93.robot.commands.gearmanipulator.ToggleIntakeMotors;
import org.usfirst.frc.team93.robot.commands.gearmanipulator.ToggleTopClaw;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator;
import org.usfirst.frc.team93.robot.utilities.JoystickAxis;
import org.usfirst.frc.team93.robot.utilities.JoystickExtended;
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
	
	public static JoystickExtended movement;
	public static JoystickAxis yaw;
	public static JoystickAxis speedSlider;
	
	public static Button switchDrive;
	
	public static int slowButton;
	public static int switchButton;
	
	private static final int a = 1;
	private static final int b = 2;
	private static final int x = 3;
	private static final int y = 4;
	private static final int lb = 5;
	private static final int rb = 6;
	public static final int back = 7;
	private static final int start = 8;
	private static final int lstick = 9;
	
	public static Button fieldCentric;
	public static Button robotCentric;
	
	public static Button resetWheels;
	
	// operator
	public static Joystick operator;
	
	public static JoystickAxis belt;
	public static JoystickAxis climber;
	
	public static Button toggleTopClaw;
	public static Button toggleBottomClaw;
	public static Button toggleBothClaws;
	public static Button toggleIntakeMotors;
	public static Button releaseGear;
	public static Button topSetpoint;
	public static Button frontSetpoint;
	public static Button bottomMiddleSetpoint;
	public static Button bottomSetpoint;
	public static Button startManualControl;
	public static Button deflector;
	
	public OI()
	{
		// driver
		driver = new Joystick(0);
		
		movement = new JoystickExtended(driver, 0, 1);
		yaw = new JoystickAxis(driver, 2);
		
		yaw.setDeadzone(0.3);
		
		speedSlider = new JoystickAxis(driver, 3);
		
		switchButton = 2;
		slowButton = 1;
		
		switchDrive = new JoystickButton(driver, switchButton);
		switchDrive.whenPressed(new CrabDriveSwitch());
		
		fieldCentric = new JoystickButton(driver, 3);
		fieldCentric.whenPressed(new CrabDriveSetMode(CrabDriveMode.FieldCentric));
		robotCentric = new JoystickButton(driver, 4);
		robotCentric.whenPressed(new CrabDriveSetMode(CrabDriveMode.RobotCentric));
		
		resetWheels = new JoystickButton(driver, 5);
		resetWheels.whenPressed(new CrabDriveResetWheels());
		
		// operator
		operator = new Joystick(1);
		belt = new JoystickAxis(operator, 1);
		climber = new JoystickAxis(operator, 5);
		
		// baseline is 0.05, 100
		// 1: X left front
		// 2: A left back
		// 3: B right front
		// 4: Y right back
		
		// All PG71 polarity testing
		// 1: Move counterclockwise
		// 2: Move clockwise
		
		toggleTopClaw = new JoystickButton(operator, y);
		toggleTopClaw.whenPressed(new ToggleTopClaw());
		toggleBottomClaw = new JoystickButton(operator, a);
		toggleBottomClaw.whenPressed(new ToggleBottomClaw());
		toggleBothClaws = new JoystickButton(operator, x);
		toggleBothClaws.whenPressed(new ToggleBothClaws());
		toggleIntakeMotors = new JoystickButton(operator, b);
		toggleIntakeMotors.whenPressed(new ToggleIntakeMotors());
		releaseGear = new JoystickButton(operator, start);
		releaseGear.whenPressed(new ReleaseGearB());
		
		deflector = new JoystickButton(operator, back);
		deflector.whenPressed(new DeflectorFlip());
		
		// note that bottom and top setpoints are run by the shoulder buttons
		// and are handled by the ShoulderButtonReader command
		frontSetpoint = new JoystickButton(operator, rb);
		frontSetpoint.whenPressed(new MoveGearSafe(GearManipulator.GearLocation.PEG));
		bottomMiddleSetpoint = new JoystickButton(operator, lb);
		bottomMiddleSetpoint.whenPressed(new FrontAcquireA());
		startManualControl = new JoystickButton(operator, lstick);
		startManualControl.whenPressed(new ManualControl());
	}
}
