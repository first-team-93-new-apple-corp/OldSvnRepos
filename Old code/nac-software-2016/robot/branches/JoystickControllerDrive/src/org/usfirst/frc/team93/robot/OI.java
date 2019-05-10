package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.commands.GearshiftHigh;
import org.usfirst.frc.team93.robot.commands.GearshiftLow;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI
{
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
	public static Joystick driverJoystick;
	public static Joystick dualActionDriver;
	public static Joystick doubleDriveLeft;
	public static Joystick doubleDriveRight;
	public static JoystickButton highGearJoystick;
	public static JoystickButton lowGearJoystick;
	public static JoystickButton highGearRegular;
	public static JoystickButton lowGearRegular;
	public static JoystickButton highGearDual;
	public static JoystickButton lowGearDual;

	public OI()
	{
		// constructors for single joystick
		// driverJoystick = new Joystick(0);
		// highGearJoystick = new JoystickButton(driverJoystick, 4);
		// lowGearJoystick = new JoystickButton(driverJoystick, 3);
		// highGearJoystick.whenPressed(new GearshiftHigh());
		// lowGearJoystick.whenPressed(new GearshiftLow());

		// constructors for two joysticks
		doubleDriveRight = new Joystick(2);
		doubleDriveLeft = new Joystick(3);
		highGearDual = new JoystickButton(doubleDriveRight, 2);
		lowGearDual = new JoystickButton(doubleDriveLeft, 2);
		highGearDual.whenPressed(new GearshiftHigh());
		lowGearDual.whenPressed(new GearshiftLow());

		// constructors for playstation controller
		// dualActionDriver = new Joystick(1);
		// highGearRegular = new JoystickButton(dualActionDriver, 6);
		// lowGearRegular = new JoystickButton(dualActionDriver, 5);
		// highGearRegular.whenPressed(new GearshiftHigh());
		// lowGearRegular.whenPressed(new GearshiftLow());
	}

	public static double getLeftValue()
	{
		double getLeftValue;
		if (Math.abs(dualActionDriver.getRawAxis(1)) < .1)
		{
			getLeftValue = 0;
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
			getRightValue = 0;
		} else
		{
			getRightValue = -1 * dualActionDriver.getRawAxis(3);
		}

		return 0.9 * getRightValue;
	}
}
