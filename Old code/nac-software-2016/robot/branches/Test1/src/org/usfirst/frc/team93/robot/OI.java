package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.commands.CANTalonCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
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
	public static Joystick driveJoystick;
	public static Button PIDButton;
	public static Button secondPIDButton;
	public static Button CANTesterLow;
	public static Button CANTesterHigh;
	public static Button CANCancel;

	public OI()
	{
		driveJoystick = new Joystick(0);
		CANTesterLow = new JoystickButton(driveJoystick, 1);
		CANTesterHigh = new JoystickButton(driveJoystick, 2);
		CANCancel = new JoystickButton(driveJoystick, 3);
		CANTesterLow.whenPressed(new CANTalonCommand(10000.0));
		CANTesterHigh.whenPressed(new CANTalonCommand(30000.0));
		CANCancel.whenPressed(new CANTalonCommand(0.0));
		// PIDButton = new JoystickButton(driveJoystick, 2);
		// secondPIDButton = new JoystickButton(driveJoystick, 3);
		// PIDButton.whenPressed(new DriveForward(-1280, 10));
		// secondPIDButton.whenPressed(new DriveForward(2560, 10));
	}

}
