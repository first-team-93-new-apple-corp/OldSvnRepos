package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.commands.ChangeDriveDirection;
import org.usfirst.frc.team93.robot.commands.GearshiftHigh;
import org.usfirst.frc.team93.robot.commands.GearshiftLow;
import org.usfirst.frc.team93.robot.commands.IntakeBall;
import org.usfirst.frc.team93.robot.commands.ManualInterrupt;
import org.usfirst.frc.team93.robot.commands.OutputBall;
import org.usfirst.frc.team93.robot.commands.PrepareShooter;
import org.usfirst.frc.team93.robot.commands.ShootingSequence;
import org.usfirst.frc.team93.robot.commands.setShooterErrorStability;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI
{

	protected static Joystick driver;
	public static Joystick operator;
	public static Joystick driverJoystick;
	public static Joystick dualActionDriver;
	public static Joystick doubleDriveLeft;
	public static Joystick doubleDriveRight;

	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.

	public static Button changeDirection;
	public static Button operatorManualInterrupt;
	public static Button TowerSetpoint;
	public static Button ToggleIntake;
	public static Button ToggleManipulator;
	public static Button reverseIntake;
	public static Button startShooter;
	public static Button softFire;
	public static Button prepareShooter;
	public static Button portcullisAngle;

	public static Button highGearJoystick;
	public static Button lowGearJoystick;

	public static int DEFAULT_DEADZONE = 1;

	public OI()
	{
		driver = new Joystick(0);
		operator = new Joystick(1);

		/*
		 * 
		 * 
		 * DRIVER BUTTONS
		 * 
		 * 
		 */
		changeDirection = new JoystickButton(driver, 1);
		changeDirection.whenPressed(new ChangeDriveDirection());

		highGearJoystick = new JoystickButton(driver, 2);
		highGearJoystick.whenPressed(new GearshiftHigh());

		lowGearJoystick = new JoystickButton(driver, 3);
		lowGearJoystick.whenPressed(new GearshiftLow());

		/*
		 * 
		 * 
		 * OPERATOR BUTTONS
		 * 
		 * 
		 */

		// portcullisAngle = new JoystickButton(operator, 2);
		// portcullisAngle.whenPressed(new SetManipulator(2.17, 0.1));

		prepareShooter = new JoystickButton(operator, 3);
		prepareShooter.toggleWhenPressed(new PrepareShooter(29000));

		ToggleIntake = new JoystickButton(operator, 5);
		ToggleIntake.toggleWhenPressed(new IntakeBall(1, 0));

		reverseIntake = new JoystickButton(operator, 7);
		reverseIntake.toggleWhenPressed(new OutputBall());

		startShooter = new JoystickButton(operator, 8);
		startShooter.whenPressed(new ShootingSequence(29000));

		softFire = new JoystickButton(operator, 6);
		softFire.whenPressed(new ShootingSequence(20000));

		operatorManualInterrupt = new JoystickButton(operator, 10);
		operatorManualInterrupt.whenPressed(new ManualInterrupt(Robot.shooter));
		operatorManualInterrupt.whenPressed(new ManualInterrupt(Robot.bacquisition));
		operatorManualInterrupt.whenPressed(new ManualInterrupt(Robot.defenseManipulator));
		operatorManualInterrupt.whenPressed(new setShooterErrorStability(false));

	}

	public static double getLeftValue()
	{
		double getLeftValue;
		if (Math.abs(dualActionDriver.getRawAxis(1)) < .1)
		{
			return 0;
		} else
		{
			getLeftValue = -1 * dualActionDriver.getRawAxis(1);
		}

		return 0.9 * getLeftValue;
	}

	public static double getRightValue()
	{
		double getRightValue;
		if (Math.abs(dualActionDriver.getRawAxis(3)) < .1)
		{
			return 0;
		} else
		{
			getRightValue = -1 * dualActionDriver.getRawAxis(3);
		}

		return 0.9 * getRightValue;
	}

	/*
	 * fix do our liking, here as a place holder for Drive Continuous stuff
	 */
	public static double getDriverLY(double deadzone)
	{
		return (deadzone(driver.getRawAxis(1), deadzone));
	}

	/*
	 * fix to our liking, here as a place holder for Drive Continuous stuff
	 */
	public static double getDriverRY(double deadzone)
	{
		return (deadzone(driver.getRawAxis(3), deadzone));
	}

	public static double getOperatorLY(double deadzone)
	{
		return (deadzone(operator.getRawAxis(1), deadzone));
	}

	public static double getOperatorRY(double deadzone)
	{
		return (deadzone(operator.getRawAxis(3), deadzone));
	}

	public static double deadzone(double value, double deadzone)
	{
		double modifiedValue = value;

		if ((value <= deadzone) && (value >= 0))
		{
			modifiedValue = 0;
		}

		return modifiedValue;
	}
}
