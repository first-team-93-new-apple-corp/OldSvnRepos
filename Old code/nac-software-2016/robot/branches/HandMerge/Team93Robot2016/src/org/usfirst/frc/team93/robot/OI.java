package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.commands.ChangeDriveDirection;
import org.usfirst.frc.team93.robot.commands.IntakeBall;
import org.usfirst.frc.team93.robot.commands.SetFiringAngle;
import org.usfirst.frc.team93.robot.commands.SetShootWheels;
import org.usfirst.frc.team93.robot.commands.ShootBoulder;

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
	protected static Joystick operator;

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
	// button.whenReleased(new ExampleCommand());

	public OI()
	{
		driver = new Joystick(0);
		operator = new Joystick(1);

		// Driver Buttons
		Button changeDirection = new JoystickButton(driver, 1);

		// Operator Buttons
		Button startShootMotors = new JoystickButton(operator, 1);
		Button shootingSetpoint = new JoystickButton(operator, 2);
		Button ToggleIntake = new JoystickButton(operator, 3);
		Button shoot = new JoystickButton(operator, 4);
		Button ExtendShooter = new JoystickButton(operator, 5);

		Button ToggleScalar = new JoystickButton(operator, 6);

		// Button Functions
		changeDirection.toggleWhenPressed(new ChangeDriveDirection());

		// Code for wheels shooter
		startShootMotors.toggleWhenPressed(new SetShootWheels(1.0));
		shoot.whenPressed(new ShootBoulder(1.0));
		shootingSetpoint.whenPressed(new SetFiringAngle(17.6));
		ToggleIntake.toggleWhenPressed(new IntakeBall(0.5));
	}

	public static Joystick getDriver()
	{
		return driver;
	}

	public static Joystick getOperator()
	{
		return driver;
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
